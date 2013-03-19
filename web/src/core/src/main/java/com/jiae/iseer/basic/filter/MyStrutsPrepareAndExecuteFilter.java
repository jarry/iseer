/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.filter;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.jiae.iseer.basic.utils.StringUtils;
import com.jiae.iseer.cons.Constants;

/**
 * ClassName: MyStrutsPrepareAndExecuteFilter
 * Description: 自定义struts filter dispather，用于实现tunnel，合并action打包
 *
 * @author   jarryli@gmail.com
 * @version  
 * @since    1.0.0
 * @Date     2011-11-1 下午07:24:15
 *
 * @see      
 */

public class MyStrutsPrepareAndExecuteFilter extends StrutsPrepareAndExecuteFilter {
    
//    private static final long serialVersionUID = 1L;
    /**
     * 父类 dofilter
     * @param req 
     * @param res 
     * @param chain 
     * @throws IOException 
     * @throws ServletException 
     */
    public void doSuperFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        super.doFilter(req, res, chain);
    }
    
    /**
     * tunnel 的转发实现
     * @param req 
     * @param res 
     * @param chain 
     * @throws IOException 
     * @throws ServletException 
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (request.getRequestURI().equals(Constants.TUNNEL_ACTION_URL)) {
            String url = request.getParameter("url");
            String param = request.getParameter("param");
            if (url == null || param == null) {
                response.sendError(HttpStatus.SC_BAD_REQUEST, "tunnel action should have url and param parameter!");
                return;
            }
            String[] urls = url.split("\\|");
            String[] params = StringUtils.isEmpty(param) ? new String[] { "" } : StringUtils.splitPreserveAllTokens(
                    param, "\\|");
            List<String> results = new ArrayList<String>();
            if (urls.length != params.length) {
                response.sendError(HttpStatus.SC_BAD_REQUEST,
                        "The length of url and the length of param should be match");
                return;
            }

            int singleStatusCode = 0;
            for (int i = 0; i < urls.length; i++) {
                MockHttpServletRequest mockRequest = getMockRequest(request, URLDecoder.decode(urls[i], "UTF-8"),
                        URLDecoder.decode(params[i], "UTF-8"));
                MockHttpServletResponse mockResponse = new MockHttpServletResponse();
                super.doFilter(mockRequest, mockResponse, chain);
                singleStatusCode = mergeMockResponse(mockResponse, response, results, singleStatusCode);
            }

            if (results.size() > 0) {
                response.setContentType("text/javascript;charset=UTF-8");
                response.getWriter().write("[" + StringUtils.join(results.toArray(), ",") + "]");
            }
        } else {
            super.doFilter(request, response, chain);
        }
    }

    /**
     * 为“包”里的每个action模拟request请求
     * 
     * @author lichunping
     * @param request
     * @param requestURI
     * @param paramString
     * @return
     */
    private MockHttpServletRequest getMockRequest(HttpServletRequest request, String requestURI, String paramString) {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest(request.getMethod(), requestURI);
        int questionMark = requestURI.indexOf("?");
        mockRequest.setServletPath(questionMark >= 0 ? requestURI.substring(0, questionMark) : requestURI);
        mockRequest.setQueryString(request.getQueryString());
        Enumeration<String> enumeration = request.getHeaderNames();
        String name;
        while (enumeration.hasMoreElements()) {
            name = (String) enumeration.nextElement();
            mockRequest.addHeader(name, request.getHeader(name));
        }
        enumeration = request.getAttributeNames();
        while (enumeration.hasMoreElements()) {
            name = (String) enumeration.nextElement();
            mockRequest.setAttribute(name, request.getAttribute(name));
        }
        if (!StringUtils.isEmpty(paramString)) {
            String[] params = paramString.split("&");
            String key;
            String value;
            int index;
            for (String param : params) {
                index = param.indexOf("=");
                if (index < 0) {
                    continue;
                }
                key = param.substring(0, index);
                value = param.substring(index + 1);
                mockRequest.addParameter(key, value);
            }
        }
        mockRequest.setCookies(request.getCookies());
        mockRequest.setSession(request.getSession());
        return mockRequest;
    }

    /**
     * 将各个action的结果合并。其中合并statusCode的规则是设置最大的一个。
     * 
     * @author lichunping
     * @param mockResponse
     * @param response
     * @param results
     * @throws IOException
     * @return 待合并response和合并response中statuscode最大的一个。
     */
    private int mergeMockResponse(MockHttpServletResponse mockResponse, HttpServletResponse response,
            List<String> results, int finalStatusCode) throws IOException {
        int statusCode = mockResponse.getStatus();
        if (statusCode > finalStatusCode) {
            response.setStatus(statusCode);
        } else {
            statusCode = finalStatusCode;
        }
        Iterator<String> iterator = (Iterator<String>) mockResponse.getHeaderNames().iterator();
        while (iterator.hasNext()) {
            String headerName = iterator.next();
            response.setHeader(headerName, mockResponse.getHeader(headerName).toString());
        }
        Cookie[] cookies = mockResponse.getCookies();
        for (Cookie cookie : cookies) {
            response.addCookie(cookie);
        }

        if (!StringUtils.isEmpty(mockResponse.getRedirectedUrl())) {
            response.sendRedirect(mockResponse.getRedirectedUrl());
        } else {
            results.add(mockResponse.getContentAsString());
        }
        return statusCode;
    }
    
}
