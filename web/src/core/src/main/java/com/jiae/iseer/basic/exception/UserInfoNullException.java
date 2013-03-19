/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.exception;

/**
 * ClassName: UserInfoNullException
 * Description: 用户信息异常
 *
 * @author   jarryli@gmail.com
 * @version  
 * @since    TODO
 * @Date     2011-11-9 下午07:24:32
 *
 * @see      
 */

public class UserInfoNullException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 4815297908987907666L;

    public UserInfoNullException() {

    }

    /**
     * @param message
     */
    public UserInfoNullException(String message) {
        super(message);
    }

    /**
     * Constructor for InternalException.
     */
    public UserInfoNullException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for InternalException.
     */
    public UserInfoNullException(Throwable cause) {
        super(cause);
    }
}
