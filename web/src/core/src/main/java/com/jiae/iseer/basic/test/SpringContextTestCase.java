package com.jiae.iseer.basic.test;

import org.hibernate.SessionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * ClassName: SpringTransactionalTestCase
 * Description: Spring测试基类
 * 
 * @author   jarryli@gmail.com
 * @version  
 * @since    TODO
 * @Date     2011-10-26 下午07:02:05
 *
 * @see      
 */

@ContextConfiguration(locations = { "/applicationContext*.xml" })
public class SpringContextTestCase extends AbstractJUnit4SpringContextTests {

    public void flush() {
        flush("sessionFactory");
    }

    public void flush(String sessionFactoryName) {
        ((SessionFactory) applicationContext.getBean(sessionFactoryName)).getCurrentSession().flush();
    }
}
