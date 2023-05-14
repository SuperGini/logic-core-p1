package com.gini.services;

import com.gini.config.folder.MainFolderConfig;
import com.gini.dto.request.folder.FolderInfoRequest;
import com.gini.dto.response.file.FileResponse;
import com.gini.dto.response.file.FileResponsePagination;
import com.gini.error.error.LogicCoreException;
import com.gini.error.error.NotFoundException;
import com.gini.persitence.model.entities.File;
import com.gini.persitence.model.entities.ProjectFolder;
import com.gini.persitence.repository.folder.FileRepository;
import com.gini.persitence.repository.folder.ProjectFolderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    private final MainFolderConfig folderProperty;
    private final ProjectFolderRepository folderRepository;
    private final FileRepository fileRepository;

    @Transactional
    public void saveFilesToFolder(FolderInfoRequest folderInfo, List<MultipartFile> imageFiles){
        var folderId = Long.parseLong(folderInfo.folderId());

        folderRepository.findProjectFolderByIdAndFolderName(folderId, folderInfo.folderName())
                .ifPresentOrElse(
                        folder -> saveFileToFolderAndDatabase(imageFiles, folder),
                            () -> {throw new NotFoundException("Folder was not found");}
                );
    }

    @Transactional
    public FileResponsePagination getFilesWithPaginationByFolderId(String folderId, Integer pageNumber, Integer pageElements) {
        List<FileResponse> fileResponses = new ArrayList<>();
        var longFolderId = Long.parseLong(folderId);

        Pageable pageRequest = PageRequest.of(pageNumber, pageElements);

        Page<File> files = fileRepository.findAllProjectFileWithPagination(pageRequest, longFolderId);
        var totalElements = (int) files.getTotalElements();

         files.stream()
                .map(this::convertToFileResponse)
                 .forEach(fileResponses::add);

         return new FileResponsePagination(totalElements, fileResponses);
    }

    private FileResponse convertToFileResponse(File file){
        var fileId = String.valueOf(file.getId());
        var folderId = String.valueOf(file.getProjectFolder().getId());
        var filePat = Path.of(file.getFileLocation());

        byte [] fileAsBytes = convertFileToBytes(filePat);

        return new FileResponse(
                fileId,
                filePat.getFileName().toString(),
                file.getFileFormat(),
                folderId,
                fileAsBytes
        );
    }

    private static byte[] convertFileToBytes(Path filePat) {
        byte[] fileAsBytes;
        try {
             fileAsBytes = Files.readAllBytes(filePat);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileAsBytes;
    }


    private void saveFileToFolderAndDatabase(List<MultipartFile> imageFiles, ProjectFolder folder) {
        var files = new ArrayList<File>();
        var mainDirectory = folderProperty.getMainDirectory();
        var folderName = folder.getFolderName()
                                        .trim()
                                        .replace(" ", "_");
        var createFolderDirectory = Paths.get(mainDirectory, folderName);

        saveFilesToDatabase(imageFiles, folder, files, mainDirectory, folderName);

        createFolder(folder, createFolderDirectory);

        imageFiles.forEach(file -> saveFilesToFolder(createFolderDirectory, file));
    }

    private void saveFilesToDatabase(List<MultipartFile> imageFiles, ProjectFolder folder, ArrayList<File> files, String mainDirectory, String folderName) {
        imageFiles.forEach(file -> {
            Path createFilePath = Paths.get(mainDirectory, folderName, file.getOriginalFilename());
            File file2 = newFile(folder, file, createFilePath);
            files.add(file2);
        });

        folder.setFiles(files);

        folderRepository.persist(folder);
    }

    private void saveFilesToFolder(Path folderDirectory, MultipartFile x) {
        if(x.getOriginalFilename() == null) return;

        Path createFilePath = folderDirectory.resolve(x.getOriginalFilename());

        try {
            Files.write(createFilePath, x.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            log.error("Cold not write file: {}", x.getOriginalFilename());
            throw new LogicCoreException("Could not write file",e);
        }
    }

    private void createFolder(ProjectFolder folder, Path createFolderDirectory) {
        try {
            if(!Files.exists(createFolderDirectory)){
                Files.createDirectories(createFolderDirectory);
            }
        } catch (IOException e) {
            log.error("Cold not create folder: {}", folder.getFolderName());
            throw new LogicCoreException("Could not create folder", e);
        }
    }

    private File newFile(ProjectFolder folder, MultipartFile file, Path createFilePath) {
        return File.builder()
                .projectFolder(folder)
                .fileLocation(createFilePath.toString())
                .fileFormat(file.getContentType())
                .build();
    }


}
