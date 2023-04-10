package com.gini.api.controller;


import com.gini.dto.request.folder.CreateFolderRequest;
import com.gini.dto.response.FolderResponse;
import com.gini.dto.request.folder.FolderResponsePagination;
import com.gini.services.FolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/v1",
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;


    @PostMapping("/folder")
    @ResponseStatus(HttpStatus.CREATED)
    public FolderResponse createFolder(@RequestBody CreateFolderRequest createFolderRequest) {

        return folderService.createProjectFolder(createFolderRequest);
    }

    @GetMapping("/folders/{userId}/{pageNumber}/{pageElements}")
    @ResponseStatus(HttpStatus.OK)
    public FolderResponsePagination getAllFoldersByIdWithPagination(@PathVariable String userId,
                                                                    @PathVariable Integer pageNumber,
                                                                    @PathVariable Integer pageElements
    ) {
        return folderService.getAllFoldersByIdWithPagination(userId, pageNumber, pageElements);
    }

}
