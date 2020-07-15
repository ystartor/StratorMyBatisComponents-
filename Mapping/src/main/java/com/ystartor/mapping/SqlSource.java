package com.ystartor.mapping;

/**
 *  表示从XML文件或注释读取的映射语句的内容。
 *  它创建将从用户接收到的输入参数中传递到数据库的SQL。
 */
public interface SqlSource {

    //TODO 这个parameterObject参数是啥？
    BoundSql getBoundSql(Object parameterObject);


}
