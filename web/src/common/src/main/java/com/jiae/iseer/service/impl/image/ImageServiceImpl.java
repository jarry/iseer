/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.service.impl.image;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiae.iseer.basic.dao.impl.Page;
import com.jiae.iseer.cons.Variables;
import com.jiae.iseer.service.image.ImageService;
import com.jiae.iseer.service.upload.FileUpload;
import com.jiae.iseer.dao.image.ImageDao;
import com.jiae.iseer.entity.image.Image;

/**
 * ClassName: ImageServiceImpl
 * Description: Image service层实现
 * 
 * @author   <a href="mailto:lichunpin@baidu.com">LiChunping</a>
 * @version  
 * @since    TODO
 * @Date     2011-9-27 下午02:02:17
 *
 * @see      
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ImageServiceImpl implements ImageService {
    
    protected static Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
    
    @Resource
    private ImageDao imageDao;
    @Resource
    private FileUpload fileUpload;
    
    private String imagesRealPath = Variables.IMAGES_REAL_PATH;
    private String tempRealPath = Variables.TEMP_REAL_PATH;
    private String indexRealPath = Variables.INDEX_REAL_PATH;
    
    public ImageServiceImpl() {
    }
    
    /**
     * get:
     * 根据id记录来获取Image对象
     * @param id
     * @return Image对象
     * @author lichunping    
     * @since 1.0.0
     */
    public Image get(Integer id) throws Exception {
        return imageDao.get(id);
    }
    
    /**
     * getById:
     * 根据ImageId获取Image信息
     * @param ImageId
     * @return Image对象
     * @author lichunping    
     * @since 1.0.0
     */
    public Image getByImageId(Integer ImageId) throws Exception {
        return imageDao.getByImageId(ImageId);
    }
    
    
    /**
     * getImageByName:
     * 根据name获取Image信息
     * @param name
     * @return Image对象
     * @author lichunping    
     * @since 1.0.0
     */
    public Image getImageByName(String name) {
        return imageDao.getByName(name);
    }
    
    /**
     * delete:
     * 根据ImageId删除Image
     * @param id
     * @author lichunping    
     * @since 1.0.0
     */
    public void delete(Integer ImageId) {
        imageDao.delete(ImageId);
    }
    
    /**
     * deleteByName:
     * 根据name删除Image
     * @param name
     * @author lichunping    
     * @since 1.0.0
     */
    public void deleteByName(String name) {
        imageDao.deleteByName(name);
    }
    
    /**
     * saveUpload:
     * 根据上传文件保存到文件夹中
     * @param imageInfo
     * @author lichunping    
     * @since 1.0.0
     */
    public void saveImage(Map<String, Object> imageInfo) {
        Image image = new Image();
        image.setName((String)(imageInfo.get("name")));
        image.setWidth(Integer.valueOf((String) imageInfo.get("width")));
        image.setHeight(Integer.valueOf((String) imageInfo.get("height")));
        image.setType((String)imageInfo.get("type"));
        imageDao.save(image);
        logger.info("save upload image:" +  imagesRealPath + "/" + imageInfo.get("name"));
    }
    
    /**
     * moveUpload:
     * 从临时移动到新的目录下
     * @param fileName
     * @author lichunping    
     * @since 1.0.0
     */
    public void moveUpload(String fileName) {
        String path = tempRealPath + "/" + fileName;
        String toPath = imagesRealPath + "/" + fileName;
        fileUpload.move(path, toPath);
    }
    
    /**
     * getImageList:
     * 根据sort获取Image对象列表信息
     * @return Image列表
     * @author lichunping    
     * @since 1.0.0
     */
    public List<Image> getImageList() throws Exception {
        return imageDao.getImageList();
    }

    /**
     * getRandomImageList:
     * 随机查询N条结果
     * @param num 查询条数
     * @return Image列表
     * @author lichunping    
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public List<Image> getRandomImageList(Integer num) throws Exception {
        String sql = "SELECT * FROM `images` ORDER BY RAND() LIMIT " + num;
        return imageDao.findBySQL(sql, null, Image.class);
    }

    /**
     * listWithPage:
     * 分页获取全部内容
     * @param crit 查询条件
     * @return Image列表
     * @author lichunping    
     * @since 1.0.0
     */
    public Page<Image> listWithPage(Page<Image> page, Integer id) throws Exception {
        if (page == null ) {
            throw new IllegalArgumentException("arguments are illegal");
        }
        Criteria crit = imageDao.createCriteria();
        ProjectionList pl = Projections.projectionList();
        pl.add(Property.forName("id"), "id");
        pl.add(Property.forName("name"), "name");
        pl.add(Property.forName("type"), "type");
        crit.setProjection(pl).setResultTransformer(Transformers.aliasToBean(Image.class));
        return imageDao.findByPage(page, crit);
        
//        return imageDao.findAll(page);
    }
    
}
