/*
 * Copyright (c) 2011, All Rights Reserved.
 */

/**
 * ClassName: UserAction
 * Function: User的action
 *
 * @author   <a href="mailto:jarryli@gmail.com">lichunping</a>
 * @version  
 * @since    user暂时用不到action，通过service层供外部调用即可。模板以及result配置等待日后处理
 * @Date     2011-9-27 下午07:42:45
 *
 * @see      
 */

package com.jiae.iseer.action.user;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;

import com.jiae.iseer.basic.action.CRUDActionSupport;
import com.jiae.iseer.basic.utils.LoggerUtils;
import com.jiae.iseer.entity.user.User;
import com.jiae.iseer.service.user.UserService;

@Controller
//@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserAction extends CRUDActionSupport<User> {
    
    private static final long serialVersionUID = 1L;
    
    @Resource
    private UserService userService;
    
    private User user;
    
    protected static  Logger logger = LoggerFactory.getLogger(UserAction.class);

    private Integer userId;
    private String name;
    private String password;
    private List<User> userList;

    public UserAction() {

        if (userId != null) {
            setUserId(userId);
        } else {
            // TODO 测试时默认为1， 后续通过登录获得并存入session中
            setUserId(1);
        }
    }

    @Override
    public String execute() {
        try {

            userList = userService.getUserList();
            // 根据id查询的是单独user
            if (userId != null) {
                user = userService.get(userId);
                return SUCCESS;
            }
            // 根据name查询的是单独user
            if (!StringUtils.isBlank(name)) {
                name = name.trim();
                user = userService.getUserByName(name);
                return "success";
            }
           
        } catch (Exception e) {
            LoggerUtils.info(logger, "get user: " + userId + " failed", e);
        }

        return SUCCESS;
    }
    
    ///
    
    /**
     * 模板列表页分页查询
     * @throws Exception 
     */
    @Override
    public String list() throws Exception {
        userList = userService.getUserList();
        return "list";
    }
    
    /**
     * 新建模板
     * @throws Exception 
     */
    @Override
    public String save() throws Exception {
        return "saveOrUpdate";
    }    
    
    /**
     * 修改模板
     * @throws Exception 
     */
    @Override
    public String modify() throws Exception {
        return "saveOrUpdate";
    }
    
    /**
     * 准备实体数据
     * @throws Exception 
     */
    @Override
    protected void prepareModel() throws Exception {
        this.user = new User();
    }
    
    /**
     * 返回实体
     * @see com.opensymphony.xwork2.ModelDriven#getModel()
     */
    @Override
    public User getModel() {
        return user;
    }
    
   ///

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * TODO // 如果没有传入用户id的话暂时默认为第一个用户
     *         等二期再引入session管理用户
     */
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<User> getUserList() {
        return userList;
    }

}