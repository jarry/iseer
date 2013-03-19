/*
 * Copyright (c) 2011, All Rights Reserved.
 */

/**
 * ClassName: PropertiesUtils
 * Function: PropertiesUtils的工具类
 *
 * @author   <a href="mailto:jarryli@gmail.com">lichunping</a>
 * @version  1.0.0
 * @since    1.0.0
 * @Date     2011-8-27 07:11:45
 *
 * @see      
 */

package com.jiae.iseer.basic.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jiae.iseer.cons.Constants;

public final class PropertiesUtils {
    
    protected static Logger   log        = LoggerFactory.getLogger(PropertiesUtils.class);
    private static Properties properties = null;
    private static String filePath = null;
    
    static {
        String fileName = Constants.PROPERTIES_FILE_NAME;
        try {
//          初始化根据文件路径读取classes路径下文件的数据流
            properties = new Properties();
            InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
            properties.load(in);
            in.close();
        } catch (Exception e) {
            LoggerUtils.error(log, "read file " + fileName + " error", e);
        }
    }
    
    
    public PropertiesUtils() {

    }
    
    /**
     * 载入一个新的的properties资源，供本Class使用
     * @param filePath 文件路径
     */
    public static void loadProperties(String filePath) {
        if (!StringUtils.isBlank(filePath)) {
            try {
                setFilePath(filePath);
                properties = new Properties();
//            指定文件的路径    
              InputStream in = new BufferedInputStream (new FileInputStream(filePath));
              properties.load(in);
              in.close();                 

            } catch (Exception e) {
                LoggerUtils.error(log, "read file " + filePath + " error", e);
            }
        }
    }
    
    public static void loadProperties() {
        loadProperties(filePath);
    }

    /**
     * 改变或添加properties对象的一个key
     * 当key存在于properties文件中时该key的值被value所代替
     * 当key不存在时，新增加key
     * 
     * @param key
     *            要存入的键
     * @param value
     *            要存入的值
     */
    public static void setValue(String key, String value) {
        if (properties != null && !StringUtils.isBlank(key) && !StringUtils.isBlank(value)) {
            properties.setProperty(key, value);
        }
    }
    
    /**
     * 读取properties对象，得到key的值
     * 
     * @param key 取得其值的键
     * @return key的值
     */
    public static String getValue(String key) {
        if (properties == null) {
            return null;
        }
        if (properties.containsKey(key)) {
            String value = properties.getProperty(key);
            return value;
        } else {
            return null;
        }
    }

    /**
     * 通过载入一个新properties文件，得到key的值
     * 该properties文件不载入本Class
     * 
     * @param filePath
     *            properties文件的路径+文件名
     * @param key
     *            取得其值的键
     * @return key的值
     */
    public static String getValue(String filePath, String key) {      
        try {
            String value = null;
            if (!StringUtils.isBlank(filePath)) {
                Properties properties = new Properties();
                FileInputStream inputFile = new FileInputStream(filePath);
                properties.load(inputFile);
                inputFile.close();
                if (properties.containsKey(key)) {
                    value = properties.getProperty(key);
                }
            }
            return value;
            
        } catch (FileNotFoundException e) {
            LoggerUtils.error(log, "read file: " + filePath + " and get: "
                    + key + " error", e);
            return null;
        } catch (IOException e) {
            LoggerUtils.error(log, "read file: " + filePath + " and get: "
                    + key + " error", e);
            return null;
        } catch (Exception e) {
            LoggerUtils.error(log, "read file: " + filePath + " and get: "
                    + key + " error", e);
            return null;
        }
    }
    
    /**
     * 删除properties对象中的一个键
     * 
     * @param key 取得其值的键
     */
    public static void remove(String key) {
        if (properties != null) {
            if (properties.containsKey(key)) {
                properties.remove(key);
            }
        }
    }
    
    /**
     * 删除properties文件中的中的一个key
     * @param filePath 
     *          properties文件的路径+文件名
     * @param key 
     *          取得其值的键
     */
    public static void remove(String filePath, String key) {
        try {
            if (!StringUtils.isBlank(filePath) && !StringUtils.isBlank(key)) {
                Properties properties = new Properties();
                FileInputStream inputFile = new FileInputStream(filePath);
                properties.load(inputFile);
                properties.remove(key);
                inputFile.close();
                saveFile(filePath, properties, null, null);
            }
        } catch (Exception e) {
            LoggerUtils.error(log, "remove: " + filePath + " : "
                    + key + " error", e);
        }
    }

    /**
     * 新增或修改properties文件中的一个键值，对文件生效
     * 
     * @param key
     *            要存入的键
     * @param value
     *            要存入的值
     */
    public static void setValue(String filePath, String key, String value) {
        try {
            if (!StringUtils.isBlank(filePath)
                    && !StringUtils.isBlank(key) 
                    && !StringUtils.isBlank(value)) {
                Properties properties = new Properties();
                FileInputStream inputFile = new FileInputStream(filePath);
                properties.load(inputFile);
                properties.setProperty(key, value);
                inputFile.close();
                saveFile(filePath, properties, key, value);
            }
        } catch (Exception e) {
            LoggerUtils.error(log, "read file: " + filePath + " addValue: "
                    + key + " = " + value + " error", e);
        }
    }
    
    public static void saveProperties() {
        if (filePath != null && properties != null) {
            saveFile(filePath, properties, null, null);
        }
    }


    /**
     * 将更改后的文件数据存入指定的文件中，该文件可以事先不存在。
     * 
     * @param filePath
     *            文件路径+文件名称
     * @param properties 对象
     * @param key 键
     * @param value 值
     * 
     */
    public static void saveFile(String filePath, Properties properties, String key, String value) {
        if (!StringUtils.isBlank(filePath) && properties != null && filePath != null) {
            
            if (!StringUtils.isBlank(key) && !StringUtils.isBlank(value)) {
                properties.setProperty(key, value);
            }
            
            String comment = "saveOrUpdate: " + key + " = " + value;
            try {
                FileOutputStream outputFile = new FileOutputStream(filePath);                
                properties.store(outputFile, comment);
                outputFile.close();
            } catch (FileNotFoundException e) {
                LoggerUtils.error(log, "saveFile: " + filePath + " "
                        + comment + " error", e);
            } catch (IOException ioe) {
                LoggerUtils.error(log, "saveFile: " + filePath + " "
                        + comment + " error", ioe);
            }
        }
    }
    
    public static int getLength() {
        return properties.entrySet().size();
    }
    
    public static HashMap<Object, Object> getMapList() {
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        Iterator<Entry<Object, Object>> iter = PropertiesUtils.getProperties().entrySet().iterator();  
        while (iter.hasNext()) {
            Entry<Object, Object> element = (Entry<Object, Object>) iter.next();
            hashMap.put(element.getKey(), element.getValue());
        }
        return hashMap;
        
    }
    
    public static List<Object[]> getList() {
        List<Object[]> list = new ArrayList<Object[]>();
        Iterator<Entry<Object, Object>> iter = properties.entrySet().iterator();   
        while (iter.hasNext()) {
            Entry<Object, Object> element = iter.next();
            Object[] object = {element.getKey(), element.getValue()};
            list.add(object);         
        }        
        return list;
    }
    
    /**
     * 清除properties文件中所有的key和其值
     * 
     */
    public static void clear() {
        if (properties != null) {
            properties.clear();
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties properties) {
        PropertiesUtils.properties = properties;
    }

    public static String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String filePath) {
        PropertiesUtils.filePath = filePath;
    }

}
