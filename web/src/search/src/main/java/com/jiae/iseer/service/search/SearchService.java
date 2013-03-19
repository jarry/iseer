/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.service.search;

import java.util.List;

import com.jiae.iseer.entity.image.Image;



/**
 * ClassName: SearchService
 * Description: Search service层对外接口
 * 
 * @author   <a href="mailto:lichunpin@baidu.com">LiChunping</a>
 * @version  
 * @since    1.0.0
 * @Date     2011-9-27 下午01:56:59
 *
 * @see      
 */
public interface SearchService {
    
    /**
     * 根据名称列表返回Image对象
     * getImagesList:
     * @author lichunping
     * @param q
     * @return 
     * @since 1.0.0
     */
    public List<Image> getImageList(List<String> nameList);
    
    
    /**
     * 根据关键词返回网页查询结果
     * getWebResultList:
     * @author lichunping
     * @param q
     * @return 
     * @since 1.0.0
     */
    public List<Object[]> getWebResultList(String q);
    
}
