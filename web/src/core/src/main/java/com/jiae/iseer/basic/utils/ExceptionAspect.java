/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.utils;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jiae.iseer.basic.exception.BusiLogicException;
import com.jiae.iseer.basic.exception.BytesFormatException;
import com.jiae.iseer.basic.exception.UserInfoNullException;

/**
 * ClassName: ExceptionAspect
 * Description: 异常处理切面，记录日志并报警
 *
 * @author   jarryli@gmail.com
 * @version  
 * @since    TODO
 * @Date     2011-11-9 下午07:03:48
 *
 * @see      
 */

@Aspect
public class ExceptionAspect {

    private static  Logger logger = LoggerFactory.getLogger(ExceptionAspect.class);
    
    /**
     * dao 层异常切面
     * @author lichunping    
     * @since 1.0.0
     * @param ex 
     */
    @AfterThrowing(pointcut = "execution(* com.jiae.iseer.dao.impl.*.*.*(..))", throwing = "ex")
    public void doDaoException1(Exception ex) {
        if (!(ex instanceof BusiLogicException)) {
            this.doRealWork(ex);
        }
    }
    /**
     * dao 层异常切面
     * @author lichunping    
     * @since 1.0.0
     * @param ex 
     */
    @AfterThrowing(pointcut = "execution(* com.jiae.iseer.basic.dao.impl.GenericDaoImpl.*(..))", throwing = "ex")
    public void doDaoException2(Exception ex) {
        this.doRealWork(ex);
    }
    /**
     * service 层异常切面
     * @author lichunping    
     * @since 1.0.0
     * @param ex 
     */
    @AfterThrowing(pointcut = "execution(* com.jiae.iseer.service.impl.*.*.*(..))", throwing = "ex")
    public void doServiceException(Exception ex) {
        if (!(ex instanceof BytesFormatException
                || ex instanceof UserInfoNullException 
                || ex instanceof BusiLogicException)) {
            this.doRealWork(ex);
        }
    }

    /**
     * 对于异常的真正处理
     * 
     * @author lichunping    
     * @since 1.0.0
     * @param ex
     */
    private void doRealWork(Exception ex) {
        LoggerUtils.error(logger, "dao exception logged in ExceptionAspect", ex);
    }

}
