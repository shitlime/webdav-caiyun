package com.vgearen.webdavcaiyundrive.model.operate;

import java.util.ArrayList;
import java.util.List;

public class RemoveRequest {
    private List<String> fileIds;

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
}
