package com.ystartor.session.xmlbean;

import com.ystartor.io.VFS;
import com.ystartor.logging.Log;
import com.ystartor.session.xmlbean.config.Environment;

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


}
