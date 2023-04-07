package com.gini.dto.request.folder;

public record CreateFolderRequest(
        String projectName,
        String userId
) {
}
