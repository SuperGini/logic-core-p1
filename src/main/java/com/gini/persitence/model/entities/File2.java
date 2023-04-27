package com.gini.persitence.model.entities;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;


/**
 * how to save images in database:
 * 1. https://stackoverflow.com/questions/38535881/hibernate-columndefinition-to-mediumblob-not-working
 * 2. https://stackoverflow.com/questions/13567155/com-mysql-jdbc-mysqldatatruncation-data-truncation-data-too-long-for-column-c
 *
 * Solution
 *
 *     Please have a look at the size of the file that you are trying to upload.
 *     Then compare that file size with the Max size allowed by your mapped Database FIELD type.
 *     For example for LOB's:
 *     TINYBLOB ≈ 255 bytes, BLOB ≈ 64KB, MEDIUMBLOB ≈ 16MB and LONGBLOB ≈ 4GB Run
 *     Use Alter table command as per your Space requirements:
 *     ALTER TABLE 'TABLE_NAME' MODIFY 'FIELD_NAME' MEDIUMBLOB;
 *     Note: It is always recommended to save a Link but not the actual file (having large sizes) in DB.
 * */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class File2 {

    @Id
    @Tsid
    private Long id;

    private String name;

    private String contentType;

    @Transient
    @Lob
    @Column(name = "file_in_bytes", columnDefinition = "MEDIUMBLOB")
    private byte[] fileInBytes;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File2 file2 = (File2) o;
        return Objects.equals(id, file2.id) && Objects.equals(name, file2.name) && Objects.equals(contentType, file2.contentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, contentType);
    }
}
