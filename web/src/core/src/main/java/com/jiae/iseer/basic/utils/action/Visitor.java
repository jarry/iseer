/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.utils.action;

import java.io.Serializable;
import java.util.List;
import com.jiae.iseer.cons.Constants;
import com.jiae.iseer.entity.user.User;

/**
 * ClassName: Visitor
 * Description: TODO Add function description
 *
 * @author   jarryli@gmail.com
 * @version  
 * @since    TODO
 * @Date     2011-11-7 下午09:46:38
 *
 * @see      
 */

public class Visitor implements Serializable {

    /**  */
    
    private static final long serialVersionUID = 1L;
    private Integer visitorUserId;
    private String visitorUserName;
    private String visitorMail = "";
    private String ip;
    private List<User> userList;
    private User currentUser;

    private Integer pageSize = Constants.PAGE_SIZE;

    public Integer getVisitorUserId() {
        return visitorUserId;
    }

    public void setVisitorUserId(Integer visitorUserId) {
        this.visitorUserId = visitorUserId;
    }

    public String getVisitorUserName() {
        return visitorUserName;
    }

    public void setVisitorUserName(String visitorUserName) {
        this.visitorUserName = visitorUserName;
    }

    public String getVisitorMail() {
        return visitorMail;
    }

    public void setVisitorMail(String visitorMail) {
        this.visitorMail = visitorMail;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<User> getUserList() {
        return userList;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
   
}
