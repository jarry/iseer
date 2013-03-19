/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.service.impl.search;
import java.awt.Color;
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
import net.semanticmetadata.lire.impl.DocumentFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiae.iseer.service.search.SearchColorService;
import com.jiae.iseer.basic.utils.LoggerUtils;
import com.jiae.iseer.basic.utils.StringUtils;
import com.jiae.iseer.cons.SearchCons;
import com.jiae.iseer.cons.Variables;
import com.jiae.iseer.dao.search.SearchDao;
import com.jiae.iseer.entity.image.Image;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * ClassName: SearchColorServiceImpl
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
public class SearchColorServiceImpl implements SearchColorService {
    
    protected static Logger logger = LoggerFactory.getLogger(SearchColorServiceImpl.class);
    private String imagesRealPath = Variables.IMAGES_REAL_PATH;
    private String tempRealPath = Variables.TEMP_REAL_PATH;
    private String indexRealPath = Variables.INDEX_REAL_PATH;
    @Resource
    private SearchDao searchDao;
    
    public SearchColorServiceImpl() {
        
    }
    
    public Color getColor(String color) {
        if (color == "black") {
            return Color.black;
        }
        if (color == "white") {
            return Color.white;
        }
        if (color == "red") {
            return Color.red;
        }
        if (color == "green") {
            return Color.green;
        }
        if (color == "yellow") {
            return Color.yellow;
        }
        if (color == "blue") {
            return Color.blue;
        }
        return Color.black;
    }
    
    /**
     * 根据图像返回相似颜色图像的结果
     * getSimilarColorList:
     * @author lichunping
     * @param q
     * @return 
     * @since 1.0.0
     */
    public List<String> getSimilarColorList(Color color) throws Exception {
        if (color == null) {
            color = Color.red;
        }

        IndexReader ir = IndexReader.open(FSDirectory.open(new File(indexRealPath)));
        // TODO
        // 应该仅仅根据颜色值查询图像颜色
        // 索引中可能没有颜色相关属性，索引内存空间小退出
        // 需要重新再设定根据颜色查询
//        ImageSearcher searcher = ImageSearcherFactory.createColorOnlySearcher(10);
////      int numDocs = ir.numDocs();
//        Document document = DocumentFactory.createColorOnlyDocument(color);
//        ImageSearchHits hits = searcher.search(document, ir);
        
        ImageSearcher searcher = ImageSearcherFactory.createCEDDImageSearcher(10);
        BufferedImage img = DocumentFactory.createColorOnlyImage(color);
        ImageSearchHits hits = searcher.search(img, ir);

        List<String> fileList = new ArrayList<String>();
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
