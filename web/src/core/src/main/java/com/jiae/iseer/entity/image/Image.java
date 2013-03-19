/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.entity.image;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.jiae.iseer.basic.entity.BaseObject;
//import com.jiae.iseer.entity.theme.Theme;
//import com.jiae.iseer.entity.system.System;

/**
 * ClassName: User
 * Description: User实体类
 *
 * @author   jarryli@gmail.com
 * @version  
 * @since    1.0.0
 * @Date     2013-2-1 下午10:33:16
 *
 * @see      
 */


@Entity
@Table(name="images")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Image extends BaseObject implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String type;
    private String name;
    private Integer width;
    private Integer height;
    private String fromUrl;
    
//    private List<Theme> themeList;
//    private System system;
    
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,  optional = true)
//    private UserInfo userInfo;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getFromUrl() {
        return fromUrl;
    }

    public void setFromUrl(String fromUrl) {
        this.fromUrl = fromUrl;
    }
    
    ///
    
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if (!(other instanceof Image)) {
            return false;
        }
        Image castOther = (Image) other;
        return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }
}
