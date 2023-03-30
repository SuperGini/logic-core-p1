package com.gini.model.entities;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

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
    private byte [] fileInBytes;

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
