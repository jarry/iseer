/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.dao.search;

/**
 * ClassName: SearchDao
 * Description: search dao层对外接口
 * 
 * @author   <a href="mailto:lichunpin@baidu.com">LiChunping</a>
 * @version  
 * @since    TODO
 * @Date     2011-9-27 下午01:56:59
 *
 * @see      
 */

public interface SearchDao {
    
    /**
     * 根据关键词返回网页查询结果
     * getWebResultList:
     * @author lichunping
     * @param q
     * @return 
     * @since 1.0.0
     */
    public String getWebResultList(String url) throws Exception;
    
}
