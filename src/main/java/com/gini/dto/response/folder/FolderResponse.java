package com.gini.dto.response.folder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gini.persitence.model.enums.FileType;
import com.gini.persitence.model.enums.FolderType;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FolderResponse {

    private String id;
    private String projectName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime createDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime updateDate;
    private Integer numberOfImages;
    private Integer numberOfVideos;
    private Integer numberOfOtherFiles;
    private BigDecimal folderCapacity;
    private BigDecimal currentFolderCapacity;
    private String lastUpdateByUser;
    private String userId;
    private String username;
    private String folderType;
}
