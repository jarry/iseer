/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.entity.user;

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
 * @Date     2011-11-1 下午10:33:16
 *
 * @see      
 */


@Entity
@Table(name="users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends BaseObject implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Integer userId;
    private String name;
    private String nickname;
    private String password;
    private Integer active;
    
//    private List<Theme> themeList;
//    private System system;
    
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,  optional = true)
//    private UserInfo userInfo;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getNickname() {
        return nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }  
    
//    @OneToOne(cascade = { CascadeType.ALL}, fetch = FetchType.LAZY)
//    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", unique = true)
////    @OneToOne
////    @JoinColumn(name = "USER_ID", unique = true, nullable = false)
//    public UserInfo getUserInfo() {
//        return userInfo;
//    }

//    public void setUserInfo(UserInfo userInfo) {
//        this.userInfo = userInfo;
//    }

//    @OneToMany(cascade = { CascadeType.ALL}, fetch = FetchType.LAZY)
//    @JoinTable(name = "user_theme", 
//            joinColumns = {@JoinColumn(name = "USER_ID")}, 
//            inverseJoinColumns = {@JoinColumn(name = "THEME_ID")}
//    )
//    public List<Theme> getThemeList() {
//        return themeList;
//    }
//
//    public void setThemeList(List<Theme> themeList) {
//        this.themeList = themeList;
//    }
    
//    @OneToOne(cascade = { CascadeType.ALL}, fetch = FetchType.LAZY)
//    @JoinTable(name = "user_system", 
//            joinColumns = {@JoinColumn(name = "USER_ID")}, 
//            inverseJoinColumns = {@JoinColumn(name = "SYSTEM_ID")}
//    )
//    public System getSystem() {
//        return system;
//    }
//
//    public void setSystem(System system) {
//        this.system = system;
//    }
    
    
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
        if (!(other instanceof User)) {
            return false;
        }
        User castOther = (User) other;
        return new EqualsBuilder().append(this.getUserId(), castOther.getUserId()).isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getUserId()).toHashCode();
    }
}
