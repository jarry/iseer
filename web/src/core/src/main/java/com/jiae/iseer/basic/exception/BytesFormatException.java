/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.exception;

/**
 * ClassName: BytesFormatException
 * Description: 字节流长度或格式不对时异常
 *
 * @author   jarryli@gmail.com
 * @version  
 * @since    TODO
 * @Date     2011-9-7 下午09:22:11
 *
 * @see      
 */

public class BytesFormatException extends Exception {

    /**  */
    
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public BytesFormatException() {

    }

    /**
     * @param message
     */
    public BytesFormatException(String message) {
        super(message);
    }

    /**
     * Constructor for InternalException.
     */
    public BytesFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for InternalException.
     */
    public BytesFormatException(Throwable cause) {
        super(cause);
    }
}

