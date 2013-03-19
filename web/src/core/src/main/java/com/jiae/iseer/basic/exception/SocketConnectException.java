/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.exception;

/**
 * ClassName: BusiLogicException
 * Description: Socket 连接相关异常
 *
 * @author   jarryli@gmail.com
 * @version  
 * @since    TODO
 * @Date     2011-10-7 下午10:00:32
 *
 * @see      
 */

public class SocketConnectException extends Exception {

    /**  */
    
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @param message
     */
    public SocketConnectException(String message) {
        super(message);
    }

    /**
     * Constructor for InternalException.
     */
    public SocketConnectException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for InternalException.
     */
    public SocketConnectException(Throwable cause) {
        super(cause);
    }
}
