package com.gini.persitence.model.enums;

import lombok.Getter;

@Getter
public enum FileType {

    VIDEO("video"),
    AUDIO("audio"),
    FILE("file"),
    OTHER("other"),
    PICTURE("picture");


    private final String type;

    FileType(String type) {
        this.type = type;
    }

}
