package com.vgearen.webdavcaiyundrive.model.upload;

public class PostUploadRequest {
    private String fileId;
    private String uploadId;
    private String contentHash;
    private String contentHashAlgorithm;

    public String getContentHash() {
        return contentHash;
    }

    public void setContentHash(String contentHash) {
        this.contentHash = contentHash;
    }

    public String getContentHashAlgorithm() {
        return contentHashAlgorithm;
    }

    public void setContentHashAlgorithm(String contentHashAlgorithm) {
        this.contentHashAlgorithm = contentHashAlgorithm;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }
}
