package com.gini.model.entities;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "folders")
public class ProjectFolder {

    @Id
    @Tsid
    @Column(name = "id")
    private Long id;

    @Column(name = "project_name")
    private String projectName;

    @CreationTimestamp
    @Column(name = "create_date")
    private OffsetDateTime createDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private OffsetDateTime updateDate;

    @Column(name = "documents_number")
    private Integer numberOfDocuments;

    @Column(name = "number_of_images")
    private Integer numberOfImages;

    @Column(name = "number_of_videos")
    private Integer numberOfVideos;

    @Column(name = "number_of_documents")
    private Integer numberOfOtherDocuments;

    @Column(name = "folder_capacity")
    private BigDecimal folderCapacity;

    @Column(name = "current_folder_capacity")
    private BigDecimal currentFolderCapacity;

    @Column(name = "last_update_user")
    private String lastUpdateByUser;

    @Version
    @Column(name = "version")
    private short version;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "projectFolder")
    private List<File> files;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectFolder that = (ProjectFolder) o;
        return Objects.equals(id, that.id) && Objects.equals(projectName, that.projectName) && Objects.equals(createDate, that.createDate) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return 35 * getClass().hashCode();
    }
}
