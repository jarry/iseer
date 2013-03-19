/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.dao.impl.search;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiae.iseer.basic.utils.NetUtils;
import com.jiae.iseer.dao.search.SearchDao;

/**
 * ClassName: SearchDaoImpl
 * Description: Search dao层
 * 
 * @author   <a href="mailto:lichunpin@baidu.com">LiChunping</a>
 * @version  
 * @since    TODO
 * @Date     2011-9-27 下午02:02:17
 *
 * @see      
 */

@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SearchDaoImpl implements SearchDao {
    
    public SearchDaoImpl() {
    }

    /**
     * 根据关键词返回网页查询结果
     * getWebResult:
     * @author lichunping
     * @param q
     * @return 
     * @throws Exception 
     * @since 1.0.0
     */
    public String getWebResultList(String url) throws Exception {
        if (url == null) {
            return null;
        }
        return NetUtils.getContentByUrl(url);
    }
}
