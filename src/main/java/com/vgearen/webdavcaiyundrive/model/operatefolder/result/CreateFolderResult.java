package com.vgearen.webdavcaiyundrive.model.operatefolder.result;

public class CreateFolderResult {
    private String parentFileId;
    private String fileId;
    private String type;
    private String fileName;
    private Boolean rapidUpload;
    private Object uploadId;
    private Object partInfos;
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

    public Object getUploadId() {
        return uploadId;
    }

    public void setUploadId(Object uploadId) {
        this.uploadId = uploadId;
    }

    public Object getPartInfos() {
        return partInfos;
    }

    public void setPartInfos(Object partInfos) {
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
