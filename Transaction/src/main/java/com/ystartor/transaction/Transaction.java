package com.ystartor.transaction;

import java.sql.Connection;

/**
 *
 */
public interface Transaction {

    Connection getConnection();

    void commit();

    void rollback();

    void close();

    Integer getTimeout();

}

