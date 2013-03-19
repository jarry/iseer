/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.dao.impl.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiae.iseer.basic.dao.impl.GenericDaoImpl;
import com.jiae.iseer.dao.user.UserDao;
import com.jiae.iseer.entity.user.User;

/**
 * ClassName: UserDaoImpl
 * Description: user dao层
 * 
 * @author   <a href="mailto:lichunpin@baidu.com">LiChunping</a>
 * @version  
 * @since    1.0.0
 * @Date     2011-9-27 下午02:02:17
 *
 * @see      
 */
@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserDaoImpl extends GenericDaoImpl<User, Integer> implements UserDao {
    
    /**
     * 构造方法，执行通用Dao
     * 并且给实体class赋值
     */
    public UserDaoImpl() {
        super();
        this.entityClass = User.class;
    }
    
    /**
     * getUserById:
     * 根据userId获取user对象
     * @param userId
     * @return user对象
     * @author lichunping    
     * @since 1.0.0
     */
    public User getByUserId(Integer userId) {
        if (userId == null) {
            return null;
        }
        
        String hql = "FROM User a WHERE a.userId=? ";
        return (User) super.findUnique(hql, userId);
        
//        Query query = super.createQuery(hql);   
//        query.setParameter(0, userId);
//        return (User) query.uniqueResult();

    }
    
    /**
     * deleteByName:
     * 根据name删除该user
     * @author lichunping    
     * @since 1.0.0
     */
    public int deleteByName(String name) {
        if (name == null) {
            return 0;
        }

        String hql = "DELETE FROM User a WHERE a.name = :name ";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", name);
        
        return super.batchUpdateOrDelete(hql, paramMap);
    }
    
    /**
     * getByName:
     * 根据名称获取user信息
     * @param name
     * @return user列表
     * @author lichunping    
     * @since 1.0.0
     */
    public User getByName(String name) {
        if (name == null) {
            return null;
        }
        String hql = "FROM User a WHERE a.name=? ";
        return (User) super.findUnique(hql, name);

    }
    
    /**
     * getUserList:
     * 查询全部user列表
     * @return user列表
     * @author lichunping    
     * @since 1.0.0
     */
    public List<User> getUserList() {
        return super.findAll();
    } 
}
