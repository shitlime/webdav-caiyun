package com.vgearen.webdavcaiyundrive.model.download.result;

public class DownloadResult {
    private String fileId;
    private Object errCode;
    private Object message;
    private String url;
    private String expiration;
    private Long size;
    private Object cdnUrl;
    private Object cdnSwitch;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Object getErrCode() {
        return errCode;
    }

    public void setErrCode(Object errCode) {
        this.errCode = errCode;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Object getCdnUrl() {
        return cdnUrl;
    }

    public void setCdnUrl(Object cdnUrl) {
        this.cdnUrl = cdnUrl;
    }

    public Object getCdnSwitch() {
        return cdnSwitch;
    }

    public void setCdnSwitch(Object cdnSwitch) {
        this.cdnSwitch = cdnSwitch;
    }
}
