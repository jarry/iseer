/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.service.user;

import java.util.List;

import com.jiae.iseer.entity.user.User;
//import com.jiae.iseer.entity.system.System;

/**
 * ClassName: UserService
 * Description: 对外接口
 * 
 * @author   <a href="mailto:lichunpin@baidu.com">LiChunping</a>
 * @version  
 * @since    TODO
 * @Date     2011-9-27 下午01:56:59
 *
 * @see      
 */

public interface UserService {
    
    
    /**
     * get:
     * 根据id记录来获取user对象
     * @param id
     * @return user对象
     * @author lichunping    
     * @since 1.0.0
     */
    public User get(Integer id) throws Exception;
    
    /**
     * getById:
     * 根据userId获取user信息
     * @param userId
     * @return user对象
     * @author lichunping    
     * @since 1.0.0
     */
    public User getByUserId(Integer userId) throws Exception;
    
    
    /**
     * getSystemId:
     * 根据userId获取system的id
     * @param userId
     * @return systemId
     * @author lichunping    
     * @since 1.0.0
     */
//    public Integer getSystemId(Integer userId) throws Exception;
    
    
    /**
     * getSystem:
     * 根据userId记录来获取system对象
     * @param userId
     * @return system对象
     * @author lichunping    
     * @since 1.0.0
     */
//    public System getSystem(Integer userId) throws Exception;
    
    
    /**
     * getUserByName:
     * 根据name获取user信息
     * @param name
     * @return user对象
     * @author lichunping    
     * @since 1.0.0
     */
    public User getUserByName(String name) throws Exception;
    
    
    /**
     * delete:
     * 根据userId删除user
     * @param id
     * @author lichunping    
     * @since 1.0.0
     */
    public void delete(Integer userId);
    
    /**
     * deleteByName:
     * 根据name删除user
     * @param name
     * @author lichunping    
     * @since 1.0.0
     */
    public void deleteByName(String name);
    
    
    /**
     * getUserList:
     * 根据sort获取user对象列表信息
     * @param sort
     * @return user列表
     * @author lichunping    
     * @since 1.0.0
     */
    public List<User> getUserList() throws Exception;
}
