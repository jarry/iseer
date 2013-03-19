package com.jiae.iseer.service.impl.upload;
import java.io.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiae.iseer.service.upload.FileEdit;


/**
 * 文件操作类
 * @author lichunping 
 *            jarryli@gmail.com 2010-05-05
 * @sinace 1.0
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class FileEditImpl implements FileEdit {
    private File file;
    private String path;
    private final String COPY_TEXT_NAME = "-\u590d\u5236"; // 复制
    private int copyIndex = 0;
    
    public FileEditImpl() {
        
    }
    public FileEditImpl(String path) {
        if (path == null) return;
        setPath(path);
        setFile(path); 
    }
    
    public void setPath(String path) {
        if (path != null)this.path = path;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setFile(String path) {
        if (path == null) return;
        this.file = new File(path);
    }
    
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * remove file and folders
     * @param file
     * @see delete(File);
     * @return
     */
    public boolean deleteFolder(File file) {
        boolean delSuccess = true; 
        try {
            if (file.exists()) {
                 if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolder(files[i]);
                    }
                }             
                if (!file.delete()) {
                    delSuccess = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         return delSuccess;
    }
    
    public File getFile() {
        return file;
    }
    
    public boolean delete() {
        if (file == null || !file.exists()) return false;
        if (file.isFile()) {
            return file.delete();
        } else {
            return deleteFolder(file);
        }
    }
    
    public boolean mkdir() {
        return file.mkdir();
    }
    
    public boolean canceReadOnly(File newName) {
        if (file.exists()) {
            file.setWritable(true);
            return file.setReadable(true);
        }
        return false;
    }
    
    public boolean setReadOnly(File newName) {
        if (file.exists()) {
            return file.setReadOnly();
        }
        return false;
    }

    /**
     * 移动文件到指定路径
     * @param toPath 目标路径
     * @return 是否移动成功
     */
    public boolean moveTo(String toPath) {
        try {
            if (toPath != null && file.exists()) {
                int toPathLen = toPath.length();
                String last = toPath.substring(toPathLen - 1, toPathLen);
                if (!last.equals("/")) toPath += "/";
                File newFile = new File(toPath + file.getName());
                
                boolean moveSuccess = file.renameTo(newFile);        
//                `renameTo` sometimes failed, add `copyFile`
                if (!moveSuccess) {
                    moveSuccess = copyFile(file.toString(), toPath);
                    file.deleteOnExit();
                }
                
                return moveSuccess;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean createNewFile(File file) {
        if (file != null && !file.exists()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    /**
     * according last dot get extension by file name
     * @param fileName
     * @return extension or null;
     */
    public String getExt(String fileName) {
        try {
            if (fileName != null && fileName.length() > 0) {
                int lastDotAt = fileName.lastIndexOf(".");
                if (lastDotAt != -1) {
                    fileName = fileName.substring(lastDotAt + 1, fileName.length());
                } else {
                    fileName = "";
                }
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return fileName;
    }
    
    /**
     * 得到复制文件后的文件名
     * 复制一个文件都需要给该文件名增加 -复制 名字
     * 当再次复制文件时，已存在-复制名字了，就需要给文件增加-复制(i)名字了，i递增.
     * @param copyNewPath
     * @return
     */
    public String getCopyNewPath(String copyNewPath) {
        String parentPath = "", newPath = "", name = "", ext = "";
        try {
        parentPath = file.getParent();
        parentPath = parentPath.replaceAll("\\\\", "/");
        name = file.getName();
        ext = getExt(name);
        ext = ext.length() > 0 ? ("." + ext) : ext;
        if (copyNewPath == null || copyNewPath.length() <= 0) {
            // 判断是否存在 -复制 这样的名字
            newPath = parentPath + "/" + name + COPY_TEXT_NAME + ext;
        } else {
            newPath = copyNewPath;
        }
        
        File newFile = new File(newPath);
        if (newFile.exists()) {
            // 如果存在 -复制 这样的名字，即增加-复制(i)名字
            copyIndex++;
            copyNewPath = parentPath + "/" + name + COPY_TEXT_NAME + "(" + String.valueOf(copyIndex) + ")" + ext;
            newPath = getCopyNewPath(copyNewPath);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newPath;
    }
    
    public boolean copy(String from, String to) {
        if (to != null) {
            File fromFile = new File(from);
            if (!fromFile.exists()) return false;
            
            if (fromFile.isFile()) {
                return copyFile(from, to);
            } else {
                return copyFolder(from, to);
            }                 
        }
        return false;
    }
    
    public boolean copyFile(String from, String to) {
        try {  
            FileInputStream in = new FileInputStream(from);  
            FileOutputStream out = new FileOutputStream(to);  
            byte[] bt = new byte[16 * 1024];  
            int count;  
            while ((count = in.read(bt)) > 0) {  
                out.write(bt, 0, count);  
            }
            in.close();  
            out.close();  
            return true;  
        } catch (IOException ex) {  
            return false;  
        }  
    }
    /**
     * 递归复制所有文件夹
     * @param from
     * @param to
     * @return
     */
    public boolean copyFolderAll(String from, String to) {
        boolean copySuccess = true; 
        try {
            String fromPath, toPath;
            File fromFolder = new File(from);
            if (!fromFolder.exists()) return false; 
            
            if (fromFolder.isDirectory()) {
                File toFolder = new File(to);
                toFolder.mkdir();
                
                File files[] = fromFolder.listFiles();
                for (int i = 0; i < files.length; i++) {
                    fromPath = from + "/"+ files[i].getName();
                    toPath   = to   + "/"+ files[i].getName();
                    if (files[i].isFile()) {
                        // 如果是文件直接复制
                        copyFile(fromPath, toPath);
                    } else {
                        // 如果是文件夹则递归执行
                        copyFolderAll(fromPath, toPath);
                    }
                }
            }             
        } catch (Exception e) {
            e.printStackTrace();
        }
         return copySuccess;
    } 
    
    public boolean copyFolder(String from, String to) {
        try {
            File toFolder = new File(to);
            if (!toFolder.exists()) {
                File files[] = file.listFiles();
                if (files.length <= 0) {
                    return toFolder.mkdir();
                } else {
                    return copyFolderAll(from, to);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean renameTo(String name) {
        try {
            if (name != null && file.exists()) {
                String path = file.getParent();
                path = path.replaceAll("\\\\", "/");
                path += "/" + name;
                // added file extension
                //path += "." + FilePath.getExt(file.getName());
                File newFile = new File(path);
                if (newFile.exists()) return false;
                return file.renameTo(newFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean rename(File newFile) {
        if (file.exists()) {
            return file.renameTo(newFile);
        }
        return false;
    }
    
    public boolean mkfile(File newName) {
        if (file.exists() && file.isDirectory()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
}
