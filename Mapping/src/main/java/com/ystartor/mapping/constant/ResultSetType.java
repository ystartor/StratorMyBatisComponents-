package com.ystartor.mapping.constant;

import java.sql.ResultSet;

/**
 * TODO ResultSetType什么地方用呢？java.sql.ResultSet
 */
public enum ResultSetType {

    DEFAULE(-1),
    FORWARD_ONLY(ResultSet.TYPE_FORWARD_ONLY),
    SCROLL_INSENSITIVE(ResultSet.TYPE_SCROLL_INSENSITIVE),
    SCROLL_SENSITIVE(ResultSet.TYPE_SCROLL_SENSITIVE);

    private final int value;
    ResultSetType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
