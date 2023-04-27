package com.gini.services;

import com.gini.config.folder.MainFolderConfig;
import com.gini.dto.request.folder.FolderInfoRequest;
import com.gini.error.error.LogicCoreException;
import com.gini.error.error.NotFoundException;
import com.gini.persitence.model.entities.File;
import com.gini.persitence.model.entities.ProjectFolder;
import com.gini.persitence.repository.folder.ProjectFolderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional
    public void saveFilesToFolder(FolderInfoRequest folderInfo, List<MultipartFile> imageFiles){
        var folderId = Long.parseLong(folderInfo.folderId());

        folderRepository.findProjectFolderByIdAndFolderName(folderId, folderInfo.folderName())
                .ifPresentOrElse(
                        folder -> saveFileToFolderAndDatabase(imageFiles, folder),
                            () -> {throw new NotFoundException("Folder was not found");}
                );
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
