/*
 * Copyright (c) 2011, All Rights Reserved.
 */

/**
 * ClassName: AppAction
 * Function: App的action
 *
 * @author   <a href="mailto:jarryli@gmail.com">lichunping</a>
 * @version  
 * @since    TODO
 * @Date     2011-9-27 下午07:42:45
 *
 * @see      
 */

package com.jiae.iseer.action.index;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
//import javax.imageio.ImageIO;

import net.semanticmetadata.lire.DocumentBuilder;
import net.semanticmetadata.lire.DocumentBuilderFactory;
import net.semanticmetadata.lire.utils.FileUtils;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.jiae.iseer.entity.image.Image;
import com.jiae.iseer.module.index.MetadataBuilder;
import com.jiae.iseer.module.index.ParallelIndexer;
import com.jiae.iseer.service.image.ImageService;
import com.jiae.iseer.service.index.IndexService;
import com.jiae.iseer.service.upload.FileUpload;
//import com.jiae.iseer.service.user.UserService;
import com.jiae.iseer.basic.action.SimpleActionSupport;
import com.jiae.iseer.basic.utils.ImageUtils;
import com.jiae.iseer.basic.utils.StringUtils;
import com.jiae.iseer.cons.Variables;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

@Controller
//@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class IndexAction extends SimpleActionSupport {
    
    private static final long serialVersionUID = 1L;
    @Resource
    private IndexService indexService;
    @Resource
    private ImageService imageService;

    private String imagesRealPath = Variables.IMAGES_REAL_PATH;
    private String tempRealPath = Variables.TEMP_REAL_PATH;
    private String indexRealPath = Variables.INDEX_REAL_PATH;
    private String action;
    private String name;
    private String type;
    private String width;
    private String height;
    private Map<String, Object> imageInfo = new HashMap<String, Object>();
    
    protected static  Logger logger = LoggerFactory.getLogger(IndexAction.class);
    
    public String execute() throws Exception {
        if (!StringUtils.isBlank(action)) {
            // all images indexing
            if (action.equals("all")) {
                indexService.createIndexDir(imagesRealPath);
            }
            // single image indexing
            if (action.equals("single") && !StringUtils.isBlank(name)) {
                imageInfo.put("name", name);
                imageInfo.put("type", type);
                imageInfo.put("width", width);
                imageInfo.put("height", height);
                // move to images path
                imageService.moveUpload(name);
                // save upload image
                imageService.saveImage(imageInfo);
                // index upload image
                String filePath = imagesRealPath + "/" + name;
                indexService.createIndexFile(filePath);

            }
        }
        return SUCCESS;
    }
    
    public String getIndexRealPath() {
        return indexRealPath;
    }

    public void setIndexRealPath(String indexRealPath) {
        this.indexRealPath = indexRealPath;
    }

    public String getImagesRealPath() {
        return imagesRealPath;
    }

    public void setImagesRealPath(String imagesRealPath) {
        this.imagesRealPath = imagesRealPath;
    }

    public String getTempRealPath() {
        return tempRealPath;
    }

    public void setTempRealPath(String tempRealPath) {
        this.tempRealPath = tempRealPath;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Map<String, Object> getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(Map<String, Object> imageInfo) {
        this.imageInfo = imageInfo;
    }


}