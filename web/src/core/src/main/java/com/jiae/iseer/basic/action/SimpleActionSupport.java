/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.action;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jiae.iseer.basic.exception.BusiLogicException;
import com.jiae.iseer.basic.exception.BusiLogicException.Message;
import com.jiae.iseer.basic.utils.StringUtils;
import com.jiae.iseer.basic.utils.action.JSONObject;
import com.jiae.iseer.basic.utils.action.Visitor;
import com.jiae.iseer.cons.UserCons;
import com.jiae.iseer.entity.user.User;
import com.opensymphony.xwork2.ActionSupport;

/**
 * ClassName: SimpleActionSupport
 * Description:  Struts2 Action基类
 *
 * @author   jarryli@gmail.com
 * @version  
 * @since    1.0.0
 * @Date     2011-9-7 下午09:11:00
 *
 * @see      
 */
@SuppressWarnings({ "serial" })
public class SimpleActionSupport extends ActionSupport implements SessionAware {

        protected final Logger logger = LoggerFactory.getLogger(getClass());

        /**
         * fail result
         */
        public static final String FAIL = "fail";
        /**
         * success result
         */
        public static final String SUCCESS = "success";

        /**
         * JSON格式请求处理结果
         */
        protected JSONObject jsonObject = new JSONObject();
        
        @SuppressWarnings("unchecked")
        private Map session;
        @SuppressWarnings("unchecked")
        public void setSession(Map session) {
            this.session = session;

        }
        @SuppressWarnings("unchecked")
        public Map getSession() {
            return session;
        }

        /**
         * 返回session中保存的用户id
         * 
         * @author lichunping
         * @since 1.0.0 
         * @return
         */
        public Integer getUserId() {
            User user = this.getUser();
            if (null == user) {
                return null;
            }
            return user.getUserId();
        }

        /**
         * 返回session中保存的主账号对象，该数据在用户登录是放入session
         * 
         * @author lichunping
         * @since 1.0.0
         * @return
         */

        public User getUser() {
            Visitor visitor = this.getVisitor();
            if (null == visitor) {
                return null;
            }
            return visitor.getCurrentUser();
        }

        /**
         * 返回session中保存的visitor对象，该数据在用户登录是放入session
         * 
         * @author lichunping
         * @since 1.0.0
         * @return
         */

        public Visitor getVisitor() {
            return (Visitor) session.get(UserCons.VISITOR_OBJECT_SESSION);
        }
        /**
         * 设置session visitor
         * @param visitor 
         */
        @SuppressWarnings("unchecked")
        public void setVisitor(Visitor visitor) {
            session.put(UserCons.VISITOR_OBJECT_SESSION, visitor);
        }

        /**
         * 将BusiLogicException中的错误信息保存到struts的Errors中
         * 
         * @author lichunping
         * @since 1.0.0
         * @param ble 
         * @return
         */
        public void buildActionErrors(BusiLogicException ble) {
            if (null == ble) {
                return;
            }
            List<Message> globalMessage = ble.getGlobalMessage();
            for (Message message : globalMessage) {
                if (null != message) {
                    this.addActionError(getText(message.getMessage()));
                }
            }
            Map<String, Message> fieldMessage = ble.getFieldMessage();
            if (null != fieldMessage && fieldMessage.size() > 0) {
                for (Entry<String, Message> entry : fieldMessage.entrySet()) {
                    Message message = entry.getValue();
                    if (null != message) {
                        this.addFieldError(entry.getKey(), getText(message
                                .getMessage()));
                    }
                }
            }
        }
        /**
         * 根据以逗号分隔的字符串转化成LIST集合：
         * 
         * @author lichunping
         * @since 1.0.0
         * @param strs
         *            需要转化的字符串
         * @param field
         *            属性
         * @param errKey
         *            String 错误信息
         * @return 字符串转化后的List集合
         */
        public List<Integer> parseInteger(String strs, String field, String errKey) {
            List<Integer> strList = null;
            if (StringUtils.isNotBlank(strs)) {
                try {
                    strList = StringUtils.parseStringToIntegerList(strs, ",");
                } catch (NumberFormatException e) {
                    if (StringUtils.isNotEmpty(errKey)) {
                        if (StringUtils.isEmpty(field)) {
                            jsonObject.addGlobalMsg(this.getText(errKey));
                        } else {
                            jsonObject.addFieldMsg(strs, getText(errKey));
                        }
                    }
                }
            }
            return strList;
        }

        public JSONObject getJsonObject() {
            return jsonObject;
        }

        public void JSONObject(JSONObject jsonObject) {
            this.jsonObject = jsonObject;
        }

    }