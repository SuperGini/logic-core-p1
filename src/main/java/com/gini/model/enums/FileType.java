package com.gini.model.enums;

import lombok.Getter;

@Getter
public enum FileType {

    DOCUMENT("document"),
    VIDEO("video"),
    PICTURE("picture"),
    OTHER("other");


    private final String type;

    FileType(String type) {
        this.type = type;
    }

}
