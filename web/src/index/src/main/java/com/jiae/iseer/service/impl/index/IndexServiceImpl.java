/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.service.impl.index;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import javax.annotation.Resource;
import javax.imageio.ImageIO;

import net.semanticmetadata.lire.utils.FileUtils;
import net.semanticmetadata.lire.utils.ImageUtils;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiae.iseer.cons.Variables;
import com.jiae.iseer.module.index.MetadataBuilder;
import com.jiae.iseer.module.index.ParallelIndexer;
import com.jiae.iseer.service.impl.search.SearchServiceImpl;
import com.jiae.iseer.service.index.IndexService;
import com.jiae.iseer.service.list.ListService;
import com.jiae.iseer.service.search.SearchService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;


/**
 * ClassName: IndexServiceImpl
 * Description: Index service层实现
 * 
 * @author   <a href="mailto:lichunpin@baidu.com">LiChunping</a>
 * @version  
 * @since    TODO
 * @Date     2011-9-27 下午02:02:17
 *
 * @see      
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class IndexServiceImpl implements IndexService {

    protected static Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);
    
    private String imagesRealPath = Variables.IMAGES_REAL_PATH;
    private String tempRealPath = Variables.TEMP_REAL_PATH;
    private String indexRealPath = Variables.INDEX_REAL_PATH;
    
    /**
     * 将指定的文件夹下的文件全部加入到索引中
     * createIndex:
     * @author lichunping
     * @param dirFile
     * @throws Exception 
     * @since 1.0.0
     */
    public void createIndexDir(String dirPath) throws Exception {
        ArrayList<String> images = FileUtils.getAllImages(new File(dirPath), false);
        createIndex(images);
    }
    
    /**
     * 将某个文件加入到索引中
     * createIndex:
     * @author lichunping
     * @param filePath
     * @throws Exception 
     * @since 1.0.0
     */
    public void createIndexFile(String filePath) throws Exception {
        ArrayList<String> images = new ArrayList<String>();
        images.add(filePath);
        createIndex(images);
//        DocumentBuilder db = DocumentBuilderFactory.getCEDDDocumentBuilder();
//        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_41, new SimpleAnalyzer(Version.LUCENE_41));
//        IndexWriter iw = new IndexWriter(FSDirectory.open(new File(indexRealPath)), conf);
//        Document doc = db.createDocument(new FileInputStream(file), file.getName());
//        iw.addDocument(doc);
//        iw.close();
    }

    /**
     * 将指定的图片文件列表全部加入到索引中
     * createIndex:
     * @author lichunping
     * @param images
     * @throws Exception 
     * @since 1.0.0
     */
    public void createIndex(ArrayList<String> images) throws Exception {
        for (String img : images) {
            System.out.print("\r\n" + img);
        }
        if(images == null) {
            logger.error("images is null.");
        }
        
        MetadataBuilder builder = new MetadataBuilder();
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_41, new WhitespaceAnalyzer(Version.LUCENE_41));
        IndexWriter iw = new IndexWriter(FSDirectory.open(new File(indexRealPath)), conf);
        
//    // 采用并行(异步)处理图像索引
//      ParallelIndexer parallelIndexer = new ParallelIndexer(images, builder);
//      parallelIndexer.run();
        
//        DocumentBuilder builder = DocumentBuilderFactory.getCEDDDocumentBuilder();
//        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_41, new WhitespaceAnalyzer(Version.LUCENE_41));
//        IndexWriter iw = new IndexWriter(FSDirectory.open(new File(indexRealPath)), conf);
        
//        采取即时读取全部图片方式
        // TODO
        // 由于有些jpg图像信息修改了，故无法被索引，需要修改这些图像
        // 应该在大内存机上重新索引全部图像
        for (Iterator<String> it = images.iterator(); it.hasNext(); ) {
            String filePath = it.next();
            
                BufferedImage img = null;
                if (ImageUtils.getImageType(filePath) == "jpg") {
                    try {
                        JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(new FileInputStream( new File(filePath) ) );
                        img = decoder.decodeAsBufferedImage();
                    } catch (Exception e) {
                        logger.error("JPEGCodec reading image:"  + filePath + " or indexing error.");
                    }
                } else {
                    try {
                        img = ImageIO.read(new FileInputStream(filePath));
                    } catch (Exception e) {
                        logger.error("ImageIO reading image:"  + filePath + " or indexing error.");
                        e.printStackTrace();
                    }
                }
                if (img != null) {
                    Document document = builder.createDocument(img, filePath);
                    iw.addDocument(document);
                    logger.info("reading image:"  + filePath + " and indexing success.");
                } else {
                    logger.error("reading image:"  + filePath + " or indexing error.");
                }
        }
        iw.commit();
        iw.close();
        logger.info("images indexing done.");
    }
    
}
