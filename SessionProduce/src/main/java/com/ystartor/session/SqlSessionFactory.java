package com.ystartor.session;

import com.ystartor.session.executor.ExecutorType;
import com.ystartor.session.xmlbean.Configuration;
import com.ystartor.transaction.constant.TransactionIsolationLevel;

import java.sql.Connection;

/**
 * 生成sqlSession的接口类
 */
public interface SqlSessionFactory {
    // 打开session——这个应该设置了更多默认值
    SqlSession openSession();
    // 是否自动提交
    SqlSession openSession(boolean autoCommit);
    // 自己设置一个连接
    SqlSession openSession(Connection connection);
    // 设置事务隔离级别来创建一个会话
    SqlSession openSession(TransactionIsolationLevel level);
    // 通过设置执行器来创建一个会话
    SqlSession openSession(ExecutorType execType);
    // 通过设置执行器、是否自动提交来创建一个会话
    SqlSession openSession(ExecutorType execType, boolean autoCommit);
    // 通过设置执行器、事务隔离级别来创建一个会话
    SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level);
    // 通过创建一个连接
    SqlSession openSession(ExecutorType execType, Connection connection);
    // 获取一个配置
    Configuration getConfiguration();
}
