/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.dao.image;

import java.util.List;

import org.hibernate.Session;

import com.jiae.iseer.basic.dao.GenericDao;
import com.jiae.iseer.entity.image.Image;

/**
 * ClassName: imageDao
 * Description: image dao层对外接口
 * 
 * @author   <a href="mailto:lichunpin@baidu.com">LiChunping</a>
 * @version  
 * @since    TODO
 * @Date     2011-9-27 下午01:56:59
 *
 * @see      
 */

public interface ImageDao extends GenericDao<Image,Integer> {  

    /**
     * getimageById:
     * 根据imageId获取image对象
     * @param imageId
     * @return image对象
     * @author lichunping    
     * @since 1.0.0
     */
    public Image getByImageId(Integer imageId);
    
    
    /**
     * deleteByName:
     * 根据name删除该image
     * @author lichunping    
     * @since 1.0.0
     */
    public int deleteByName(String name);
    
    /**
     * getByName:
     * 根据名称获取image信息
     * @param name
     * @return image列表
     * @author lichunping    
     * @since 1.0.0
     */
    public Image getByName(String name);

    /**
     * getByNames:
     * 根据名称列表获取image信息
     * @param nameList
     * @return image列表
     * @author lichunping    
     * @since 1.0.0
     */
    public List<Image> getByNames(List<String> nameList);
    
    /**
     * getimageList:
     * 查询全部image列表
     * @return image列表
     * @author lichunping    
     * @since 1.0.0
     */
    public List<Image> getImageList();
    
    /**
     * 提供一个获取的session供service调用
     * 多个更新时采用同一个session提交
     * getSession:
     *
     * @return      
     * @since
     */
    public Session getSession();
    
}
