package com.gini.model.entities;


import com.gini.model.enums.FileType;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "files")
public class File {

    @Id
    @Tsid
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "file_format",length = 10)
    private String fileFormat;

    @Column(name = "file_type", length = 100)
    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @Column(name = "file")
    @Lob
    private byte [] file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private ProjectFolder projectFolder;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return Objects.equals(id, file.id) && Objects.equals(fileFormat, file.fileFormat) && Objects.equals(fileType, file.fileType);
    }

    @Override
    public int hashCode() {
        return 10 * getClass().hashCode();
    }
}
