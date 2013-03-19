package com.jiae.iseer.basic.test;

import java.security.SecureRandom;
import java.util.Random;

//import javax.sql.DataSource;

import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * ClassName: SpringTransactionalTestCase
 * Description: 
 * Spring支持数据库事务、依赖注入的基于JUnit 3.8的基类的便捷简写与flush()函数.
 * 如果需要默认载入更多的applicatioContext.xml,在项目中重写本类.
 * 
 * @author   jarryli@gmail.com
 * @version  
 * @since    TODO
 * @Date     2011-10-26 下午07:02:05
 *
 * @see      
 */
@ContextConfiguration(locations = { "/applicationContext*.xml" })
public class SpringTransactionalTestCase extends AbstractTransactionalJUnit4SpringContextTests {

//    @Autowired  
//    @Qualifier("DataSource")  
//    private DataSource dataSource;
//    
//    public DataSource getDataSource() {
//        return dataSource;
//    }
    
    public void flush() {
        flush("sessionFactory");
    }

    /**
     * 刷新sessionFactory,强制Hibernate执行SQL以验证ORM配置
     * 
     * @param sessionFactoryName 
     */
    public void flush(String sessionFactoryName) {
        ((SessionFactory) applicationContext.getBean(sessionFactoryName)).getCurrentSession().flush();
    }

    /**
     * 获得随机数字字符串
     */
    public static String getTokenid(int length) {
        if (0 == length) {
            return "";
        }
        Random rand = new SecureRandom();
        StringBuffer sb = new StringBuffer();
        while (sb.length() < length) {
            sb.append(rand.nextInt());
        }
        return sb.substring(0, length);
    }

}
