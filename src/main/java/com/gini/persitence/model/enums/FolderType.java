package com.gini.persitence.model.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum FolderType {

    VIDEO("video"),
    AUDIO("audio"),
    FILE("file"),
    OTHER("other"),
    PICTURE("picture");


    private final String type;

    FolderType(String type) {
        this.type = type;
    }

    public static FolderType fromString(String type) {
        return Arrays.stream(FolderType.values())
                .filter(x -> x.getType().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Incorrect value for enum"));
    }
}
