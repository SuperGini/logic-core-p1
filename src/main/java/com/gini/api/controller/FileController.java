package com.gini.api.controller;

import com.gini.dto.request.folder.FolderInfoRequest;
import com.gini.dto.response.file.FileResponse;
import com.gini.dto.response.file.FileResponsePagination;
import com.gini.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/v1",
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveImage(@RequestPart FolderInfoRequest folderInfo, @RequestPart List<MultipartFile> imageFile) {
        fileService.saveFilesToFolder(folderInfo, imageFile);
    }

    @GetMapping("/images/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public FileResponsePagination getFiles(@PathVariable String folderId){
       return fileService.getFilesWithPaginationByFolderId(folderId);
    }


}
