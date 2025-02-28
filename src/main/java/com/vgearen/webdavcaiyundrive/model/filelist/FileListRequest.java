package com.vgearen.webdavcaiyundrive.model.filelist;


import java.util.Arrays;
import java.util.List;

public class FileListRequest {
    private PageInfo pageInfo;
    /**
     * 排序依据，默认上传时间
     */
    private String orderBy = "updated_at";
    /**
     * 排序方式
     */
    private String orderDirection = "DESC";
    /**
     * 父目录id
     */
    private String parentFileId;
    /**
     * 缩略图样式列表
     */
    private List<String> imageThumbnailStyleList = Arrays.asList("Small", "Large");

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public String getParentFileId() {
        return parentFileId;
    }

    public void setParentFileId(String parentFileId) {
        this.parentFileId = parentFileId;
    }

    public List<String> getImageThumbnailStyleList() {
        return imageThumbnailStyleList;
    }

    public void setImageThumbnailStyleList(List<String> imageThumbnailStyleList) {
        this.imageThumbnailStyleList = imageThumbnailStyleList;
    }
}
