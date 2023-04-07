package com.gini.services;

import com.gini.dto.request.folder.CreateFolderRequest;
import com.gini.dto.request.folder.FolderResponse;
import com.gini.dto.request.folder.FolderResponsePagination;
import com.gini.mappers.response.FolderMapper;
import com.gini.persitence.dto.FolderInfo;
import com.gini.persitence.model.entities.ProjectFolder;
import com.gini.persitence.model.entities.User;
import com.gini.persitence.repository.folder.ProjectFolderRepository;
import com.gini.persitence.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                .user(user)
                .build();

        ProjectFolder savedProjectFolder = projectFolderRepository.persist(projectFolder);

        return folderMapper.mapToResponse(savedProjectFolder);
    }

    @Transactional
    public FolderResponsePagination getAllFoldersByIdWithPagination(Long userId, Integer pageNumber){
        FolderResponsePagination response = new FolderResponsePagination();

        Pageable pageWithThreeElements = PageRequest.of(pageNumber, 3);

        Page<FolderInfo> page =  projectFolderRepository
                                            .findProjectsByUserIdWithPagination(userId,pageWithThreeElements);

        var totalPages = page.getTotalPages();
        response.setTotalPages(totalPages);

        page.stream()
                    .map(folderMapper::mapToResponse)
                    .forEach(folderResponse -> response.getFolderResponses().add(folderResponse));

        return response;
    }

}
