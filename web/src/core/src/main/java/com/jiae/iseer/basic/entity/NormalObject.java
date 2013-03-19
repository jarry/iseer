/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.entity;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 * ClassName: BaseObject
 * Description: TODO Add function description
 *
 * @author   jarryli@gmail.com
 * @version  
 * @since    TODO
 * @Date     2011-10-1 下午10:33:16
 *
 * @see      
 */

/**
 * Base class for Model objects. 
 * Child objects should implement toString(), equals() and hashCode().
 * 设置了MappedSuperclass注释
 * 这是最普通的类，供一般的非实体类继承
 * 
 * @author lichunping 
 * @version 1.0.0
 */

@MappedSuperclass
public abstract class NormalObject implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * Returns a multi-line String with key=value pairs.
     * 
     * @return a String representation of this class.
     */
    public abstract String toString();

    /**
     * Compares object equality. When using Hibernate, the primary key should not be a part of this comparison.
     * 
     * @param o
     *            object to compare to
     * @return true/false based on equality tests
     */
    public abstract boolean equals(Object o);

    /**
     * When you override equals, you should override hashCode. See "Why are equals() and hashCode() importation" for
     * more information: http://www.hibernate.org/109.html
     * 
     * @return hashCode
     */
    public abstract int hashCode();
}