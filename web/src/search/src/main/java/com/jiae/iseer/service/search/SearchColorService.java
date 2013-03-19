/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.service.search;

import java.awt.Color;
import java.awt.image.BufferedImage;
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
public interface SearchColorService {
    
    /**
     * 根据图像返回相似颜色图像的结果
     * getSimilarColorList:
     * @author lichunping
     * @param q
     * @return 
     * @since 1.0.0
     */
    public List<String> getSimilarColorList(Color color) throws Exception;
    
    /**
     * 根据颜色值返回颜色对象
     * getColor:
     * @author lichunping
     * @param color
     * @return Color
     * @since 1.0.0
     */
    public Color getColor(String color);
}
