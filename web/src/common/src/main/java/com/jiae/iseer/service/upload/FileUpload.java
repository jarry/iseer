package com.jiae.iseer.service.upload;

import java.io.*;
import java.util.*;

/**
 * 文件上传接口类
 * @author lichunping 
 *        jarryli@gmail.com 2010-05-05
 * save upload files name and type
 * @see FileUploadAction.java
 */

public interface FileUpload {

    /**
     * 获得上传文件列表
     * @author lichunping
     * @return 文件列表    
     * @since 1.0
     */
    public ArrayList<Map<String, String>> getUploadsList();
    
    /**
     * 设置上传文件列表
     * @author lichunping
     * @param uploadsList 文件列表    
     * @since 1.0
     */
    public void setUploadsList(ArrayList<Map<String, String>> uploadsList);
    
    /**
     * 添加到属性map中
     * @author lichunping
     * @param key 
     * @param value
     * @since 1.0
     */
    public void addProperty(String key, String value);
    
    /**
     * 添加上传信息到属性map中
     * @author lichunping
     * @param properties  
     * @since 1.0
     */
    public void addUploadsListRow(Map<String, String> properties);
    
    /**
     * 重置属性列表
     * @author lichunping
     * @since 1.0
     */
    public void resetProperty();
    
    /**
     * move upload file to the path;
     * @author lichunping
     * @param from 来源路径
     * @param to   目的路径
     * @return true || false
     * @since 1.0
     */
    public boolean move(String from, String to);
    
    /**
     * move upload file to the path;
     * @author lichunping 
     * @param fromFile 来源文件
     * @param to       目的路径
     * @return true || false
     * @since 1.0
     */
    public boolean move(File fromFile, String to);
    
    /**
     * moveFileReplace:
     * 移动文件，若已存在移动到的同名文件，则提前删除
     * @author lichunping
     * @param fromFile 来源文件
     * @param to       目的路径
     * @return true || false
     * @since 1.0
     */
    public boolean moveFileReplace(File fromFile, String to);
    
    /**
     * moveFiles:
     * 批量移动文件
     * @author lichunping
     * @param from 来源文件集合
     * @param toPath    目的路径
     * @return true || false
     * @since 1.0
     */
    public boolean moveFiles(ArrayList<File> from, String toPath);

    /**
     * 得到属性
     * @author lichunping
     * @return 属性Map
     * @since 1.0
     */
    public Map<String, String> getProperties();

    /**
     * 得到MD5加密后的文件名
     * @author lichunping
     * @param name
     * @return name
     * @since 1.0
     */
    public String getMD5FileName (String name);
    
    /**
     * 设置属性列表
     * @author lichunping
     * @param properties 属性Map
     * @since 1.0
     */
    public void setProperties(Map<String, String> properties);
    
}
