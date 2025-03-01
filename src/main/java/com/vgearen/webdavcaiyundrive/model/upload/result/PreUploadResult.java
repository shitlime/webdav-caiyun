package com.vgearen.webdavcaiyundrive.model.upload.result;

import java.util.List;

public class PreUploadResult {
    private String parentFileId;
    private String fileId;
    private String type;
    private String fileName;
    /**
     * 秒传标记
     */
    private Boolean rapidUpload;
    private String uploadId;
    private List<ResultPartInfos> partInfos;
    private Object exist;
    private Object formInfo;

    public String getParentFileId() {
        return parentFileId;
    }

    public void setParentFileId(String parentFileId) {
        this.parentFileId = parentFileId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Boolean getRapidUpload() {
        return rapidUpload;
    }

    public void setRapidUpload(Boolean rapidUpload) {
        this.rapidUpload = rapidUpload;
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public List<ResultPartInfos> getPartInfos() {
        return partInfos;
    }

    public void setPartInfos(List<ResultPartInfos> partInfos) {
        this.partInfos = partInfos;
    }

    public Object getExist() {
        return exist;
    }

    public void setExist(Object exist) {
        this.exist = exist;
    }

    public Object getFormInfo() {
        return formInfo;
    }

    public void setFormInfo(Object formInfo) {
        this.formInfo = formInfo;
    }
}
