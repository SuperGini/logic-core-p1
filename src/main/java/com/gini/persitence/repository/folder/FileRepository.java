package com.gini.persitence.repository.folder;


import com.gini.persitence.model.entities.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface FileRepository extends JpaRepository<File, Long> {


    @Query("""
                SELECT f FROM File f WHERE f.projectFolder.id = :folderId

            """)
    Page<File> findAllProjectFileWithPagination(Pageable pageable, @Param("folderId")Long folderId);



}
