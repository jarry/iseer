/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.service.impl.user;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiae.iseer.service.user.UserService;
import com.jiae.iseer.dao.user.UserDao;
import com.jiae.iseer.entity.user.User;
//import com.jiae.iseer.entity.system.System;

/**
 * ClassName: UserServiceImpl
 * Description: User service层实现
 * 
 * @author   <a href="mailto:lichunpin@baidu.com">LiChunping</a>
 * @version  
 * @since    TODO
 * @Date     2011-9-27 下午02:02:17
 *
 * @see      
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {
    
    protected static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Resource
    private UserDao userDao;
    
    public UserServiceImpl() {
    }
    
    /**
     * get:
     * 根据id记录来获取user对象
     * @param id
     * @return user对象
     * @author lichunping    
     * @since 1.0.0
     */
    public User get(Integer id) throws Exception {
        return userDao.get(id);
    }
    
    /**
     * getById:
     * 根据userId获取user信息
     * @param userId
     * @return user对象
     * @author lichunping    
     * @since 1.0.0
     */
    public User getByUserId(Integer userId) throws Exception {
        return userDao.getByUserId(userId);
    }
    
    
//    /**
//     * getSystemId:
//     * 根据userId获取system的id
//     * @param userId
//     * @return systemId
//     * @author lichunping    
//     * @since 1.0.0
//     */
//    public Integer getSystemId(Integer userId) throws Exception {
//        return getSystem(userId).getSystemId();
//    }
    
    
//    /**
//     * getSystem:
//     * 根据userId记录来获取system对象
//     * @param userId
//     * @return system对象
//     * @author lichunping    
//     * @since 1.0.0
//     */
//    public System getSystem(Integer userId) throws Exception {
//        User user = userDao.get(userId);
//        if (null != user) {
//            return user.getSystem();
//        }
//        return null;
//    }
    
    
    /**
     * getUserByName:
     * 根据name获取user信息
     * @param name
     * @return user对象
     * @author lichunping    
     * @since 1.0.0
     */
    public User getUserByName(String name) {
        return userDao.getByName(name);
    }
    
    /**
     * delete:
     * 根据userId删除user
     * @param id
     * @author lichunping    
     * @since 1.0.0
     */
    public void delete(Integer userId) {
        userDao.delete(userId);
    }
    
    /**
     * deleteByName:
     * 根据name删除user
     * @param name
     * @author lichunping    
     * @since 1.0.0
     */
    public void deleteByName(String name) {
        userDao.deleteByName(name);
    }
    
    /**
     * getUserList:
     * 根据sort获取user对象列表信息
     * @param sort
     * @return user列表
     * @author lichunping    
     * @since 1.0.0
     */
    public List<User> getUserList() throws Exception {
        return userDao.getUserList();
    }

    
}
