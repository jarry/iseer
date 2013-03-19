/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.action.search;

/**
 * ClassName: PublicAction
 * Description: 公开服务的默认类
 * 
 * @author   <a href="mailto:lichunpin@baidu.com">LiChunping</a>
 * @version  
 * @since    1.0.0
 * @Date     2011-8-10 下午03:43:34
 *
 * @see      
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import com.jiae.iseer.basic.utils.StringUtils;

import com.jiae.iseer.basic.action.SimpleActionSupport;
import com.jiae.iseer.basic.utils.CharacterCodeUtils;
import com.jiae.iseer.basic.utils.ImageUtils;
import com.jiae.iseer.basic.utils.NetUtils;
import com.jiae.iseer.basic.utils.PropertiesUtils;
import com.jiae.iseer.cons.Variables;
import com.jiae.iseer.entity.image.Image;
import com.jiae.iseer.service.image.ImageService;
import com.jiae.iseer.service.search.SearchColorService;
import com.jiae.iseer.service.search.SearchSameService;
import com.jiae.iseer.service.search.SearchService;
import com.jiae.iseer.service.search.SearchSimilarService;
import com.jiae.iseer.service.upload.FileEdit;

public class SearchAction extends SimpleActionSupport {
    
    private static final long serialVersionUID = 1L;
    
    private String q;
    private String type;
    private String name;
    private String upload;
    private String url;
    private Integer id;
    private List<String[]> file;
    private List<Image> imageList;
    private String color;
    private String same;
    
    private Image image;
    private String imagesRealPath = Variables.IMAGES_REAL_PATH;
    private String tempRealPath = Variables.TEMP_REAL_PATH;
    private String indexRealPath = Variables.INDEX_REAL_PATH;

    
    @Resource
    private ImageService imageService;
    @Resource
    private SearchService searchService;
    @Resource
    private SearchSimilarService searchSimilarService;
    @Resource
    private SearchColorService searchColorService;
    @Resource
    private SearchSameService searchSameService;

//    @Resource
//    private SearchServiceSame searchSameService;
//    @Resource
//    private SearchServiceColor searchColorService;
//    @Resource
//    private SearchServiceCategory searchCategoryService;
    
    
    public SearchAction() {
        
    }
     
    public String execute() throws Exception {
        List<String> fileList = new ArrayList<String>();
        String filePath = null;
        BufferedImage img = null;
        
        // 全部相似
        if (id != null || name != null || url != null) {
            if (id != null) {
                // search images by id
                image = imageService.get(id);
                filePath = imagesRealPath + "/" + image.getName();
            } else if(name != null && !name.trim().equals("")) {
                // user upload to search
                if (upload != null) {
                    filePath = tempRealPath + "/" + name;
                } else {
                // search images by name
                    filePath = imagesRealPath + "/" + name;
                }

            } else if (url != null && !url.trim().equals("")) {
                upload = "uploadRemote";
                Map<String, String> imgInfo = NetUtils.getImageByUrl(url, tempRealPath);
                name = imgInfo.get("name");
                filePath = imgInfo.get("path") + "/" + name;
            }
            
            if (name != null) {
                image = new Image();
                image.setName(name);
                Dimension d = ImageUtils.getImageSize(filePath);
                image.setWidth(d.getSize().width);
                image.setHeight(d.getSize().height);
            }
            
            // for upload and get URL
            try {
                img = ImageIO.read( new File(filePath) );
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            // for find by ID
            if (img != null) {
                fileList = searchSimilarService.getSimilarList(img);
            }
        // 查询颜色
        } else if(color != null) {
            Color c = searchColorService.getColor(color);
            fileList = searchColorService.getSimilarColorList(c);
            
        // 查询相同
        } else if (same != null) {
            fileList = searchSameService.getSameList(null);
        }
        
        if (fileList.size() > 0) {
            imageList = searchService.getImageList(fileList);
        }
        return "success";
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String[]> getFile() {
        return file;
    }

    public void setFile(List<String[]> file) {
        this.file = file;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getSame() {
        return same;
    }

    public void setSame(String same) {
        this.same = same;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImagesRealPath() {
        return imagesRealPath;
    }

    
    public void setImagesRealPath(String imagesRealPath) {
        this.imagesRealPath = imagesRealPath;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
}
