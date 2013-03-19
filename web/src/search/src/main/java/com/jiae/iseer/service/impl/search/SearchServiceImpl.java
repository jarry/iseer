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

import com.jiae.iseer.service.search.SearchService;
import com.jiae.iseer.basic.utils.LoggerUtils;
import com.jiae.iseer.basic.utils.StringUtils;
import com.jiae.iseer.cons.SearchCons;
import com.jiae.iseer.dao.image.ImageDao;
import com.jiae.iseer.dao.search.SearchDao;
import com.jiae.iseer.entity.image.Image;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * ClassName: SearchServiceImpl
 * Description: search service层
 * TODO // 目前暂提供网页搜索以及app与widget的简单搜索，以后再更新其他的
 * @author   <a href="mailto:lichunpin@baidu.com">LiChunping</a>
 * @version  
 * @since    1.0.0
 * @Date     2011-9-27 下午02:02:17
 *
 * @see      
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SearchServiceImpl implements SearchService {
    
    protected static Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);
    
    @Resource
    private SearchDao searchDao;
    @Resource
    private ImageDao imageDao;
    
    private String webSearchUrl = SearchCons.WEB_SEARCH_URL;
    private String webSearchParam = SearchCons.WEB_SEARCH_PARAM;
    
    public SearchServiceImpl() {
    }
       

    /**
     * 根据字符串内容(必须是标准xml字符串)解析为XML，并且返回列表
     * parseXML:
     * TODO // 本方法有待完善，需要按照dom结构获取数据
     * @author lichunping
     * @param page
     * @return 
     * @throws DocumentException 
     * @since 1.0.0
     */
    @SuppressWarnings("unused")
    private List<Object[]> parseXML(String page) throws DocumentException {
        if (page == null) {
            return null;
        }
        List<Object[]> resultList = new ArrayList<Object[]>();
        Document doc = DocumentHelper.parseText(page);
        Element root = doc.getRootElement();
        for ( Iterator<?> i = root.elementIterator(); i.hasNext(); ) {
            Element element = (Element) i.next();
            resultList.add(new Object[]{element.getNodeTypeName(), element.getName(), element.getText()});
         }
        return resultList;
    }
    
    /**
     * 根据关键词返回网页查询结果
     * TODO // 正式使用时从PS接口获取网页搜索结果
     * parseResult:
     * @author lichunping
     * @param q
     * @return 
     * @since 1.0.0
     */
    private List<Object[]> parseResult(String page) {
        if (page == null) {
            return null;
        }
        page  = StringUtils.filterScript(page.replaceAll("[\\n|\\r|\\t]", ""));
        List<Object[]> resultList = new ArrayList<Object[]>();
        
        // 获取网页结果页内容，根据<h3>与</br>来查询
        String regex = null;
//        regex = "<table.*class=\"result\".*<td.*>(.*href=\"(.*)\".*)</td>.*?</table>";
//        regex = "<a onmousedown=\"return c[\\s\\S]+?href=\"([\\s\\S]+?)\"[\\s\\S]+?target=\"_blank\">([\\s\\S]+?)</a>([\\s\\S]+?)<br";
        regex = "<h3.*?href=\"(.*?)\".*?>(.*?)</a.*?</h3>(.*?)<br";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);  
        Matcher m = p.matcher(page);   
        
        Object[] object = null; 
        
        while (m.find()) {   
          object = new Object[]{
                  StringUtils.filterHtml(m.group(1)).trim(), 
                  StringUtils.filterHtml(m.group(2)).trim(), 
                  StringUtils.filterHtml(m.group(3)).trim()
                  };
          resultList.add(object);  
            
        }       
        
        return resultList;
    }
    
    /**
     * 根据名称列表返回Image对象
     * getImagesList:
     * @author lichunping
     * @param q
     * @return 
     * @since 1.0.0
     */
    public List<Image> getImageList(List<String> nameList) {
        List<Image> imageList= new ArrayList<Image>();
        imageList = imageDao.getByNames(nameList);
        return imageList;
    }
    
    /**
     * 根据关键词返回网页查询结果
     * getWebResultList:
     * @author lichunping
     * @param q
     * @return 
     * @since 1.0.0
     */
    public List<Object[]> getWebResultList(String q) {
        if (q == null) {
            return null;
        }
       
        String url =  webSearchUrl + "?" + webSearchParam + "="+ q;
        String content = null;
        
        try {
            content = searchDao.getWebResultList(url);
        } catch (Exception e) {
            LoggerUtils.error(logger, "getWebResultList: " + q + " error", e);
        }
        
        if (content != null) {
//            try {
//                List<Object[]> resultList = new ArrayList<Object[]>();
//                resultList = parseXML(content);
//                return resultList;
//            } catch (DocumentException e) {
//                LoggerUtils.info(logger, "getWebResultList: " + q, e);
//            }        
            return parseResult(content);
        }
        return null;
    }

    

}
