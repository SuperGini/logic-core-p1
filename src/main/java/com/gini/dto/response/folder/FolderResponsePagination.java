package com.gini.dto.response.folder;

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
    private int totalElements;

    @Builder.Default //so that the builder takes my instantiation of the list and not instantiate the list when the builder is created
    private List<FolderResponse> folderResponses = new ArrayList<>();

}
