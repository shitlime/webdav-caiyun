
package com.vgearen.webdavcaiyundrive.model.operatefolder;


public class CreateFolderRequest {
    private String parentFileId;
    private String name;
    private String description = "";
    private String type = "folder";
    private String fileRenameMode = "force_rename";

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

    public String getFileRenameMode() {
        return fileRenameMode;
    }

    public void setFileRenameMode(String fileRenameMode) {
        this.fileRenameMode = fileRenameMode;
    }
}
