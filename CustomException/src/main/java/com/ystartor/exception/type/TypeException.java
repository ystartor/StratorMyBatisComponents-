package com.ystartor.exception.type;

import com.ystartor.exception.PersistenceException;

/**
 *
 */
public class TypeException extends PersistenceException {

    private static final long serialVersionUID = 8614420898975117130L;

    public TypeException(){
        super();
    }

    public TypeException(String message){
        super(message);
    }

    public TypeException(Throwable cause){
        super(cause);
    }

    public TypeException(String message, Throwable cause){
        super(message, cause);
    }

}
