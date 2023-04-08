package com.gini.mappers.response;

import com.gini.dto.request.folder.CreateFolderRequest;
import com.gini.dto.response.FolderResponse;
import com.gini.mappers.Mapper;
import com.gini.persitence.dto.FolderInfo;
import com.gini.persitence.model.entities.ProjectFolder;
import org.springframework.stereotype.Component;

@Component
public class FolderMapper implements Mapper<ProjectFolder, CreateFolderRequest, FolderResponse> {


    @Override
    public FolderResponse mapToResponse(ProjectFolder value) {
        return FolderResponse.builder()
                .id(String.valueOf(value.getId()))
                .projectName(value.getFolderName())
                .createDate(value.getCreateDate())
                .updateDate(value.getUpdateDate())
                .numberOfImages(value.getNumberOfImages())
                .numberOfVideos(value.getNumberOfVideos())
                .numberOfOtherFiles(value.getNumberOfOtherFiles())
                .folderCapacity(value.getFolderCapacity())
                .currentFolderCapacity(value.getCurrentFolderCapacity())
                .lastUpdateByUser(value.getLastUpdateByUser())
                .userId(String.valueOf(value.getUser().getId()))
                .build();
    }

    public FolderResponse mapToResponse(FolderInfo folderInfo) {
        return FolderResponse.builder()
                .id(String.valueOf(folderInfo.getFolderId()))
                .userId(String.valueOf(folderInfo.getUserid()))
                .username(folderInfo.getUsername())
                .projectName(folderInfo.getProjectName())
                .createDate(folderInfo.getCreateDate())
                .updateDate(folderInfo.getUpdateDate())
                .lastUpdateByUser(folderInfo.getLastUpdateByUser())
                .build();
    }
}
