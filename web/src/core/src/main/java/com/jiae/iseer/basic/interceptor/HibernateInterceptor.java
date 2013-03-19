/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.interceptor;
import java.io.Serializable;
import java.util.Date;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

/**
 * ClassName: HibernateInterceptor
 * Description: hibernate的拦截节器
 *
 * @author   jarryli@gmail.com
 * @version  
 * @since    TODO
 * @Date     2011-11-1 下午07:24:15
 *
 * @see      
 */

public class HibernateInterceptor extends EmptyInterceptor {
    
    private static final long serialVersionUID = 1L;

    /**
     * 向数据库插入数据时填充创建时间字段
     * @author lichunping
     * @since 1.0.0
     */
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {

        for (int i = 0; i < propertyNames.length; i++) {
            if ("createTime".equals(propertyNames[i])) {
                state[i] = new Date();
                break;
            }
        }
        return true;

    }
}
