package com.gini.dto.request.folder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FolderResponsePagination {

    private int totalPages;
    List<FolderResponse> folderResponses = new ArrayList<>();

}
