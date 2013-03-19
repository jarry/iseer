/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.dao.impl.image;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiae.iseer.basic.dao.impl.GenericDaoImpl;
import com.jiae.iseer.dao.image.ImageDao;
import com.jiae.iseer.entity.image.Image;

/**
 * ClassName: ImageDaoImpl
 * Description: image dao层
 * 
 * @author   <a href="mailto:lichunpin@baidu.com">LiChunping</a>
 * @version  
 * @since    1.0.0
 * @Date     2011-9-27 下午02:02:17
 *
 * @see      
 */
@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ImageDaoImpl extends GenericDaoImpl<Image, Integer> implements ImageDao {
    
    /**
     * 构造方法，执行通用Dao
     * 并且给实体class赋值
     */
    public ImageDaoImpl() {
        super();
        this.entityClass = Image.class;
    }
    
    /**
     * getimageById:
     * 根据imageId获取image对象
     * @param imageId
     * @return image对象
     * @author lichunping    
     * @since 1.0.0
     */
    public Image getByImageId(Integer imageId) {
        if (imageId == null) {
            return null;
        }
        
        String hql = "FROM Image a WHERE a.id=? ";
        return (Image) super.findUnique(hql, imageId);
        
//        Query query = super.createQuery(hql);   
//        query.setParameter(0, imageId);
//        return (image) query.uniqueResult();

    }
    
    /**
     * deleteByName:
     * 根据name删除该image
     * @author lichunping    
     * @since 1.0.0
     */
    public int deleteByName(String name) {
        if (name == null) {
            return 0;
        }

        String hql = "DELETE FROM Image a WHERE a.name = :name ";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", name);
        
        return super.batchUpdateOrDelete(hql, paramMap);
    }
    
    /**
     * getByName:
     * 根据名称获取image信息
     * @param name
     * @return image列表
     * @author lichunping    
     * @since 1.0.0
     */
    public Image getByName(String name) {
        if (name == null) {
            return null;
        }
        String hql = "FROM Image a WHERE a.name=? ";
        return (Image) super.findUnique(hql, name);
    }
    
    /**
     * getByNames:
     * 根据名称列表获取image信息
     * @param nameList
     * @return image列表
     * @author lichunping    
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public List<Image> getByNames(List<String> nameList) {
        if (nameList == null) {
            return null;
        }
        String names = "";
        String[] nameArr = new String[nameList.size()];
        nameArr = nameList.toArray(new String[nameArr.length]);
        
        names = StringUtils.join(nameArr, "','");
        String sql = "SELECT * FROM `images` WHERE `name` IN('" + names  + "')";
        return (List<Image>)super.findBySQL(sql, null, Image.class);
    }
    
    /**
     * getimageList:
     * 查询全部image列表
     * @return image列表
     * @author lichunping    
     * @since 1.0.0
     */
    public List<Image> getImageList() {
        return super.findAll();
    } 
}
