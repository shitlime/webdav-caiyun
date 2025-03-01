package com.vgearen.webdavcaiyundrive.store;

import com.vgearen.webdavcaiyundrive.model.FileType;
import com.vgearen.webdavcaiyundrive.model.CFile;
import com.vgearen.webdavcaiyundrive.model.upload.result.PreUploadResult;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 虚拟文件（用于上传时，列表展示）
 */
@Service
public class VirtualCFileService {
    private final Map<String, Map<String, CFile>> virtualCFileMap = new ConcurrentHashMap<>();

    /**
     * 创建文件
     */
    public void createCFile(String parentId, PreUploadResult preUploadResult) {
        Map<String, CFile> cFileMap = virtualCFileMap.computeIfAbsent(parentId, s -> new ConcurrentHashMap<>());
        cFileMap.put(preUploadResult.getFileId(), convert(preUploadResult));
    }

    public void updateLength(String parentId, String fileId, long length) {
        Map<String, CFile> cFileMap = virtualCFileMap.get(parentId);
        if (cFileMap == null) {
            return;
        }
        CFile cFile = cFileMap.get(fileId);
        if (cFile == null) {
            return;
        }
        cFile.setSize(cFile.getSize() + length);
        cFile.setUpdateTime(new Date());
    }

    public void remove(String parentId, String fileId) {
        Map<String, CFile> cFileMap = virtualCFileMap.get(parentId);
        if (cFileMap == null) {
            return;
        }
        cFileMap.remove(fileId);
    }

    public Collection<CFile> list(String parentId) {
        Map<String, CFile> cFileMap = virtualCFileMap.get(parentId);
        if (cFileMap == null) {
            return Collections.emptyList();
        }
        return cFileMap.values();
    }

    private CFile convert(PreUploadResult preUploadResult) {
        CFile cFile = new CFile();
        cFile.setCreateTime(new Date());
        cFile.setFileId(preUploadResult.getFileId());
        cFile.setName(preUploadResult.getFileName());
        cFile.setFileType(FileType.file.name());
        cFile.setUpdateTime(new Date());
        cFile.setSize(0L);
        return cFile;
    }
}
