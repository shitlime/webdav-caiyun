
package com.vgearen.webdavcaiyundrive.model.filelist.result;

import java.util.List;

public class FileListData {
    /**
     * 文件数据
     */
    private List<Item> items;

    /**
     * 下一页光标
     */
    private String nextPageCursor;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getNextPageCursor() {
        return nextPageCursor;
    }

    public void setNextPageCursor(String nextPageCursor) {
        this.nextPageCursor = nextPageCursor;
    }
}
