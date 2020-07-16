package com.ystartor.transaction.constant;

import java.sql.Connection;

/**
 * 事务隔离级别
 */
public enum  TransactionIsolationLevel {

    NONE(Connection.TRANSACTION_NONE),
    READ_COMMITIED(Connection.TRANSACTION_READ_COMMITTED),
    READ_UNCOMMITIED(Connection.TRANSACTION_READ_UNCOMMITTED),
    REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),
    SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);

    private final int level;
    TransactionIsolationLevel(int level){
        this.level = level;
    }
    public int getLevel(){
        return level;
    }

}
