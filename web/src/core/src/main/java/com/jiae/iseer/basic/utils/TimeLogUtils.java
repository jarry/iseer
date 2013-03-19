/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: TimeLogUtils
 * Description: 记录性能相关数据的工具类。
 *
 * @author   jarryli@gmail.com
 * @version  
 * @since    TODO
 * @Date     2011-10-18 下午02:40:26
 *
 * @see      
 */

public class TimeLogUtils {

    protected static Logger logger = LoggerFactory.getLogger(TimeLogUtils.class);
    public static final String DELIMITER = "-----";
    /**
     * debug 级别记录
     * @param className 
     * @param doWhat 
     */
    public static void debug(String className, String doWhat) {
        logger.debug(className + DELIMITER + doWhat);
    }
    /**
     * info 级别记录
     * @param className 
     * @param doWhat 
     */
    public static void info(String className, String doWhat) {
        logger.info(className + DELIMITER + doWhat);
    }
}
