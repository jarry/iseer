/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.service.index;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.semanticmetadata.lire.utils.FileUtils;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.jiae.iseer.module.index.MetadataBuilder;
import com.jiae.iseer.module.index.ParallelIndexer;


/**
 * ClassName: IndexService
 * Description: Index service层对外接口
 * 
 * @author   <a href="mailto:lichunpin@baidu.com">LiChunping</a>
 * @version  
 * @since    TODO
 * @Date     2011-9-27 下午01:56:59
 *
 * @see      
 */

public interface IndexService {
    /**
     * 将指定的文件夹下的文件全部加入到索引中
     * createIndex:
     * @author lichunping
     * @param dirPath
     * @throws Exception 
     * @since 1.0.0
     */
    public void createIndexDir(String dirPath) throws Exception;
    
    /**
     * 将某个文件加入到索引中
     * createIndex:
     * @author lichunping
     * @param filePath
     * @throws Exception 
     * @since 1.0.0
     */
    public void createIndexFile(String filePath) throws Exception;

    /**
     * 将指定的图片文件列表全部加入到索引中
     * createIndex:
     * @author lichunping
     * @param images
     * @throws Exception 
     * @since 1.0.0
     */
    public void createIndex(ArrayList<String> images) throws Exception;
}
