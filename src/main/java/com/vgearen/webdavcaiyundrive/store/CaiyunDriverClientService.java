package com.vgearen.webdavcaiyundrive.store;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.vgearen.webdavcaiyundrive.client.CaiyunDriverClient;
import com.vgearen.webdavcaiyundrive.model.*;
import com.vgearen.webdavcaiyundrive.model.download.DownloadRequest;
import com.vgearen.webdavcaiyundrive.model.download.result.DownloadResult;
import com.vgearen.webdavcaiyundrive.model.filelist.FileListRequest;
import com.vgearen.webdavcaiyundrive.model.filelist.PageInfo;
import com.vgearen.webdavcaiyundrive.model.filelist.result.*;
import com.vgearen.webdavcaiyundrive.model.operate.*;
import com.vgearen.webdavcaiyundrive.model.operatefolder.CreateFolderRequest;
import com.vgearen.webdavcaiyundrive.model.operatefolder.result.CreateFolderResult;
import com.vgearen.webdavcaiyundrive.model.upload.PostUploadRequest;
import com.vgearen.webdavcaiyundrive.model.upload.PreUploadRequest;
import com.vgearen.webdavcaiyundrive.model.upload.result.PreUploadResult;
import com.vgearen.webdavcaiyundrive.util.JsonUtil;
import net.sf.webdav.exceptions.WebdavException;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class CaiyunDriverClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CaiyunDriverClientService.class);
    private static String rootPath = "/";
    private CFile rootCFile = null;

    @Autowired
    private CachingInputStreamWrapperFactory cacheWrapperFactory;

    private static Cache<String, Set<CFile>> cFilesCache = Caffeine.newBuilder()
            .initialCapacity(128)
            .maximumSize(65535)
            .expireAfterWrite(20, TimeUnit.SECONDS)
            .build();

    private final CaiyunDriverClient client;

    @Autowired
    private VirtualCFileService virtualCFileService;

    public CaiyunDriverClientService(CaiyunDriverClient caiyunDriverClient) {
        this.client = caiyunDriverClient;
        CaiyunDriverFileSystemStore.setBean(this);
    }

    public CFile getCFileByPath(String path) {
        path = normalizingPath(path);

        return getFileIdByPath(path);
    }

    public Set<CFile> getCFiles(String fileId) {
        Set<CFile> cFiles = cFilesCache.get(fileId, key -> {
            // 获取真实的文件列表
            return getCFilesWithNoRepeat(fileId);
        });
        Set<CFile> all = new LinkedHashSet<>(cFiles);
        // 获取上传中的文件列表
        Collection<CFile> virtualCFiles = virtualCFileService.list(fileId);
        all.addAll(virtualCFiles);
        return all;
    }

    private Set<CFile> getCFilesWithNoRepeat(String catalogId) {
        List<CFile> cFiles = fileListFromApi(catalogId, null, new ArrayList<>());
        cFiles.sort(Comparator.comparing(CFile::getUpdateTime).reversed());
        Set<CFile> cFileSet = new LinkedHashSet<>();
        for (CFile item : cFiles) {
            if (!cFileSet.add(item)) {
                LOGGER.info("当前目录下{} 存在同名文件：{}，文件大小：{}", catalogId, item.getName(), item.getSize());
            }
        }
        // 对文件名进行去重，只保留最新的一个
        return cFileSet;
    }

    private String normalizingPath(String path) {
        path = path.replaceAll("//", "/");
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    private CFile getFileIdByPath(String path) {
        if (!StringUtils.hasLength(path)) {
            path = rootPath;
        }
        if (path.equals(rootPath)) {
            return getRootCFile();
        }
        PathInfo pathInfo = getPathInfo(path);
        CFile cFile = getCFileByPath(pathInfo.getParentPath());
        if (cFile == null) {
            return null;
        }
        return getCFileByParentId(cFile.getFileId(), pathInfo.getName());
    }

    private CFile getCFileByParentId(String parentId, String name) {
        Set<CFile> cFiles = getCFiles(parentId);
        for (CFile cFile : cFiles) {
            if (cFile.getName().equals(name)) {
                return cFile;
            }
        }
        return null;
    }

    public PathInfo getPathInfo(String path) {
        path = normalizingPath(path);
        if (path.equals(rootPath)) {
            PathInfo pathInfo = new PathInfo();
            pathInfo.setPath(path);
            pathInfo.setName(path);
            return pathInfo;
        }
        int index = path.lastIndexOf("/");
        String parentPath = path.substring(0, index + 1);
        String name = path.substring(index + 1);
        PathInfo pathInfo = new PathInfo();
        pathInfo.setPath(path);
        pathInfo.setParentPath(parentPath);
        pathInfo.setName(name);
        return pathInfo;
    }

    private CFile getRootCFile() {
        if (rootCFile == null) {
            rootCFile = new CFile();
            rootCFile.setName("/");
            rootCFile.setFileId("/");
            rootCFile.setCreateTime(new Date());
            rootCFile.setUpdateTime(new Date());
            rootCFile.setFileType(FileType.folder.name());
        }
        return rootCFile;
    }

    public List<CFile> fileListFromApi(String parentFileId, String pageCursor, List<CFile> all) {
        FileListRequest listQuery = new FileListRequest();
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageSize(100);
        pageInfo.setPageCursor(pageCursor);
        listQuery.setParentFileId(parentFileId);
        listQuery.setPageInfo(pageInfo);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        // 新api
        String json = client.post("https://personal-kd-njs.yun.139.com/hcy/file/list", listQuery);
        CaiyunResponse<FileListData> cFileListResult = JsonUtil.readValue(json, new TypeReference<CaiyunResponse<FileListData>>() {
        });

        if (cFileListResult.getData().getItems() != null) {
            for (Item item : cFileListResult.getData().getItems()) {
                CFile cFile = new CFile();
                cFile.setFileId(item.getFileId());
                cFile.setFileType(item.getType());
                cFile.setName(item.getName());
                try {
                    Date updateTime = format.parse(item.getUpdatedAt());
                    Date createTime = format.parse(item.getCreatedAt());
                    cFile.setUpdateTime(updateTime);
                    cFile.setCreateTime(createTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                all.add(cFile);
            }
        }

        String nextPageCursor = cFileListResult.getData().getNextPageCursor();
        if (nextPageCursor == null) {
            return all;
        }
        return fileListFromApi(parentFileId, nextPageCursor, all);
    }

    public Response download(String path, HttpServletRequest request, long size) {
        CFile cFile = getCFileByPath(path);

        DownloadRequest downloadRequest = new DownloadRequest();
        downloadRequest.setFileId(cFile.getFileId());

        String json = client.post("https://personal-kd-njs.yun.139.com/hcy/file/getDownloadUrl", downloadRequest);
        CaiyunResponse<DownloadResult> downloadResult =
                JsonUtil.readValue(json, new TypeReference<CaiyunResponse<DownloadResult>>() {});
        String url = downloadResult.getData().getUrl();
        LOGGER.debug("{} url = {}", path, url);
        return client.download(url, request, size);
    }


    public void uploadPre(String path, long size, InputStream inputStream) {
        path = normalizingPath(path);
        PathInfo pathInfo = getPathInfo(path);
        CFile parent = getCFileByPath(pathInfo.getParentPath());
        if (parent == null) {
            return;
        }
        // 如果已存在，先删除
        CFile cFile = getCFileByPath(path);
        if (cFile != null) {
            if (cFile.getSize() == size) {
                //如果文件大小一样，则不再上传
                return;
            }
            remove(path);
        }

        if (pathInfo.getName().startsWith("._") || pathInfo.getName().startsWith("~$") || ".DS_Store".equals(pathInfo.getName())) {
            // 临时文件不上传
            return;
        }


        PreUploadRequest preUploadRequest = new PreUploadRequest();
        preUploadRequest.setParentFileId(parent.getFileId());
        preUploadRequest.setName(pathInfo.getName());
        preUploadRequest.setType("file");
        preUploadRequest.setSize(size);
        preUploadRequest.setContentHashAlgorithm("SHA256");
        String sha256;
        CachingInputStreamWrapper cachingInputStream = cacheWrapperFactory.create(inputStream);
        try {
            sha256 = cachingInputStream.cacheAndCalculateHash();
            preUploadRequest.setContentHash(sha256);
        } catch (Exception e) {
            LOGGER.error("计算文件SHA256出错：{}", e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // 获取一个 url 上传整个文件，避免多url分段上传导致 complete api 未知报错
        ArrayList<PreUploadRequest.PartInfos> partInfos = new ArrayList<>();
        PreUploadRequest.PartInfos pi = preUploadRequest.new PartInfos();
        PreUploadRequest.PartInfos.ParallelHashCtx phc = pi.new ParallelHashCtx();
        phc.setPartOffset(0L);
        pi.setParallelHashCtx(phc);
        pi.setPartNumber(1);
        pi.setPartSize(size);
        partInfos.add(pi);

        preUploadRequest.setPartInfos(partInfos);

        LOGGER.info("开始上传文件，文件名：{}，总大小：{}", path, size);
        String json = client.post("https://personal-kd-njs.yun.139.com/hcy/file/create", preUploadRequest);
        CaiyunResponse<PreUploadResult> preUploadRes =
                JsonUtil.readValue(json, new TypeReference<CaiyunResponse<PreUploadResult>>() {});


        if (preUploadRes.getData().getRapidUpload()) {
            LOGGER.info("{} 秒传成功", path);
            return;
        }

        if (size > 0) {
            virtualCFileService.createCFile(parent.getFileId(), preUploadRes.getData());
        }

        // upload file inputStream
        LOGGER.info("文件正在上传。文件名：{}", path);
        try {
            client.upload(preUploadRes.getData().getPartInfos().get(0).getUploadUrl(),
                    size, cachingInputStream.getCachedInputStream());
        } catch (IOException e) {
            LOGGER.error("上传文件时错误：{}", e.toString());
            e.printStackTrace();
            throw new WebdavException(e);
        }
        cachingInputStream.cleanUp();

        // complete api
        PostUploadRequest postUploadRequest = new PostUploadRequest();
        postUploadRequest.setFileId(preUploadRes.getData().getFileId());
        postUploadRequest.setUploadId(preUploadRes.getData().getUploadId());
        postUploadRequest.setContentHashAlgorithm("SHA256");
        postUploadRequest.setContentHash(sha256);
        client.post("https://personal-kd-njs.yun.139.com/hcy/file/complete", postUploadRequest);

        virtualCFileService.remove(parent.getFileId(), preUploadRes.getData().getFileId());
        LOGGER.info("文件上传成功。文件名：{}", path);
        clearCache();
    }


    public void remove(String path) {
        path = normalizingPath(path);
        CFile cFile = getCFileByPath(path);
        if (cFile == null) {
            return;
        }

        RemoveRequest removeRequest = new RemoveRequest();
        removeRequest.setFileId(cFile.getFileId());

        // 新api
        client.post("https://personal-kd-njs.yun.139.com/hcy/recyclebin/batchTrash", removeRequest);
        clearCache();
    }

    public void createFolder(String path) {
        path = normalizingPath(path);
        PathInfo pathInfo = getPathInfo(path);
        CFile parent = getCFileByPath(pathInfo.getParentPath());
        if (parent == null) {
            LOGGER.warn("创建目录失败，未发现父级目录：{}", pathInfo.getParentPath());
            return;
        }

        CreateFolderRequest createFileRequest = new CreateFolderRequest();
        createFileRequest.setParentFileId(parent.getFileId());
        createFileRequest.setName(pathInfo.getName());

        // 新api
        String json = client.post("https://personal-kd-njs.yun.139.com/hcy/file/create", createFileRequest);
        CaiyunResponse<CreateFolderResult> createFolderResult = JsonUtil.readValue(json, new TypeReference<CaiyunResponse<CreateFolderResult>>() {
        });

        if (createFolderResult.getData().getFileName() == null) {
            LOGGER.error("创建目录{}失败: {}", path, json);
        }
        if (!createFolderResult.getData().getFileName().equals(pathInfo.getName())) {
            LOGGER.info("创建目录{}与原值{}不同，重命名", createFolderResult.getData().getFileName(), pathInfo.getName());
            rename(pathInfo.getParentPath() + "/" + createFolderResult.getData().getFileName(), pathInfo.getName());
        }
        clearCache();
    }

    public void rename(String sourcePath, String newName) {
        sourcePath = normalizingPath(sourcePath);
        CFile cFile = getCFileByPath(sourcePath);

        RenameRequest renameRequest = new RenameRequest();
        renameRequest.setFileId(cFile.getFileId());
        renameRequest.setName(newName);
        // 新api
        client.post("https://personal-kd-njs.yun.139.com/hcy/file/update", renameRequest);

        clearCache();
    }

    public void move(String sourcePath, String targetPath) {
        sourcePath = normalizingPath(sourcePath);
        targetPath = normalizingPath(targetPath);

        CFile sourceCFile = getCFileByPath(sourcePath);
        CFile targetCFile = getCFileByPath(targetPath);

        MoveRequest moveRequest = new MoveRequest();
        moveRequest.setFileId(sourceCFile.getFileId());
        moveRequest.setToParentFileId(targetCFile.getFileId());

        client.post("https://personal-kd-njs.yun.139.com/hcy/file/batchMove", moveRequest);
        clearCache();
    }

    private void clearCache() {
        cFilesCache.invalidateAll();
    }
}
