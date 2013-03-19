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
import net.semanticmetadata.lire.ImageDuplicates;
import net.semanticmetadata.lire.ImageSearchHits;
import net.semanticmetadata.lire.ImageSearcher;
import net.semanticmetadata.lire.ImageSearcherFactory;
import net.semanticmetadata.lire.impl.DocumentFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiae.iseer.service.search.SearchSameService;
import com.jiae.iseer.basic.utils.LoggerUtils;
import com.jiae.iseer.basic.utils.StringUtils;
import com.jiae.iseer.cons.SearchCons;
import com.jiae.iseer.cons.Variables;
import com.jiae.iseer.dao.search.SearchDao;
import com.jiae.iseer.entity.image.Image;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * ClassName: SearchSameServiceImpl
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
public class SearchSameServiceImpl implements SearchSameService {
    
    protected static Logger logger = LoggerFactory.getLogger(SearchSameServiceImpl.class);
    
    private String imagesRealPath = Variables.IMAGES_REAL_PATH;
    private String tempRealPath = Variables.TEMP_REAL_PATH;
    private String indexRealPath = Variables.INDEX_REAL_PATH;
    @Resource
    private SearchDao searchDao;
    
    public SearchSameServiceImpl() {
        
    }
    
    /**
     * 根据图像返回相同图像的结果
     * getSameList:
     * @author lichunping
     * @param image
     * @return List<Image>
     * @since 1.0.0
     */
    public List<String> getSameList(Image image)  throws Exception {

        IndexReader ir = IndexReader.open(FSDirectory.open(new File(indexRealPath)));

        ImageSearcher searcher = ImageSearcherFactory.createDefaultSearcher();
        ImageDuplicates imageDuplicates = searcher.findDuplicates(ir);

        List<String> fileList = new ArrayList<String>();
        String fileName = null;
        // TODO 
        // 查询相同文件有问题，把所有文件都重新查询了一遍，故设定i<1
        // 应该根据上传图像检索相同的图片
        for (int i = 0; imageDuplicates != null && i < 1; i++) {
            fileName = imageDuplicates.getDuplicate(i).toString();
            System.out.println("相同文件名:" + fileName);
            
            Integer idx = -1;
            idx = fileName.lastIndexOf("\\");
            if (idx != -1) {
                fileName = fileName.substring((idx + 1), fileName.length());
            }
            idx = fileName.lastIndexOf("/");
            if (fileName.lastIndexOf("/") != -1) {
                fileName = fileName.substring((idx + 1), fileName.length());
            }
            fileName = fileName.replaceAll("]", "");
            fileList.add(fileName);
        }
        return fileList; 
    }

}
