package com.ystartor.session.xmlbean.config;

import com.ystartor.transaction.TransactionFactory;

import javax.sql.DataSource;

/**
 *  env
 */
public class Environment {

    private final String id;
    private final TransactionFactory transactionFactory;
    private final DataSource dataSource;

    public Environment(String id, TransactionFactory transactionFactory, DataSource dataSource){
        if (null == id){
            throw new IllegalArgumentException("id 不能为空");
        }
        if (null == transactionFactory){
            throw new IllegalArgumentException("transactionFactory不能为空")
        }
        this.id = id;
        if (null == dataSource){
            throw new IllegalArgumentException("dataSource不能为空");
        }
        this.transactionFactory = transactionFactory;
        this.dataSource = dataSource;
    }

    //TODO 这个静态内部类
    public static class Builder{
        private final String id;
        private TransactionFactory transactionFactory;
        private DataSource dataSource;

        public Builder(String id){
            this.id = id;
        }

        public Builder transactionFactory(TransactionFactory transactionFactory){
            this.transactionFactory = transactionFactory;
            return this;
        }

        public Builder dataSource(DataSource dataSource){
            this.dataSource = dataSource;
            return this;
        }

        public String getId() {
            return id;
        }

        public Environment build(){
            return new Environment(this.id, this.transactionFactory, this.dataSource);
        }
    }


    public String getId() {
        return id;
    }

    public TransactionFactory getTransactionFactory() {
        return transactionFactory;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
