package com.gini.mappers.response;

import com.gini.dto.request.folder.CreateFolderRequest;
import com.gini.dto.request.folder.FolderResponse;
import com.gini.mappers.Mapper;
import com.gini.persitence.dto.FolderInfo;
import com.gini.persitence.model.entities.ProjectFolder;
import org.springframework.stereotype.Component;

@Component
public class FolderMapper implements Mapper<ProjectFolder, CreateFolderRequest, FolderResponse> {


    @Override
    public FolderResponse mapToResponse(ProjectFolder value) {
        return FolderResponse.builder()
                .id(value.getId())
                .projectName(value.getFolderName())
                .createDate(value.getCreateDate())
                .updateDate(value.getUpdateDate())
                .numberOfImages(value.getNumberOfImages())
                .numberOfVideos(value.getNumberOfVideos())
                .numberOfOtherFiles(value.getNumberOfOtherFiles())
                .folderCapacity(value.getFolderCapacity())
                .currentFolderCapacity(value.getCurrentFolderCapacity())
                .lastUpdateByUser(value.getLastUpdateByUser())
                .userId(value.getUser().getId())
                .build();
    }

    public FolderResponse mapToResponse(FolderInfo folderInfo) {
        return FolderResponse.builder()
                .id(folderInfo.getFolderId())
                .userId(folderInfo.getUserid())
                .username(folderInfo.getUsername())
                .projectName(folderInfo.getProjectName())
                .createDate(folderInfo.getCreateDate())
                .updateDate(folderInfo.getUpdateDate())
                .lastUpdateByUser(folderInfo.getLastUpdateByUser())
                .build();
    }
}
