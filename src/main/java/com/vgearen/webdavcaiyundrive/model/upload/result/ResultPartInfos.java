package com.vgearen.webdavcaiyundrive.model.upload.result;

public class ResultPartInfos {
    private Integer partNumber;
    private Long partSize;
    private String uploadUrl;
    private String cdnUploadUrl;
    private Object etag;
    private Object parallelHashCtx;

    public Integer getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(Integer partNumber) {
        this.partNumber = partNumber;
    }

    public Long getPartSize() {
        return partSize;
    }

    public void setPartSize(Long partSize) {
        this.partSize = partSize;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getCdnUploadUrl() {
        return cdnUploadUrl;
    }

    public void setCdnUploadUrl(String cdnUploadUrl) {
        this.cdnUploadUrl = cdnUploadUrl;
    }

    public Object getEtag() {
        return etag;
    }

    public void setEtag(Object etag) {
        this.etag = etag;
    }

    public Object getParallelHashCtx() {
        return parallelHashCtx;
    }

    public void setParallelHashCtx(Object parallelHashCtx) {
        this.parallelHashCtx = parallelHashCtx;
    }
}
