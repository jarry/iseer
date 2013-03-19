package com.jiae.iseer.service.impl.upload;

import java.io.*;
import java.util.*;

import javax.annotation.Resource;

import net.semanticmetadata.lire.utils.ImageUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiae.iseer.basic.utils.StringUtils;
import com.jiae.iseer.service.upload.FileUpload;
import com.jiae.iseer.service.upload.FileEdit;

/**
 * 文件上传后对文件移动增删操作
 * @author lichunping 
 *        jarryli@gmail.com 2010-5  
 *         
 * @sinace 1.0.1
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class FileUploadImpl implements FileUpload {
    /**
     * save upload files name and type
     * @see FileUploadAction.java
     */
    private ArrayList<Map<String, String>> uploadsList = new ArrayList<Map<String, String>>();
    private Map<String, String> properties = new HashMap<String, String>();

    @Resource
    private FileEdit fileEdit;
    
    public FileUploadImpl() {

    }
     
    public ArrayList<Map<String, String>> getUploadsList() {
        return uploadsList;
    }
    
    public void setUploadsList(ArrayList<Map<String, String>> uploadsList) {
        this.uploadsList = uploadsList;
    }
    
    public void addProperty(String key, String value) {
        properties.put(key, value);
    }
    
    public void addUploadsListRow(Map<String, String> properties) {
        if (properties != null) {
            this.properties = properties;
        }
        uploadsList.add(this.properties);
    }
    
    public void resetProperty() {
        properties = new HashMap<String, String>();
    }
    
    /**
     * 移动文件，如存在同名文件则不替换
     * @param from 要移动的文件路径
     * @param to 文件目标的路径
     * @return 是否成功移动文件
     */
    public boolean move(String from, String to) {
        try {
            File fromFile = new File(from);
            File toFile   = new File(to);
            
            if (!fromFile.exists()) {
            return false;
            }
        
            boolean moveSuccess = fromFile.renameTo(toFile);    
    //        `renameTo` sometimes failed, add `copyFile`
            if (!moveSuccess) {
                moveSuccess = fileEdit.copyFile(from, to);
                fromFile.deleteOnExit();
            }
            return moveSuccess;
    
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 移动文件，如存在同名文件则不替换
     * @param fromFile 要移动的文件
     * @param to 文件目标的路径
     * @return 是否成功移动文件
     */
    public boolean move(File fromFile, String to) {
        try {
            String toPath = to;
            File toFile   = new File(toPath);
            if (!fromFile.exists() ) {
                return false;
            }
            
            boolean moveSuccess = fromFile.renameTo(toFile);    
    //        `renameTo` sometimes failed, add `copyFile`
//            if (!moveSuccess) {
//                moveSuccess = fileEdit.copyFile(fromFile.toString(), toPath);
//                fromFile.deleteOnExit();
//            }
            return moveSuccess;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 移动文件并替换同名文件
     * @param fromFile 要移动的文件
     * @param to 文件目标的路径
     * @return 是否成功移动文件
     */
    public boolean moveFileReplace(File fromFile, String to) {
    try {
        String toPath = to;
        File toFile   = new File(toPath);
        if (!fromFile.exists()) {
            return false;
        }
        if (toFile.exists()) {
            toFile.deleteOnExit();
        }    
        
        boolean moveSuccess = fromFile.renameTo(toFile);    
//        `renameTo` sometimes failed, add `copyFile`
        if (!moveSuccess) {
            moveSuccess = fileEdit.copyFile(fromFile.toString(), toPath);
            fromFile.deleteOnExit();
        }
        return moveSuccess;        
        
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
    }
    
    /**
     * 批量移动文件
     * @param from 要移动的文件集合
     * @param toPath 文件目标的路径
     * @return 是否成功移动文件
     */
    public boolean moveFiles(ArrayList<File> from, String toPath) {
        boolean moveSuccess = true;
        File file;
        String newName = "";
        try {
            for (int i = 0; i < from.size(); i++) {
                file = from.get(i);
                Map<String, String> uploadFile = uploadsList.get(i);
                newName = getMD5FileName(uploadFile.get("name"));
                if (!moveFileReplace(file, toPath + "/" + newName)) {
                    moveSuccess = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return moveSuccess;
    }

    public Map<String, String> getProperties() {
        return properties;
    }
    
    /**
     * 得到MD5加密后的文件名
     * @author lichunping
     * @param name
     * @return name
     * @since 1.0
     */
    public String getMD5FileName (String name) {
        return ImageUtils.getMD5FileName(name);
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
    
}
