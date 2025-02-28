package com.vgearen.webdavcaiyundrive.model.operate;

import java.util.ArrayList;
import java.util.List;

public class MoveRequest {
    private List<String> fileIds;
    private String toParentFileId;

    public List<String> getFileIds() {
        return fileIds;
    }

    public void setFileIds(List<String> fileIds) {
        this.fileIds = fileIds;
    }

    public void setFileId(String fileId) {
        this.fileIds = new ArrayList<>();
        this.fileIds.add(fileId);
    }

    public String getToParentFileId() {
        return toParentFileId;
    }

    public void setToParentFileId(String toParentFileId) {
        this.toParentFileId = toParentFileId;
    }
}
