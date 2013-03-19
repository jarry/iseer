/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.exception;

/**
 * ClassName: BytesFormatException
 * Description: 图片格式异常
 *
 * @author   jarryli@gmail.com
 * @version  
 * @since    TODO
 * @Date     2011-9-7 下午09:22:11
 *
 * @see      
 */

public class ImageFileFormatException extends Exception {

    /**  */
    
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public ImageFileFormatException() {

    }

    /**
     * @param message
     *            异常信息
     */
    public ImageFileFormatException(String message) {
        super(message);
    }

    /**
     * 
     * @param message
     *            异常信息
     * @param cause
     *            异常原因
     */
    public ImageFileFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 
     * @param cause
     *            异常原因
     */
    public ImageFileFormatException(Throwable cause) {
        super(cause);
    }
}

