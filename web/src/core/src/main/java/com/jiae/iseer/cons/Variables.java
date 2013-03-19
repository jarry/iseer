package com.jiae.iseer.cons;

import com.jiae.iseer.basic.utils.PropertiesUtils;

/**
 * 定义整个工程用的变量,通过variables.properties文件读入
 * 
 * @author Administrator
 * 
 */
public class Variables {

    /**
     * Image的文件的真实路径
     * 实际线上环境指向BeansDB
     * 参照classes/variables.properties
     */
    public static String IMAGES_REAL_PATH = PropertiesUtils.getValue("images.realPath");
    public static String TEMP_REAL_PATH = PropertiesUtils.getValue("temp.realPath");
    public static String INDEX_REAL_PATH = PropertiesUtils.getValue("index.realPath");
    
}
