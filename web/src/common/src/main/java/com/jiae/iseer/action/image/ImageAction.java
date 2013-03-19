/*
 * Copyright (c) 2011, All Rights Reserved.
 */

/**
 * ClassName: ImageAction
 * Function: Image的action
 *
 * @author   <a href="mailto:jarryli@gmail.com">lichunping</a>
 * @version  
 * @since    image暂时用不到action，通过service层供外部调用即可。模板以及result配置等待日后处理
 * @Date     2011-9-27 下午07:42:45
 *
 * @see      
 */

package com.jiae.iseer.action.image;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;

import com.jiae.iseer.basic.action.CRUDActionSupport;
import com.jiae.iseer.basic.dao.impl.Page;
import com.jiae.iseer.basic.utils.LoggerUtils;
import com.jiae.iseer.cons.Constants;
import com.jiae.iseer.entity.image.Image;
import com.jiae.iseer.service.image.ImageService;

@Controller
//@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ImageAction extends CRUDActionSupport<Image> {
    
    private static final long serialVersionUID = 1L;
    
    @Resource
    private ImageService imageService;
    
    private Image image;
    
    protected static  Logger logger = LoggerFactory.getLogger(ImageAction.class);

    private Integer imageId;
    private String name;
    private String password;
    // 每页15项，自动查询列表总行数.
    private Page<Image> page = new Page<Image>(Constants.PAGE_SIZE, true);
    private List<Image> imageList;

    public ImageAction() {

    }

    @Override
    public String execute() {
        try {

            // 根据id查询的是单独image
            if (imageId != null) {
                image = imageService.get(imageId);
                return SUCCESS;
            }
            // 根据name查询的是单独image
            if (!StringUtils.isBlank(name)) {
                name = name.trim();
                image = imageService.getImageByName(name);
                return "success";
            }
           
        } catch (Exception e) {
            LoggerUtils.info(logger, "get image: " + imageId + " failed", e);
        }

        return SUCCESS;
    }
    
    ///
    
    /**
     * 模板列表页分页查询
     * @throws Exception 
     */
    @Override
    public String list() throws Exception {
        page = imageService.listWithPage(page, null);
        return "list";
    }
    
    /**
     * 新建模板
     * @throws Exception 
     */
    @Override
    public String save() throws Exception {
        return "saveOrUpdate";
    }    
    
    /**
     * 修改模板
     * @throws Exception 
     */
    @Override
    public String modify() throws Exception {
        return "saveOrUpdate";
    }
    
    /**
     * 准备实体数据
     * @throws Exception 
     */
    @Override
    protected void prepareModel() throws Exception {
        this.image = new Image();
    }
    
    /**
     * 返回实体
     * @see com.opensymphony.xwork2.ModelDriven#getModel()
     */
    @Override
    public Image getModel() {
        return image;
    }
    
   ///

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * TODO // 如果没有传入用户id的话暂时默认为第一个用户
     *         等二期再引入session管理用户
     */
    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public Page<Image> getPage() {
        return page;
    }


}