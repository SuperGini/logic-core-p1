package com.gini.persitence.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class FolderInfo {

    private Long userid;
    private Long folderId;
    private String username;
    private String projectName;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;
    private String lastUpdateByUser;

    public FolderInfo(Long userid,
                      Long folderId,
                      String username,
                      String projectName,
                      OffsetDateTime createDate,
                      OffsetDateTime updateDate,
                      String lastUpdateByUser) {
        this.userid = userid;
        this.folderId = folderId;
        this.username = username;
        this.projectName = projectName;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.lastUpdateByUser = lastUpdateByUser;
    }
}
