package com.gini.dto.request.folder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FolderResponse {

    private Long id;
    private String projectName;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;
    private Integer numberOfImages;
    private Integer numberOfVideos;
    private Integer numberOfOtherFiles;
    private BigDecimal folderCapacity;
    private BigDecimal currentFolderCapacity;
    private String lastUpdateByUser;
    private Long userId;
    private String username;
}
