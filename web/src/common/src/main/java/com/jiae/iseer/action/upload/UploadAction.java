package com.jiae.iseer.action.upload;

import java.awt.Dimension;
import java.io.*;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.util.ServletContextAware;
import org.springframework.stereotype.Controller;

import com.jiae.iseer.basic.action.SimpleActionSupport;
import com.jiae.iseer.basic.utils.ImageUtils;
import com.jiae.iseer.cons.Variables;
import com.jiae.iseer.service.image.ImageService;
import com.jiae.iseer.service.index.IndexService;
import com.jiae.iseer.service.upload.FileUpload;

/**
 * 文件上传Action
 * @author lichunping 
 *            jarryli@gmail.com 2010-05-05  
 * @sinace 1.0
 */
@Controller
//@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UploadAction extends SimpleActionSupport implements ServletContextAware {
    
    private static final long serialVersionUID = 1L;
    
    @Resource
    private FileUpload fileUpload;
    @Resource
    private ImageService imageService;
    @Resource
    private IndexService indexService;
    private ServletContext context;
    
    // upload multi-files
    private ArrayList<File> uploads;
    private ArrayList<Map<String, String>> uploadsList;
    private ArrayList<String> uploadsFileName;
    private ArrayList<String> uploadsContentType;
    private final String[] PROPERTIE_NAMES = {"name", "contentType", "size"};
    
    // upload single file
    private File uploadFile;
    private String uploadFileFileName;
    private String uploadFileContentType;
    
    private String imagesRealPath = Variables.IMAGES_REAL_PATH;
    private String tempRealPath = Variables.TEMP_REAL_PATH;
    private String indexRealPath = Variables.INDEX_REAL_PATH;

    private String path;
    private Map<String, String> MESSAGE = new HashMap<String, String>();
    private Map<String, Object> imageInfo = new HashMap<String, Object>();
    private final long MAX_SIZE = 300 * 1024 * 1024;  
    
    public String execute() {
        setPath(tempRealPath);
        try {
            if (uploads == null && uploadFile == null) {
                setMessage("INFO", " not select file.");
                return "error";
            }
            
            if (uploads != null) { 
                setUploadsList();
                saveFiles();
                return "success";
            }
            if (uploadFile != null) {
                saveFile();
                return "success";
            }
            return "error";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }    
    
    public void setUploadsList() {     
        try {
            for (int i = 0; i < uploadsFileName.size(); i++) {
                fileUpload.addProperty(PROPERTIE_NAMES[0], uploadsFileName.get(i));
                fileUpload.addProperty(PROPERTIE_NAMES[1], uploadsContentType.get(i));    
                fileUpload.addProperty(PROPERTIE_NAMES[2], String.valueOf(uploads.get(i).length() / 1024));
                fileUpload.addUploadsListRow(null);
                fileUpload.resetProperty();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        uploadsList = fileUpload.getUploadsList();
    }
    
    public void saveFile() {
        // for save the  upload single file
        if (path != null && uploadFile != null) {
//            String fileType = ImageUtils.getImageType(path + "/" + uploadFileFileName);
            
            if (!ImageUtils.isValidType(uploadFileContentType)) {
                setMessage("UPLOAD_RESULT", "failed, is not valid image type.");
                return;
            }
            String newName = fileUpload.getMD5FileName(uploadFileFileName);
            if (fileUpload.moveFileReplace(uploadFile, path + "/" 
                    + newName)) {
                Dimension d = ImageUtils.getImageSize(path + "/" + newName);
                // set map
                imageInfo.put("name", newName);
                imageInfo.put("type", uploadFileContentType);
                imageInfo.put("width", d.getSize().width);
                imageInfo.put("height", d.getSize().height);
                imageInfo.put("size", uploadFile.length() / 1024);
                imageInfo.put("path", path + "/" + newName);
                
                setMessage("UPLOAD_RESULT", "success");
                setMessage("INFO", " upload file successfully.");
            } else {
                setMessage("UPLOAD_RESULT", "failed");
                setMessage("INFO", " can not upload file.");
            }
        }     
    }
    
    public void saveFiles() {
        // for save the  upload multi-files
        if (path != null && uploads != null) {
            if (fileUpload.moveFiles(uploads, path)) {
                setMessage("UPLOAD_RESULT", "success");
                setMessage("INFO", " upload files successfully.");
            } else {
                setMessage("UPLOAD_RESULT", "failed");
                setMessage("INFO", " can not upload files.");
            }
        }    
    }
    
    public void setUploads(ArrayList<File> uploads) {
        this.uploads = uploads;
    }
    
    public ArrayList<File> getUploads() {
        return uploads;
    }
    
    public void setUploadsFileName(ArrayList<String> filesName) {
        uploadsFileName = filesName;
    }
    
    public ArrayList<String> getUploadsFileName() {
        return uploadsFileName;
    }
    
    public void setUploadsContentType(ArrayList<String> contentType) {
        uploadsContentType = contentType;
    }
    
    public ArrayList<String> getUploadsContentType() {
        return uploadsContentType;
    }
    
    public String getUploadFileFileName() {
        return uploadFileFileName;
    }
    public void setUploadFileFileName(String uploadFileFileName) {
        this.uploadFileFileName = uploadFileFileName;
    }
    
    public String getUploadFileContentType() {
        return uploadFileContentType;
    }
    public void setUploadFileContentType(String uploadFileContentType) {
        this.uploadFileContentType = uploadFileContentType;
    }

    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
    }
    
    public File getUploadFile() {
        return uploadFile;
    }
    
    public void setMessage(String key, String value) {
        MESSAGE.put(key, value);
    }
    
    public Map<String, String> getMessage() {
        return MESSAGE;
    }

    public FileUpload getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(FileUpload fileUpload) {
        this.fileUpload = fileUpload;
    }

    public ArrayList<Map<String, String>> getUploadsList() {
        return uploadsList;
    }

    public void setUploadsList(ArrayList<Map<String, String>> uploadsList) {
        this.uploadsList = uploadsList;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public Map<String, Object> getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(Map<String, Object> imageInfo) {
        this.imageInfo = imageInfo;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.struts2.util.ServletContextAware#setServletContext(javax.servlet.ServletContext)
     */
    @Override
    public void setServletContext(ServletContext context) {
        this.context = context;
    }
    
}
