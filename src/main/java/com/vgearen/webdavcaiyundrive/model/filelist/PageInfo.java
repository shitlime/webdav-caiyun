package com.vgearen.webdavcaiyundrive.model.filelist;

public class PageInfo {
    private Integer pageSize = 100;
    private String pageCursor;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageCursor() {
        return pageCursor;
    }

    public void setPageCursor(String pageCursor) {
        this.pageCursor = pageCursor;
    }
}
