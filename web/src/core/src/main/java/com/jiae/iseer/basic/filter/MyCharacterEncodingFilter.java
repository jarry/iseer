/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: MyCharacterEncodingFilter
 * Description: CharacterEncodingFilter自定义过滤器
 *
 * @author   jarryli@gmail.com
 * @version  
 * @since    1.0.0
 * @Date     2011-11-1 下午07:24:15
 *
 * @see      
 */

public class MyCharacterEncodingFilter extends HttpServlet implements Filter {
    
    private static final long serialVersionUID = 5265981768694455791L;

    private FilterConfig      filterConfig;
    private String            encoding;
    private boolean           forceEncoding    = false;

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setForceEncoding(boolean forceEncoding) {
        this.forceEncoding = forceEncoding;
    }

    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (this.encoding != null
                && (this.forceEncoding || request.getCharacterEncoding() == null)) {
            request.setCharacterEncoding(this.encoding);
            if (this.forceEncoding) {
                response.setCharacterEncoding(this.encoding);
            }
        }
        filterChain.doFilter(request, response);
    }
    
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain filterChain) {
        try {
            // get value form web.xml
            String encoding = filterConfig.getInitParameter("encoding");
            if (encoding != null) {
                request.setCharacterEncoding(encoding);
                response.setCharacterEncoding(encoding);
            }
            filterChain.doFilter(request, response);
        } catch (ServletException sx) {
            filterConfig.getServletContext().log(sx.getMessage());
        } catch (IOException iox) {
            filterConfig.getServletContext().log(iox.getMessage());
        }
    }
    
    

    public void destroy() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.doGet(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.doPost(request, response);
    }  
}
