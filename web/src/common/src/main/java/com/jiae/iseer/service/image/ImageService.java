/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.service.image;

import java.util.List;
import java.util.Map;

import com.jiae.iseer.basic.dao.impl.Page;
import com.jiae.iseer.entity.image.Image;


/**
 * ClassName: ImageService
 * Description: 对外接口
 * 
 * @author   <a href="mailto:lichunpin@baidu.com">LiChunping</a>
 * @version  
 * @since    TODO
 * @Date     2011-9-27 下午01:56:59
 *
 * @see      
 */

public interface ImageService {
    
    
    /**
     * get:
     * 根据id记录来获取Image对象
     * @param id
     * @return Image对象
     * @author lichunping    
     * @since 1.0.0
     */
    public Image get(Integer id) throws Exception;
    
    /**
     * getById:
     * 根据ImageId获取Image信息
     * @param ImageId
     * @return Image对象
     * @author lichunping    
     * @since 1.0.0
     */
    public Image getByImageId(Integer ImageId) throws Exception;
    
    
    /**
     * getImageByName:
     * 根据name获取Image信息
     * @param name
     * @return Image对象
     * @author lichunping    
     * @since 1.0.0
     */
    public Image getImageByName(String name) throws Exception;
    
    
    /**
     * delete:
     * 根据ImageId删除Image
     * @param id
     * @author lichunping    
     * @since 1.0.0
     */
    public void delete(Integer ImageId);
    
    /**
     * deleteByName:
     * 根据name删除Image
     * @param name
     * @author lichunping    
     * @since 1.0.0
     */
    public void deleteByName(String name);
    
    /**
     * saveUpload:
     * 根据上传文件保存到文件夹中
     * @param imageInfo
     * @author lichunping    
     * @since 1.0.0
     */
    public void saveImage(Map<String, Object> imageInfo);
    
    /**
     * getImageList:
     * 根据sort获取Image对象列表信息
     * @param sort
     * @return Image列表
     * @author lichunping    
     * @since 1.0.0
     */
    public List<Image> getImageList() throws Exception;
    
    /**
     * moveUpload:
     * 从临时移动到新的目录下
     * @param fileName
     * @author lichunping
     * @since 1.0.0
     */
    public void moveUpload(String fileName);

    /**
     * getRandomImageList:
     * 随机查询N条结果
     * @param sort
     * @return Image列表
     * @author lichunping    
     * @since 1.0.0
     */
    public List<Image> getRandomImageList(Integer num) throws Exception;
    
    /**
     * listWithPage:
     * 分页获取全部内容
     * @param crit 查询条件
     * @return Image列表
     * @author lichunping    
     * @since 1.0.0
     */
    public Page<Image> listWithPage(Page<Image> page, Integer id) throws Exception;
    
}
