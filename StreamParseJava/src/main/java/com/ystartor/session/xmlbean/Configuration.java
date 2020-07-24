package com.ystartor.session.xmlbean;

import com.ystartor.executor.constant.ExecutorType;
import com.ystartor.io.VFS;
import com.ystartor.logging.Log;
import com.ystartor.mapping.constant.AutoMappingBehavior;
import com.ystartor.mapping.constant.AutoMappingUnknownColumnBehavior;
import com.ystartor.mapping.constant.ResultSetType;
import com.ystartor.reflection.DefaultReflectorFactory;
import com.ystartor.reflection.ReflectorFactory;
import com.ystartor.reflection.factory.DefaultObjectFactory;
import com.ystartor.reflection.factory.ObjectFactory;
import com.ystartor.session.constant.LocalCacheScope;
import com.ystartor.session.xmlbean.config.Environment;
import com.ystartor.type.JdbcType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 *
 */
public class Configuration {

    //
    protected Environment environment;
    //TODO safeRowBoundsEnabled 开关
    protected boolean safeRowBoundsEnabled;
    //TODO safeResultHandlerEnabled开关
    protected boolean safeResultHandlerEnabled = true;
    //TODO mapUnderscoreToCamelCase开关
    protected boolean mapUnderscoreToCamelCase;
    //TODO aggressiveLazyLoading开关
    protected boolean aggressiveLazyLoading;
    //TODO multipleResultSetsEnabled开关
    protected boolean multipleResultSetsEnabled = true;
    //TODO useGeneratedKeys开关
    protected boolean useGeneratedKeys;
    //TODO useColumnLabel开关
    protected boolean useColumnLabel = true;
    //TODO cacheEnabled开关
    protected boolean cacheEnabled = true;
    //TODO callSettersOnNulls开关
    protected boolean callSettersOnNulls;
    //TODO useActualParamName开关
    protected boolean useActualParamName = true;
    //TODO returnInstanceForEmptyRow开关
    protected boolean returnInstanceForEmptyRow;
    //TODO shrinkWhitespacesInSql开关
    protected boolean shrinkWhitespacesInSql;

    protected String logPrefix;
    protected Class<? extends Log> logImpl;
    protected Class<? extends VFS> vfsImpl;
    protected Class<?> defaultSqlProviderType;
    protected LocalCacheScope localCacheScope = LocalCacheScope.SESSION;
    protected JdbcType jdbcTypeForNull = JdbcType.OTHER;
    protected Set<String> lazyLoadTriggerMethods = new HashSet<>(Arrays.asList("equals", "clone", "hashCode", "toString"));
    protected Integer defaultStatementTimeout;
    protected Integer defaultFetchSize;
    protected ResultSetType defaultResultSetType;

    protected ExecutorType defaultExecutorType = ExecutorType.SIMPLE;
    protected AutoMappingBehavior autoMappingBehavior = AutoMappingBehavior.PARTIAL;
    protected AutoMappingUnknownColumnBehavior autoMappingUnknownColumnBehavior = AutoMappingUnknownColumnBehavior.NONE;


    protected Properties variables = new Properties();
    protected ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
    protected ObjectFactory objectFactory = new DefaultObjectFactory();






}
