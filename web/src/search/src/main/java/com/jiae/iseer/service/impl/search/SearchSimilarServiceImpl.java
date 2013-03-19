/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.service.impl.search;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.semanticmetadata.lire.DocumentBuilder;
import net.semanticmetadata.lire.ImageSearchHits;
import net.semanticmetadata.lire.ImageSearcher;
import net.semanticmetadata.lire.ImageSearcherFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//import com.jiae.iseer.service.app.AppSearchService;
import com.jiae.iseer.service.search.SearchSimilarService;
//import com.jiae.iseer.service.widget.WidgetSearchService;
import com.jiae.iseer.basic.utils.LoggerUtils;
import com.jiae.iseer.basic.utils.StringUtils;
import com.jiae.iseer.cons.SearchCons;
import com.jiae.iseer.cons.Variables;
import com.jiae.iseer.dao.search.SearchDao;
import com.jiae.iseer.entity.image.Image;
//import com.jiae.iseer.entity.app.App;
//import com.jiae.iseer.entity.widget.Widget;

import net.semanticmetadata.lire.DocumentBuilder;
import net.semanticmetadata.lire.ImageSearchHits;
import net.semanticmetadata.lire.ImageSearcher;
import net.semanticmetadata.lire.ImageSearcherFactory;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;
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
public class SearchSimilarServiceImpl implements SearchSimilarService {
    
    protected static Logger logger = LoggerFactory.getLogger(SearchSimilarServiceImpl.class);
    
    private String imagesRealPath = Variables.IMAGES_REAL_PATH;
    private String tempRealPath = Variables.TEMP_REAL_PATH;
    private String indexRealPath = Variables.INDEX_REAL_PATH;
    
    @Resource
    private SearchDao searchDao;
    
    public SearchSimilarServiceImpl() {
        
    }
    
    /**
     * 根据图像返回相似图像的结果
     * getResultList:
     * @author lichunping
     * @param q
     * @return 
     * @since 1.0.0
     */
    public List<String> getSimilarList(BufferedImage image)  throws Exception {
        IndexReader ir = DirectoryReader.open(FSDirectory.open(new File(indexRealPath)));
        ImageSearcher searcher = ImageSearcherFactory.createCEDDImageSearcher(10);
        
        List<String> fileList = new ArrayList<String>();
        ImageSearchHits hits = searcher.search(image, ir);
        String fileName = null;
        for (int i = 0; i < hits.length(); i++) {
            fileName = hits.doc(i).getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)[0];
            
//            System.out.println(hits.score(i) + ": " + 
//                    hits.doc(i).getField(DocumentBuilder.FIELD_NAME_IDENTIFIER).stringValue());
            if (hits.score(i) < 0.1) {
                continue;
            }
            
            Integer idx = -1;
            idx = fileName.lastIndexOf("\\");
            if (idx != -1) {
                fileName = fileName.substring((idx + 1), fileName.length());
            }
            idx = fileName.lastIndexOf("/");
            if (fileName.lastIndexOf("/") != -1) {
                fileName = fileName.substring((idx + 1), fileName.length());
            }
            
            fileList.add(fileName);
        }
        return fileList; 
    }

}