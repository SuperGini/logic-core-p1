package com.gini.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogicCoreConstants {

    public static final Pattern EMAIL_PATTERN =  Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    public static final Pattern USERNAME_PATTERN =  Pattern.compile("^[A-Za-z]{5,60}$");
}
