/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.dao;

import java.sql.Types;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.Type;

/**
 * ClassName: MySqlOrder
 * Description: TODO Add function description
 *
 * @author   jarryli@gmail.com
 * @version  
 * @since    TODO
 * @Date     2011-11-8 上午11:43:20
 *
 * @see      
 */

public class MySqlOrder extends Order {
    
    private static final long serialVersionUID = 1L;


    private boolean ascending;
    private boolean ignoreCase;
    private String propertyName;

    protected MySqlOrder(String propertyName, boolean ascending) {
        super(propertyName, ascending);
        this.propertyName = propertyName;
        this.ascending = ascending;
    }
    /**
     * 转换成mysql 字符串
     * @param criteria 
     * @param criteriaQuery 
     * @throws HibernateException hibernate异常
     * @author   lichunping
     * @version  1.0.0
     */
    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
        String[] columns = criteriaQuery.getColumnsUsingProjection(criteria, this.propertyName);
        Type type = criteriaQuery.getTypeUsingProjection(criteria, this.propertyName);
        StringBuffer fragment = new StringBuffer();
        for (int i = 0; i < columns.length; i++) {
            SessionFactoryImplementor factory = criteriaQuery.getFactory();
            boolean isVarchar = (type.sqlTypes(factory)[i] == Types.VARCHAR);

            // 如果是字符串字段则需要按照中文排序则需要加入该函数的引用
            if (isVarchar) {
                fragment.append("convert(");
            }

            boolean lower = this.ignoreCase && isVarchar;
            if (lower) {
                fragment.append(factory.getDialect().getLowercaseFunction()).append('(');
            }
            fragment.append(columns[i]);
            if (lower) {
                fragment.append(')');
            }
            // 如果是字符串字段则需要按照中文排序则需要加入该函数的引用
            if (isVarchar) {
                fragment.append(" using gbk)");
            }

            fragment.append((this.ascending) ? " asc" : " desc");
            if (i >= columns.length - 1) {
                continue;
            }
            fragment.append(", ");
        }
        return fragment.toString();
    }
    /**
     * 升序
     * @param propertyName 
     * @return
     * @author   jarryli@gmail.com
     * @version  1.0.0
     */
    public static MySqlOrder asc(String propertyName) {
        return new MySqlOrder(propertyName, true);
    }
    /**
     * 降序
     * @param propertyName 
     * @return
     * @author   jarryli@gmail.com
     * @version  1.0.0
     */
    public static MySqlOrder desc(String propertyName) {
        return new MySqlOrder(propertyName, false);
    }

    
}
