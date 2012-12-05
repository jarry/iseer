/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.dao.impl.list;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.hibernate.Query;
//import org.hibernate.Session;


/**
 * ClassName: AppDaoImpl
 * Description: app dao层
 * 
 * @author   <a href="mailto:lichunpin@baidu.com">LiChunping</a>
 * @version  
 * @since    1.0.0
 * @Date     2011-9-27 下午02:02:17
 *
 * @see      
 */
public class ListDaoImpl {
    

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
