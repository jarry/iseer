/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.service.impl.search;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiae.iseer.service.search.SearchCategoryService;
import com.jiae.iseer.basic.utils.LoggerUtils;
import com.jiae.iseer.basic.utils.StringUtils;
import com.jiae.iseer.cons.SearchCons;
import com.jiae.iseer.dao.search.SearchDao;
import com.jiae.iseer.entity.image.Image;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * ClassName: SearchCategoryServiceImpl
 * Description: service层
 * 
 * @author   <a href="mailto:lichunpin@baidu.com">LiChunping</a>
 * @version  
 * @since    1.0.0
 * @Date     2011-9-27 下午02:02:17
 *
 * @see      
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SearchCategoryServiceImpl implements SearchCategoryService {
    
    protected static Logger logger = LoggerFactory.getLogger(SearchCategoryServiceImpl.class);
    
    @Resource
    private SearchDao searchDao;
    
    public SearchCategoryServiceImpl() {
        
    }
    
    /**
     * 根据图像返回相似图像的结果
     * getResultList:
     * @author lichunping
     * @param q
     * @return 
     * @since 1.0.0
     */
    public List<Object[]> getResultList(Image image)  throws Exception {
        // TODO
        return null;
    }

}
