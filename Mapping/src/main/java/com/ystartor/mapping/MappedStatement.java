package com.ystartor.mapping;

import com.ystartor.mapping.constant.ResultSetType;
import com.ystartor.mapping.constant.StatementType;
import com.ystartor.session.parsexml.Configuration;

/**
 *
 */
//TODO
public final class MappedStatement {

    private String resource;
    private Configuration configuration;
    private String id;
    //TODO 这个不知道干嘛用的
    private Integer fetchSize;
    private Integer timeout;
    private StatementType statementType;
    //TODO 用处……
    private ResultSetType resultSetType;
    


}
