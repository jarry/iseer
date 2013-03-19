/*
 * Copyright (c) 2011, All Rights Reserved.
 */

/**
 * ClassName: CharacterCodeUtils
 * Function: 字符编码处理类
 *
 * @author   <a href="mailto:jarryli@gmail.com">lichunping</a>
 * @version  
 * @since    TODO
 * @Date     2011-8-27 07:11:45
 *
 * @see      
 */

package com.jiae.iseer.basic.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CharacterCodeUtils {
    
    protected static Logger log = LoggerFactory.getLogger(CharacterCodeUtils.class);
    
    /**
     * encodeCovert: 给字符转码
     * 
     * @author lichunping
     * @param str
     *            要编码的字符
     * @param from
     *            原来的编码。如: iso-8859-1
     * @param to
     *            转码后的编码。如:utf-8
     * @return 转码后的字符串
     * @since 1.0
     */
    public static String encodeCovert(String str, String from, String to) {
        try {
            String formStr = str;
            byte[] toStr = formStr.getBytes(from);
            return new String(toStr, to);
        } catch (Exception e) {
            LoggerUtils.error(log, "encodeCovert " + str + " error", e);
        }
        return "null";
    }

    public static String iso2utf8(String str) {
        return encodeCovert(str, "iso-8859-1", "utf-8");
    }

    public static String iso2gbk(String str) {
        return encodeCovert(str, "iso-8859-1", "gbk");
    }

    public static String encode(String str) {
        try {
            str = URLEncoder.encode(str, "utf-8");
            // str = str.replaceAll("%2F", "/");
        } catch (UnsupportedEncodingException e) {
            LoggerUtils.error(log, "encode " + str + " error", e);
        }
        return str;
    }

    public static String decode(String str) {
        try {
            str = URLDecoder.decode(str, "utf-8");
            // str = str.replaceAll("%2F", "/");
        } catch (UnsupportedEncodingException e) {
            LoggerUtils.error(log, "decode " + str + " error", e);
        }
        return str;
    }

    /**
     * html转义字符 encodeHTML:
     * 
     * @param html
     * @return
     * @since
     */
    public static String encodeHTML(String html) {
        html = html.replaceAll("&", "&amp;");
        html = html.replaceAll("'", "&#039;");
        // ' = &rsquo; = &#039;
        html = html.replaceAll("\"", "&quot;");
        html = html.replaceAll("<", "&lt;");
        html = html.replaceAll(">", "&gt;");
        return html;
    }
}
