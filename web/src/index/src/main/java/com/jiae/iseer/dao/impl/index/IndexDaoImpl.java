/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.dao.impl.index;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiae.iseer.dao.index.IndexDao;
import com.jiae.iseer.dao.search.SearchDao;

//import org.hibernate.Query;
//import org.hibernate.Session;


/**
 * ClassName: IndexDaoImpl
 * Description: Index dao层
 * 
 * @author   <a href="mailto:lichunpin@baidu.com">LiChunping</a>
 * @version  
 * @since    1.0.0
 * @Date     2011-9-27 下午02:02:17
 *
 * @see      
 */
@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class IndexDaoImpl implements IndexDao {
    
    public IndexDaoImpl() {
    }


    public static void main(String[] args) {  
        String str = "abc";  
        String str1 = "abc";  
        String str2 = new String("abc");  
        System.out.println(str == str1);   // true
        System.out.println(str1 == "abc");  // true
        System.out.println(str2 == "abc");  // false
        System.out.println(str1 == str2);   // false
        System.out.println(str1.equals(str2)); // true  
        System.out.println(str1 == str2.intern());  // true
        System.out.println(str1.intern() == str2);  // false
        System.out.println(str2 == str2.intern());  // false
        System.out.println(str1.intern() == str2.intern());  // true
        System.out.println(str1.hashCode() == str2.hashCode());  // true
    }  

}
