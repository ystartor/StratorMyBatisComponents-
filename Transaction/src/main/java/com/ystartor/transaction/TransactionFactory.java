package com.ystartor.transaction;

import com.ystartor.transaction.constant.TransactionIsolationLevel;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

/**
 * 事务生成工厂
 */
public interface TransactionFactory {

    default void setProperties(Properties props){
        //
    }

    Transaction newTransaction(Connection conn);

    Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit);


}
