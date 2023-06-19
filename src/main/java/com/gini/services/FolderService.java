package com.gini.services;

import com.gini.dto.request.folder.CreateFolderRequest;
import com.gini.dto.response.folder.FolderResponse;
import com.gini.dto.response.folder.FolderResponsePagination;
import com.gini.error.error.LogicCoreException;
import com.gini.error.error.NotFoundException;
import com.gini.mappers.response.FolderMapper;
import com.gini.persitence.dto.FolderInfo;
import com.gini.persitence.model.entities.ProjectFolder;
import com.gini.persitence.model.entities.User;
import com.gini.persitence.model.enums.FolderType;
import com.gini.persitence.repository.folder.ProjectFolderRepository;
import com.gini.persitence.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Find by Id anti-pattern
 * https://vladmihalcea.com/spring-data-jpa-findbyid/
 * throws SQLIntegrityConstraintViolationException if user is not found;
 * */

@Slf4j
@Service
@RequiredArgsConstructor
public class FolderService {


    private final UserRepository userRepository;
    private final ProjectFolderRepository projectFolderRepository;
    private final FolderMapper folderMapper;


    @Transactional
    public FolderResponse createProjectFolder(CreateFolderRequest createFolderRequest) {
        Long userId = Long.parseLong(createFolderRequest.userId());
        String projectName = createFolderRequest.projectName();

        User user = userRepository.getReferenceById(userId); //use this not findById() => anti-pattern

        ProjectFolder projectFolder = ProjectFolder.builder()
                .folderName(projectName)
                .folderType(FolderType.fromString(createFolderRequest.folderType()))
                .user(user)
                .build();

        ProjectFolder savedProjectFolder = projectFolderRepository.persist(projectFolder);

        return folderMapper.mapToResponse(savedProjectFolder);
    }

    @Transactional(readOnly = true)
    public FolderResponsePagination getAllFoldersByIdWithPagination(String userId, Integer pageNumber, Integer pageElements){
        var userIdAsLong = Long.parseLong(userId);

        FolderResponsePagination response = new FolderResponsePagination();
        Pageable pageWithElements = PageRequest.of(pageNumber, pageElements);

        Page<FolderInfo> page =  projectFolderRepository
                                            .findProjectsByUserIdWithPagination(userIdAsLong,pageWithElements);

        var totalPages = page.getTotalPages();
        var totalElements = (int) page.getTotalElements();

        response.setTotalPages(totalPages);
        response.setTotalElements(totalElements);

        page.stream()
                    .map(folderMapper::mapToResponse)
                    .forEach(folderResponse -> response.getFolderResponses().add(folderResponse));

        return response;
    }

    @Transactional(readOnly = true)
    public FolderResponsePagination getAllFoldersWithPagination(Integer pageNumber, Integer pageElements){
        FolderResponsePagination response = new FolderResponsePagination();
        Pageable pageWithElements = PageRequest.of(pageNumber, pageElements);

        Page<FolderInfo> page = projectFolderRepository.findAllProjectsWithPagination(pageWithElements);

   //     var totalPages = page.getTotalPages();
        var totalElements = (int) page.getTotalElements();

      //  response.setTotalPages(totalPages);
        response.setTotalElements(totalElements);

        page.stream()
                .map(folderMapper::mapToResponse)
                .forEach(folderResponse -> response.getFolderResponses().add(folderResponse));

        return response;
    }

    @Transactional
    public int deleteUserFolder(String folderId, String userId){
        var folderID = Long.parseLong(folderId);
        var userID = Long.parseLong(userId);

        projectFolderRepository.findById(folderID)
                .ifPresentOrElse(x -> deleteFolderFromDatabaseAndLocally(userID, x),
                        () -> {throw new NotFoundException("Folder was not found");}
                );

        return 1;

    }

    private void deleteFolderFromDatabaseAndLocally(long userID, ProjectFolder folder) {
        var userIdFromDB = folder.getUser().getId();
        var folderName = folder.getFolderName();

        if(userIdFromDB != userID){
            throw new LogicCoreException("You are not allowed to delete the folder");
        }

        deleteFolderLocally(folderName);
        projectFolderRepository.delete(folder); // throws OptimisticLockingFailureException if version is different
                                                //or entity was already deleted
    }

    private void deleteFolderLocally(String folderName){
        var folder = folderName.replace(" ", "_");
        var folderPath = Path.of("rootDirectory/" +folder);
        try {
            if(Files.isDirectory(folderPath)){
                FileSystemUtils.deleteRecursively(folderPath);
            }

        } catch (IOException e) {
            throw new RuntimeException("Can't delete directory: " + folderName);
        }
    }

}
