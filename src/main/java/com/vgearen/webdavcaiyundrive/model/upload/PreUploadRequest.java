package com.vgearen.webdavcaiyundrive.model.upload;

import com.vgearen.webdavcaiyundrive.model.UploadPreRequest;

import java.util.List;

public class PreUploadRequest {
    private String parentFileId;
    private String name;
    private String type;
    private Long size;
    /**
     * 默认
     */
    private String fileRenameMode = "auto_rename";
    private String contentHash;
    private String contentHashAlgorithm;
    /**
     * 默认
     */
    private String contentType = "application/oct-stream";
    /**
     * 默认 false
     */
    private Boolean parallelUpload = Boolean.FALSE;
    private List<PartInfos> partInfos;

    public class PartInfos {
        private ParallelHashCtx parallelHashCtx;
        private Integer partNumber;
        private Long partSize;

        public class ParallelHashCtx {
            private Long partOffset;

            public Long getPartOffset() {
                return partOffset;
            }

            public void setPartOffset(Long partOffset) {
                this.partOffset = partOffset;
            }
        }

        public ParallelHashCtx getParallelHashCtx() {
            return parallelHashCtx;
        }

        public void setParallelHashCtx(ParallelHashCtx parallelHashCtx) {
            this.parallelHashCtx = parallelHashCtx;
        }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getFileRenameMode() {
        return fileRenameMode;
    }

    public void setFileRenameMode(String fileRenameMode) {
        this.fileRenameMode = fileRenameMode;
    }

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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Boolean getParallelUpload() {
        return parallelUpload;
    }

    public void setParallelUpload(Boolean parallelUpload) {
        this.parallelUpload = parallelUpload;
    }

    public List<PartInfos> getPartInfos() {
        return partInfos;
    }

    public void setPartInfos(List<PartInfos> partInfos) {
        this.partInfos = partInfos;
    }
}
