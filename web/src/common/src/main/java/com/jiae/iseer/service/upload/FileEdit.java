package com.jiae.iseer.service.upload;
import java.io.*;

/**
 * FileEdit 对外的接口
 * @author lichunping 
 *            jarryli@gmail.com 2010-05-05
 * @sinace 1.0
 */
public interface FileEdit {

    /**
     * 设置目录的路径
     * @author lichunping
     * @param path      
     * @since 1.0
     */
    public void setPath(String path);
    
    //public String getPath();
    
    /**
     * 设置文件的路径
     * @author lichunping
     * @param path      
     * @since 1.0
     */
    public void setFile(String path);
    
    /**
     * 设置文件
     * @author lichunping
     * @param 文件对象      
     * @since 1.0
     */
    //public void setFile(File file);

    /**
     * remove file and folders
     * @author lichunping
     * @param file
     * @see delete(File);
     * @since 1.0
     * @return true || false
     */
    public boolean deleteFolder(File file);
    
    /**
     * 获得文件
     * @author lichunping
     * @since 1.0
     * @return 文件对象
     */
    public File getFile();
    
    /**
     * 删除定义的文件
     * @author lichunping
     * @since 1.0
     * @return true || false
     */
    public boolean delete();
    
    /**
     * 建立文件夹，路径在构造函数中指定
     * @author lichunping
     * @since 1.0
     * @return true || false
     */
    public boolean mkdir();
    
    /**
     * 是否为只读文件
     * @author lichunping
     * @param  文件对象
     * @since 1.0
     * @return true || false
     */
    public boolean canceReadOnly(File newName);
    
    /**
     * 设置为只读文件
     * @author lichunping
     * @param  文件对象 
     * @since 1.0
     * @return true || false
     */
    public boolean setReadOnly(File newName);
    
    /**
     * 移动文件
     * @author lichunping
     * @param  toPath 移动目的地 
     * @since 1.0
     * @return true || false
     */
    public boolean moveTo(String toPath);
    
    /**
     * 设置为只读文件
     * @author lichunping
     * @param  文件对象 
     * @since 1.0
     * @return true || false
     */
    public boolean createNewFile(File file);
    
    /**
     * 得到复制文件后的文件名
     * 复制一个文件都需要给该文件名增加 -复制 名字
     * 当再次复制文件时，已存在-复制名字了，就需要给文件增加-复制(i)名字了，i递增.
     * @author lichunping
     * @param copyNewPath 要复制的文件路径
     * @return 复制后的文件路径
     * @since 1.0
     */
    public String getCopyNewPath(String copyNewPath);    
    
    /**
     * 复制对象，含文件与目录
     * @author lichunping
     * @param  from 来源路径
     * @param  to 目的路径 
     * @since 1.0
     * @return true || false
     */
    public boolean copy(String from, String to);
    
    /**
     * 复制文件
     * @author lichunping
     * @param  from 来源路径
     * @param  to 目的路径 
     * @since 1.0
     * @return true || false
     */
    public boolean copyFile(String from, String to);
    
    /**
     * 递归复制所有文件夹
     * @author lichunping
     * @param  from 来源路径
     * @param  to 目的路径 
     * @since 1.0
     * @return true || false
     */
    public boolean copyFolderAll(String from, String to);
    
    /**
     * 复制文件夹
     * @author lichunping
     * @param  from 来源路径
     * @param  to 目的路径 
     * @since 1.0
     * @return true || false
     */
    public boolean copyFolder(String from, String to);
    
    /**
     * 重命名文件
     * @author lichunping
     * @param  name 新的文件路径与名称
     * @since 1.0
     * @return true || false
     */
    public boolean renameTo(String name);
    
    /**
     * 重命名文件
     * @author lichunping
     * @newFile  新的文件对象
     * @since 1.0
     * @return true || false
     */
    public boolean rename(File newFile);
    
    /**
     * according last dot get extension by file name
     * @param fileName
     * @return extension or null;
     */
    public String getExt(String fileName);
    
    /**
     * 创建一个对象，文件或者文件夹
     * @author lichunping
     * @newName  新的文件对象
     * @since 1.0
     * @return true || false
     */
    public boolean mkfile(File newName);
    
}
