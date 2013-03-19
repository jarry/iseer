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
public interface SearchCategoryService {
    
    /**
     * 根据图像返回相似图像的结果
     * getResultList:
     * @author lichunping
     * @param q
     * @return 
     * @since 1.0.0
     */
    public List<Object[]> getResultList(Image image) throws Exception;
    
}
