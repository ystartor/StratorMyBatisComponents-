package com.ystartor.exception;

/**
 * 自定义异常基础类
 */
public class StratorException extends RuntimeException {
    private static final long serialVersionUID = 3880206998166270511L;

    public StratorException(){
        super();
    }

    public StratorException(String message){
        super(message);
    }

    public StratorException(Throwable cause){
        super(cause);
    }

    public StratorException(String message, Throwable cause){
        super(message, cause);
    }


}
