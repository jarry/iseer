/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.exception;

/**
 * ClassName: BytesFormatException
 * Description: 字节转换异常
 *
 * @author   jarryli@gmail.com
 * @version  
 * @since    TODO
 * @Date     2011-9-7 下午09:22:11
 *
 * @see      
 */

public class ByteArrayTransferException extends Exception {

    /**  */
    
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @param message
     */
    public ByteArrayTransferException(String message) {
        super(message);
    }

    /**
     * 
     * @param message
     * @param cause
     */
    public ByteArrayTransferException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 
     * @param cause
     */
    public ByteArrayTransferException(Throwable cause) {
        super(cause);
    }
}

