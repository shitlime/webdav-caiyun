package com.vgearen.webdavcaiyundrive.model.filelist.result;

import java.util.List;

public class Item {
    private String fileId;
    private String parentFileId;
    private String name;
    private String description;
    /**
     * 文件类型：folder 文件夹；file 文件
     */
    private String type;
    private String fileExtension;
    /**
     * 文件分类：（folder 文件夹；video 视屏……）
     */
    private String category;
    private String createdAt;
    private String updatedAt;
    private String trashedAt;
    private String localCreatedAt;
    private String localUpdatedAt;
    private String starredAt;
    private Boolean starred;
    /**
     * 文件大小
     */
    private Long size;
    /**
     * 缩略图URL
     */
    // todo （可扩展）给缩略图创建新的类保存数据
    private List thumbnailUrls;
    // todo 类型未知
    private Object punishMode;
    private Boolean systemDir;
    private String revisionId;
    // todo （可扩展）创建新的类保存数据
    private Object mediaMetaInfo;
    // todo （可扩展）创建新的类保存数据
    private Object metadataAuditInfo;
    // todo （可扩展）创建新的类保存数据
    private Object contentAuditInfo;
    // todo （可扩展）创建新的类保存数据
    private List userTags;
    // todo （可扩展）创建新的类保存数据
    private Object addressDetail;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getParentFileId() {
        return parentFileId;
    }

    public void setParentFileId(String parentFileId) {
        this.parentFileId = parentFileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTrashedAt() {
        return trashedAt;
    }

    public void setTrashedAt(String trashedAt) {
        this.trashedAt = trashedAt;
    }

    public String getLocalCreatedAt() {
        return localCreatedAt;
    }

    public void setLocalCreatedAt(String localCreatedAt) {
        this.localCreatedAt = localCreatedAt;
    }

    public String getLocalUpdatedAt() {
        return localUpdatedAt;
    }

    public void setLocalUpdatedAt(String localUpdatedAt) {
        this.localUpdatedAt = localUpdatedAt;
    }

    public String getStarredAt() {
        return starredAt;
    }

    public void setStarredAt(String starredAt) {
        this.starredAt = starredAt;
    }

    public Boolean getStarred() {
        return starred;
    }

    public void setStarred(Boolean starred) {
        this.starred = starred;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public List getThumbnailUrls() {
        return thumbnailUrls;
    }

    public void setThumbnailUrls(List thumbnailUrls) {
        this.thumbnailUrls = thumbnailUrls;
    }

    public Object getPunishMode() {
        return punishMode;
    }

    public void setPunishMode(Object punishMode) {
        this.punishMode = punishMode;
    }

    public Boolean getSystemDir() {
        return systemDir;
    }

    public void setSystemDir(Boolean systemDir) {
        this.systemDir = systemDir;
    }

    public String getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(String revisionId) {
        this.revisionId = revisionId;
    }

    public Object getMediaMetaInfo() {
        return mediaMetaInfo;
    }

    public void setMediaMetaInfo(Object mediaMetaInfo) {
        this.mediaMetaInfo = mediaMetaInfo;
    }

    public Object getMetadataAuditInfo() {
        return metadataAuditInfo;
    }

    public void setMetadataAuditInfo(Object metadataAuditInfo) {
        this.metadataAuditInfo = metadataAuditInfo;
    }

    public Object getContentAuditInfo() {
        return contentAuditInfo;
    }

    public void setContentAuditInfo(Object contentAuditInfo) {
        this.contentAuditInfo = contentAuditInfo;
    }

    public List getUserTags() {
        return userTags;
    }

    public void setUserTags(List userTags) {
        this.userTags = userTags;
    }

    public Object getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(Object addressDetail) {
        this.addressDetail = addressDetail;
    }
}
