package com.jiae.iseer.action.download;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.apache.struts2.util.ServletContextAware;
import org.springframework.stereotype.Controller;

import com.jiae.iseer.basic.action.SimpleActionSupport;
import com.jiae.iseer.basic.utils.ImageUtils;
import com.jiae.iseer.basic.utils.NetUtils;
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
public class DownloadAction extends SimpleActionSupport implements ServletContextAware {
    
    private static final long serialVersionUID = 1L;
    
    private ServletContext context;

    private final String[] PROPERTIE_NAMES = {"name", "contentType", "size"};
    
    private String imagesRealPath = Variables.IMAGES_REAL_PATH;
    private String tempRealPath = Variables.TEMP_REAL_PATH;
    private String indexRealPath = Variables.INDEX_REAL_PATH;
    private String url;
    private String path;
    private Map<String, String> MESSAGE = new HashMap<String, String>();
    private Map<String, Object> imageInfo = new HashMap<String, Object>();
    
    public String execute() {
        String filePath = null;
        BufferedImage img = null;
        setPath(tempRealPath);
        
        try {
            if (url != null) {
                Map<String, String> imgInfo = NetUtils.getImageByUrl(url, tempRealPath);
                String name = imgInfo.get("name");
                filePath = imgInfo.get("path") + "/" + name;
                String type = ImageUtils.getImageType(filePath);
                
                if (!ImageUtils.isValidType(type)) {
                    setMessage("UPLOAD_RESULT", "failed, is not valid image type.");
                    return "error";
                }
                Dimension d = ImageUtils.getImageSize(filePath);
//                img = ImageIO.read( new File(filePath) );
                
                imageInfo.put("name", name);
                imageInfo.put("type", type);
                imageInfo.put("width", d.getSize().width);
                imageInfo.put("height", d.getSize().height);
                imageInfo.put("path", path + "/" + name);

                return "success";
            }
            return "error";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }    

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setMessage(String key, String value) {
        MESSAGE.put(key, value);
    }
    
    public Map<String, String> getMessage() {
        return MESSAGE;
    }

    public Map<String, Object> getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(Map<String, Object> imageInfo) {
        this.imageInfo = imageInfo;
    }

    public String[] getPROPERTIE_NAMES() {
        return PROPERTIE_NAMES;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
