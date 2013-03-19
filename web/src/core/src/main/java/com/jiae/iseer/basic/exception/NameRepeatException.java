/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.exception;

/**
 * ClassName: BytesFormatException
 * Description: 自定义重名时的异常
 *
 * @author   jarryli@gmail.com
 * @version  
 * @since    TODO
 * @Date     2011-9-7 下午09:22:11
 *
 * @see      
 */

public class NameRepeatException extends Exception {

    /**  */
    
    private static final long serialVersionUID = 1L;

    /**
     * 默认构造函数
     */
    public NameRepeatException() {
        super();
    }

    /**
     * @param message
     *            异常信息
     * @param cause
     *            Throwable对象 Constructor for InternalException.
     */
    public NameRepeatException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     *            异常信息
     */
    public NameRepeatException(String message) {
        super(message);
    }

    /**
     * @param cause
     *            Throwable对象 Constructor for InternalException.
     */
    public NameRepeatException(Throwable cause) {
        super(cause);
    }
}

