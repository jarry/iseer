/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.baidu.webos.dao.impl.user;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

//import org.hibernate.Session;
import org.hibernate.Session;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baidu.webos.basic.test.SpringTransactionalTestCase;

import com.baidu.webos.dao.user.UserDao;
import com.baidu.webos.entity.user.User;
import com.baidu.webos.entity.system.System;
import com.baidu.webos.entity.user.UserInfo;


/**
 * ClassName: UserTest
 * Description: TODO Add function description
 *
 * @author   lichunping@baidu.com
 * @version  
 * @since    TODO
 * @Date     2011-11-8 下午05:39:37
 *
 * @see      
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserDaoImplTest  extends SpringTransactionalTestCase {
    
    @Resource
    private UserDao userDao;
    
//    @Resource
//    private SystemDao systemDao;
    
    User user;
    UserInfo userInfo;
    System system;
    Session session;
    
    @Resource
    HibernateTemplate hibernateTemplate;
    
    public UserDaoImplTest() {

    }

    public void setSession(Session session) {
        this.session = session;
    }
    
    public Session getSession() {
        return userDao.getSession();
    }
    
    public void flush() {
        getSession().flush();
    }
    
    

  @Test 
  public void testCreateSystem() {
      java.lang.System.out.print("\r\n为用户创建system测试:\r\n");
      user = userDao.get(6);
      system = new System();
      system.setThemeId(2);
      system.setCreateTime(new Date());
      system.setModifyTime(new Date());
      
      system.setUser(user);
//      systemDao.saveOrUpdate(system);
      
      getSession().saveOrUpdate(system);      
      
      java.lang.System.out.print("\r\n为用户创建system测试结束:\r\n");
  } 
    
  @Test
  public void testSaveUser() {
      java.lang.System.out.print("\r\n插入user测试:\r\n");
      user = new User();
//      user.setUserId(null);
      user.setName("jarry" + new Date());
      user.setActive(1);
      user.setNickname("李春平");
      user.setPassword("001001");
      user.setCreateTime(new Date());
      user.setModifyTime(new Date());
      
      userDao.saveOrUpdate(user); 
      
      userInfo = new UserInfo();
      userInfo.setUserId(user.getUserId());
      userInfo.setGender(1);
      userInfo.setBirthday(new Date());
      userInfo.setPhone("123");
      userInfo.setMail("lichunping@baidu.com" + new Date().toString());      
      userInfo.setComeFrom("from");
      userInfo.setType(1);
      userInfo.setLevel(1);
      userInfo.setTitle("233");
      userInfo.setDescription("321");
      userInfo.setCreateTime(new Date());
      userInfo.setModifyTime(new Date());
      
      user.setUserInfo(userInfo);
      userDao.saveOrUpdate(user);
      
      java.lang.System.out.print("\r\n插入user测试结束:\r\n");
  }
    
    @Test
    public void testDeleteUser() {
        java.lang.System.out.print("\r\n删除user测试:\r\n");
//        user = userDao.get(2);        
        if (user != null) {
//            userDao.delete(user);  
//            hibernateTemplate.setFlushMode(HibernateTemplate.FLUSH_EAGER);
//            hibernateTemplate.delete(user);
        }
        
//        删除userId为2的user对象
        userDao.delete(40);
        
        java.lang.System.out.print("\r\n删除user测试结束:\r\n");
    }
    
    @Test
    public void testGetUser() {
        java.lang.System.out.print("\r\n获取user测试:\r\n");
//        user = userDao.getByUserId(1);
        user = userDao.get(1);
        
        java.lang.System.out.print(user);
        if (user != null) {
            java.lang.System.out.print(user.getUserId() + " | " + user.getName());
        }
        java.lang.System.out.print("\r\n获取user测试结束:\r\n");
        
        User user =  userDao.getByName("lichuning");  
        if (user != null) {
            try {
                printObject(user);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        
    }
    
    @Test
    public void testGetUserList() {
        java.lang.System.out.print("\r\n获取全部user测试:\r\n");
        List<User> userList = userDao.getUserList();
        for (Iterator<User> iter = userList.iterator(); iter.hasNext();) {
            user = (User) iter.next();
            try {
                printObject(user);
                java.lang.System.out.println("\r\n");
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            
        } 
        java.lang.System.out.print("\r\n获取全部user测试结束:\r\n");
    }
    
    /**
     * 根据方法打印所有get方法
     * printObject:
     *
     * @param model
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException      
     * @since
     */
    public void printObject(Object model) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Method[] m = model.getClass().getMethods();        
        for (int i = 0; i < m.length; i++) {
            if (m[i].getName().indexOf("get") == 0) {
                java.lang.System.out.println(m[i].getName() + " | " + m[i].invoke(model));
            }
        }
    }
    
}
