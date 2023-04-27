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

import java.util.Optional;

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
                folder.lastUpdateByUser,
                folder.folderType
            )
             FROM ProjectFolder folder
             JOIN folder.user AS user
             WHERE folder.user.id = :userId
                    
            """)
    Page<FolderInfo> findProjectsByUserIdWithPagination(@Param("userId") Long userId, Pageable pageable);


    @Query("""
            SELECT new com.gini.persitence.dto.FolderInfo(
                   user.id,
                   folder.id,
                   user.username,
                   folder.folderName,
                   folder.createDate,
                   folder.updateDate,
                   folder.lastUpdateByUser,
                   folder.folderType
               )
                FROM ProjectFolder folder
                JOIN folder.user AS user
            """)
    Page<FolderInfo> findAllProjectsWithPagination(Pageable pageable);

    Optional<ProjectFolder> findProjectFolderByIdAndFolderName(Long folderId, String folderName);























//    @Query("""
//            SELECT folder FROM ProjectFolder folder LEFT JOIN folder.files AS files WHERE folder.id = :folderId
//            """)
//    ProjectFolder findProject2(@Param("folderId") Long folderId);


    //    @Modifying(flushAutomatically = true)
//    @Query("""
//        DELETE FROM ProjectFolder AS folder WHERE  folder.id = :folderId AND folder.user.id = :userId AND EXISTS(SELECT f FROM folder.files f WHERE f.projectFolder.id = :folderId)
//        """)
//    int delete(@Param("folderId") Long folderId, @Param("userId") Long userId);


//    @Query("""
//        SELECT folder FROM ProjectFolder folder
//        LEFT JOIN FETCH folder.files AS files
//        WHERE folder.id = :folderId AND folder.user.id = :userId
//        """)
//    ProjectFolder findFolder(@Param("folderId") Long folderId, @Param("userId") Long userId);


}
