/*
 * Copyright (c) 2011, All Rights Reserved.
 */

/**
 * ClassName: VelocityUtils
 * Function: Velocity工具类，通过在toolbox.xml文件中配置可以在vm模板文件中调用该工具类的方法
 *
 * @author   <a href="mailto:jarryli@gmail.com">lichunping</a>
 * @version  
 * @since    1.1.0
 * @Date     2011-8-27 07:11:45
 *
 * @see      
 */

package com.jiae.iseer.basic.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VelocityUtils {
    
    protected static Logger log = LoggerFactory.getLogger(VelocityUtils.class);

    /**
     * 将普通字符串进行实体字符和json特殊子符转码字符串
     * 
     * @param source
     *            source 待转换的源字符串
     * @return String 转码后的字符串
     * 
     * @author jarryli@gmail.com
     * @version 1.0.0
     */
    public String escape4HtmlAndJson(Object source) {
        if (source == null) {
            return "";
        }
        return StringUtils.escape4Json(StringUtils.escape4Html(source.toString()));
    }
    
    /**
     * 仅进行json转义
     * 
     * @author jarryli@gmail.com 
     * @since 1.2.15
     * @param source 
     * @return
     */
    public String escape4Json(Object source) {
        if (source == null) {
            return "";
        }
        return StringUtils.escape4Json(source.toString());
    }
    
    
    /**
     * 仅进行json转义
     * 
     * @author jarryli@gmail.com 
     * @since 1.2.15
     * @param source 
     * @return
     */
    public String escape4String(Object source) {
        if (source == null) {
            return "";
        }
        return source.toString().trim();
    }
    
    /**
     * 针对null进行特殊处理，如果是字符串则返回空字符串，否则返回字符串
     * 
     * @author jarryli@gmail.com
     * @since 1.0.0
     * @param source 
     * @return
     */
    public String escapeNull(Object source) {
        if (null == source) {
//            return "0";
            return "null";
        } else {
            return source.toString();
        }
    }
   
    
    /**
     * 对url进行编码
     * @param paramObject 
     * @return 
     */
    public String encodeUrl(Object paramObject) {
        if ((paramObject == null) || ("null".equals(paramObject.toString()))) {
            return "";
        }
        try {
            return URLEncoder.encode(paramObject.toString(), "utf-8");
        } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
            localUnsupportedEncodingException.printStackTrace();
        }
        return "";
    }

    /**
     * 用于传递给前端的报表的比率数据，去除后面的百分号
     * 
     * @author jarryli@gmail.com
     * @since 1.0.0
     * @param paramObject 
     * @return
     */
    public String parseRate(String paramObject) {
        if (paramObject == null || paramObject.equals("")) {
            paramObject = "-";
        } else if (paramObject.endsWith("%")) {
            paramObject = paramObject.substring(0, paramObject.length() - 1);
        }

        return StringUtils.escape4Json(StringUtils.escape4Html(paramObject));
    }
    
    /**
     * 获得标准格式化日期
     * @param date 日期参数
     * @return
     */
    public static String formatNormalDate(Date date) {
//        return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
        return DateFormatUtils.format(date, "yyyy-MM-dd");
    }
    
    /**
     * 重设图像尺寸最小200x200
     * @param width
     * @param height
     * @return
     */
    public static Object resizeImageSize(Integer width, Integer height) {
        int flag = 200;
        return resizeImageSize(width, height, flag);
    }

    /**
     * 重设图像尺寸最小200x200
     * @param width
     * @param height
     * @return
     */
    public static Object resizeImageSize(Integer width, Integer height, Integer flag) {
        if (width == null || height == null || flag == null) {
            return null;
        }
        HashMap<String, Integer> size = new HashMap<String, Integer>();
        float proportion = 0;
        if (width < height) {
            if (width > flag) {
                size.put("width", flag);
                proportion = (width / flag);
                height = (int) (height > flag ? (height / proportion) : height);
            } else {
                size.put("width", width);
            }
            size.put("height", height);
 
        } else {
            if (height > flag) {
                size.put("height", flag);
                proportion = (height / flag);
                width = (int) (width > flag ? (width / proportion) : width);
            } else {
                size.put("height", height);
            }
            size.put("width", width);
        }
        return size;
    }
}

