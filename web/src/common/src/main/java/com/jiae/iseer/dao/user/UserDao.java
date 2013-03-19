/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.dao.user;

import java.util.List;

import org.hibernate.Session;

import com.jiae.iseer.basic.dao.GenericDao;
import com.jiae.iseer.entity.user.User;

/**
 * ClassName: UserDao
 * Description: user dao层对外接口
 * 
 * @author   <a href="mailto:lichunpin@baidu.com">LiChunping</a>
 * @version  
 * @since    TODO
 * @Date     2011-9-27 下午01:56:59
 *
 * @see      
 */

public interface UserDao extends GenericDao<User,Integer> {  

    /**
     * getUserById:
     * 根据userId获取user对象
     * @param userId
     * @return user对象
     * @author lichunping    
     * @since 1.0.0
     */
    public User getByUserId(Integer userId);
    
    
    /**
     * deleteByName:
     * 根据name删除该user
     * @author lichunping    
     * @since 1.0.0
     */
    public int deleteByName(String name);
    
    /**
     * getByName:
     * 根据名称获取user信息
     * @param name
     * @return user列表
     * @author lichunping    
     * @since 1.0.0
     */
    public User getByName(String name);
    
    /**
     * getUserList:
     * 查询全部user列表
     * @return user列表
     * @author lichunping    
     * @since 1.0.0
     */
    public List<User> getUserList();
    
    /**
     * 提供一个获取的session供service调用
     * 多个更新时采用同一个session提交
     * getSession:
     *
     * @return      
     * @since
     */
    public Session getSession();
    
}
