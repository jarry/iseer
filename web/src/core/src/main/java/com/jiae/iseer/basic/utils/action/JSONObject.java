/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.utils.action;

import java.io.Serializable;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.jiae.iseer.cons.Constants;

/**
 * ClassName: JSONObject
 * Description: TODO 通用JSON处理类
 *
 * @author   jarryli@gmail.com
 * @version  
 * @since    TODO
 * @Date     2011-10-8 上午11:09:00
 *
 * @see      
 */

public class JSONObject implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String SUCCESS_OK = "true";
    public static final String SUCCESS_FAIL = "false";

    // 执行成功与否：true/false
    String success;
    // 返回消息
    Map<String, Object> message = new HashMap<String, Object>();

    // 字段错误信息
    Map<String, String> fieldMessages = new HashMap<String, String>();
    // 全局错误信息
    String globalErrorStr = null;
    // 上传错误信息
    StringBuilder uploadErrorStr = new StringBuilder();

    public JSONObject() {
        success = JSONObject.SUCCESS_FAIL;
    }

    /**
     * 添加域错误信息
     * 
     * @author lichunping
     * @since 1.0.0
     * @param key 字段
     * @param value 出错信息
     */
    public void addFieldMsg(String key, String value) {
        if (key == null) {
            return;
        }
        this.fieldMessages.put(key, value);
    }

    /**
     * 添加全局错误信息
     * 
     * @author lichunping
     * @since 1.0.0
     * @param value
     *            错误信息
     */
    public void addGlobalMsg(String value) {
        this.globalErrorStr = value;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    
    /**
     * 返回错误信息
     * 若存在全局错误信息，添加全局错误信息后直接返回
     * 
     * @author lichunping
     * @since 1.0.0
     * @return message
     */
    public Map<String, Object> getMessage() {
        if (globalErrorStr != null) {
            message.put(Constants.GLOBAL_ERROR, globalErrorStr);
        } else if (fieldMessages.size() > 0) {
            message.put(Constants.FIELD_ERROR, fieldMessages);
        }
        return message;
    }

    public void setMessage(Map<String, Object> message) {
        this.message = message;
    }

    /**
     * 判断是否存在错误
     * 
     * @author lichunping
     * @since 1.0.0
     * @return
     */
    public boolean hasErrors() {
        return (StringUtils.isNotEmpty(globalErrorStr) || MapUtils.isNotEmpty(fieldMessages));
    }
}
