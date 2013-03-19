/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
 * 在映射时基类中的属性才对子类有效，供实体类继承，需要有时间记录等
 * 
 * @author lichunping 
 * @version 1.0.0
 */

@MappedSuperclass
public abstract class BaseObject implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Date createTime;

    @Column(updatable = false)
    private Date modifyTime;

    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

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