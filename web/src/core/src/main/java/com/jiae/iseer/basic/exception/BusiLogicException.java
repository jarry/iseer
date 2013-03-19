/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.exception;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
/**
 * ClassName: BusiLogicException
 * Description: 业务逻辑异常类
 *
 * @author   jarryli@gmail.com
 * @version  
 * @since    TODO
 * @Date     2011-10-7 下午10:00:32
 *
 * @see      
 */

public class BusiLogicException extends Exception {

    /**  */
    
    private static final long serialVersionUID = 1L;
    /**
     * 全局性异常
     */
    private List<Message> globalMessage = new ArrayList<Message>();
    /**
     * 属性异常
     */
    private Map<String, Message> fieldMessage = new TreeMap<String, Message>();

    /**
     * 
     */
    public BusiLogicException() {
    }

    public BusiLogicException(String message) {
        this.addGlobalMessage(message);
    }

    public List<Message> getGlobalMessage() {
        return globalMessage;
    }

    public void setGlobalMessage(List<Message> globalMessage) {
        this.globalMessage = globalMessage;
    }

    public Map<String, Message> getFieldMessage() {
        return fieldMessage;
    }

    public void setFieldMessage(Map<String, Message> fieldMessage) {
        this.fieldMessage = fieldMessage;
    }

    /**
     * 默认为STRUTS信息类型
     * 
     * @author lichunping
     * @since 1.0.0
     * @param message 
     */
    public void addGlobalMessage(String message) {
        if (message == null) {
            throw new IllegalArgumentException("the argument message can't be null");
        }
        Message msg = new Message();
        msg.setType(Message.TYPE_STRUS);
        msg.setMessage(message);
        this.globalMessage.add(msg);
    }
    /**
     * 添加全局信息
     * 
     * @author lichunping
     * @since 1.0.0
     * @param message 
     */
    public void addGlobalMessage(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("the argument message can't be null");
        }
        this.globalMessage.add(message);
    }

    /**
     * 新增属性提示信息，默认为STRUTS信息类型
     * 
     * @author lichunping
     * @since 1.0.0
     * @param field 
     * @param message 
     */
    public void addFieldMessage(String field, String message) {
        if (field == null || message == null) {
            throw new IllegalArgumentException("the argument field/message can't be null");
        }
        Message msg = new Message();
        msg.setType(Message.TYPE_STRUS);
        msg.setMessage(message);
        this.fieldMessage.put(field, msg);
    }
    
    /**
     * 添加域相关信息
     * 
     * @author lichunping
     * @since 1.0.0
     * @param field 
     * @param message 
     */
    public void addFieldMessage(String field, Message message) {
        if (field == null || message == null) {
            throw new IllegalArgumentException("the argument field/message can't be null");
        }
        this.fieldMessage.put(field, message);
    }
    
    /**
     * 错误相关消息
     * @author lichunping
     * @since 1.0.0
     *
     */
    public static class Message {
        /**
         * 消息类型-简单文本
         */
        public static final short TYPE_TEXT = 1;
        /**
         * 消息类型-STRUTS信息
         */
        public static final short TYPE_STRUS = 2;
        private String message;
        private List<Message> subMessages = new ArrayList<Message>();
        private Short type;

        private String realMessage;

        public Message() {
            super();
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<Message> getSubMessages() {
            return subMessages;
        }

        public void setSubMessages(List<Message> subMessages) {
            this.subMessages = subMessages;
        }

        public Short getType() {
            return type;
        }

        public void setType(Short type) {
            this.type = type;
        }

        public String getRealMessage() {
            return realMessage;
        }

        public void setRealMessage(String realMessage) {
            this.realMessage = realMessage;
        }

    }
}
