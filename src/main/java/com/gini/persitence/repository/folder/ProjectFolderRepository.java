package com.gini.persitence.repository.folder;

import com.gini.persitence.dto.FolderInfo;
import com.gini.persitence.model.entities.ProjectFolder;
import com.gini.persitence.repository.HibernateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.MANDATORY)
public interface ProjectFolderRepository extends HibernateRepository<ProjectFolder>, JpaRepository<ProjectFolder, Long> {

    @Query("""
            SELECT new com.gini.persitence.dto.FolderInfo(
                user.id,
                folder.id,
                user.username,
                folder.folderName,
                folder.createDate,
                folder.updateDate,
                folder.lastUpdateByUser
            )
             FROM ProjectFolder folder
             JOIN folder.user AS user
             WHERE folder.user.id = :userId
                    
            """)
    Page<FolderInfo> findProjectsByUserIdWithPagination(@Param("userId") Long userId, Pageable pageable);

}
