/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.velocity;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.struts2.dispatcher.StrutsRequestWrapper;
//import org.apache.struts2.views.velocity.VelocityManager;
//import org.apache.velocity.context.Context;
//import com.opensymphony.xwork2.util.ValueStack;

import org.apache.struts2.dispatcher.VelocityResult;

/**
 * ClassName: FlexibleVelocityResult
 * Description: struts2默认的velocity dispatcher不支持修改contentType，该方法支持自定义contentType
 * 
 * org.apache.struts2.dispatcher
 * VelocityResult.java 源码：
 *   protected String getContentType(String templateLocation) {
 *       return "text/html";
 *  }
 * 
 * @author   <a href="mailto:lichunpin@baidu.com">LiChunping</a>
 * @version  1.0.0
 * @since    TODO
 * @Date     2011-9-27 下午06:53:03
 *
 * @see      
 */

public class FlexibleVelocityResult extends VelocityResult {
    
    private static final long serialVersionUID = 1L;
//        private String contentType = "text/html;charset=UTF-8";  
        private String contentType = "text/html";
    
        public void setContentType(String contentType) {
          this.contentType = contentType;  
        }  
        
        @Override
        protected String getContentType(String templateLocation) {
            return contentType;
        }
        
        /**
         * 当前velocity中不会更新session，所以为了降低不必要的Memcached请求， 使用HttpServletRequest构建velocity上下文，而不用RequestWrapper
         * 
         * @see org.apache.struts2.dispatcher.VelocityResult#createContext(org.apache
         *      .struts2.views.velocity.VelocityManager, com.opensymphony.xwork2.util.ValueStack,
         *      javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)
         */
        // TODO 待处理memcached
//        @Override
//        protected Context createContext(VelocityManager velocityManager, ValueStack stack, HttpServletRequest request,
//                HttpServletResponse response, String location) {
//            StrutsRequestWrapper wrapper = (StrutsRequestWrapper) request;
//            HttpServletRequest req = (HttpServletRequest) wrapper.getRequest();
//            if (req instanceof RequestWrapper) {
//                req = (HttpServletRequest) ((RequestWrapper) req).getRequest();
//                wrapper = new StrutsRequestWrapper(req);
//            }
//            return velocityManager.createContext(stack, wrapper, response);
//        }
        
   }  