package com.ystartor.mapping;

import com.ystartor.session.parsexml.Configuration;
import com.ystartor.type.JdbcType;

/**
 *
 */
public class ParameterMapping {

    private Configuration configuration;

    private String property;
    private ParameterMode mode;
    private Class<?> javaType = Object.class;
    private JdbcType jdbcType;
    private Integer numericScale;
    //TODO typehandler

}
