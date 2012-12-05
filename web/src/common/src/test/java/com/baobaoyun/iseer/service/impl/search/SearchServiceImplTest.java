/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.baidu.webos.service.impl.search;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;


import com.baidu.webos.basic.test.SpringTransactionalTestCase;
import com.baidu.webos.service.search.SearchService;

/**
 * ClassName: AppServiceImplTest
 * Description: TODO Add function description
 *
 * @author   lichunping@baidu.com
 * @version  
 * @since    TODO
 * @Date     2011-11-8 下午05:39:37
 *
 * @see      
 */

public class SearchServiceImplTest  extends SpringTransactionalTestCase {
    
//    @Autowired
    @Resource
    private SearchService searchService;
    
    public SearchServiceImplTest() {
        
    }
    
    @Test
    public void testGetResultList() throws Exception {
        List<Object[]> resultList = new ArrayList<Object[]>();
//        resultList = searchService.getWebResultList("%CC%B9%BF%CB%B4%F3%D5%BD");
        resultList = searchService.getWebResultList("人民");
//        resultList = searchService.getWebResultList("中国政府");
        System.out.print("\r\n分析结果测试: \r\n");
        int count = 0;
        assertNotNull("resultList数据为null", resultList);
        if (resultList != null && resultList.size() > 0) {
            for (Iterator<?> iter = resultList.iterator(); iter.hasNext();) {
                count ++;
                Object[] object = (Object[]) iter.next();
                System.out.print(" \r\n  (" + count + ") | href： " + object[0] + " | title : " + object[1] + " | description: " + object[2] + " | "); 
            } 
        }
    }
    
}
