package com.gini.dto.response.file;

import java.util.List;

public record FileResponsePagination(
        int totalElements,
        List<FileResponse> fileResponses
) {
}
