package com.gini.dto.response.file;

public record FileResponse(
        String id,
        String fileName,
        String fileFormat,
        String folderId,
        byte [] file
) {
}
