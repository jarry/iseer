/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.NoResultException;

import org.hibernate.Criteria;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.jiae.iseer.basic.dao.GenericDao;
import com.jiae.iseer.basic.dao.MySqlOrder;
import com.jiae.iseer.basic.utils.BeanUtils;
import com.jiae.iseer.basic.utils.LoggerUtils;
import com.jiae.iseer.basic.utils.TimeLogUtils;

/**
 * ClassName: GenericDaoImpl Description: Hibernate的范型基类
 * 以在service类中直接创建使用或继承出DAO子类，在多个Service类中共享DAO操作
 * 参考Spring自带的例子，HibernateTemplate.
 * 通过Hibernate的sessionFactory.getCurrentSession()获得session,直接使用Hibernate原生API.
 * 
 * @param <T>
 *            DAO操作的对象类型
 * @param <PK>
 *            主键类型
 * @author jarryli@gmail.com
 * @version
 * @since 1.0.0
 * @Date 2011-10-8 下午02:24:33
 * 
 * @see
 */
@SuppressWarnings("unchecked")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public abstract class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    protected Logger         logger = LoggerFactory.getLogger(getClass());

    protected SessionFactory sessionFactory;

    protected Class<T>       entityClass;

    public GenericDaoImpl() {

    }

    public GenericDaoImpl(SessionFactory sessionFactory, Class<T> entityClass) {
        this.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Integer save(T entity) {
        Assert.notNull(entity);
        Integer id = (Integer) getSession().save(entity);
        logger.info("save entity: {}", entity);
        return id;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Long saveWithLongKey(T entity) {
        Assert.notNull(entity);
        Long id = (Long) getSession().save(entity);
        logger.info("save entity: {}", entity);
        return id;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveOrUpdate(T entity) {
        Assert.notNull(entity);
        getSession().saveOrUpdate(entity);
        logger.info("save entity: {}", entity);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void delete(T entity) {
        Assert.notNull(entity);
        getSession().delete(entity);
        logger.info("delete entity: {}", entity);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void delete(PK id) {
        Assert.notNull(id);
        delete(get(id));
    }

    public List<T> findAll() {
        return findByCriteria();
    }

    public Page<T> findAll(Page<T> page) {
        return findByCriteria(page);
    }

    /**
     * 根据id获取对象.
     */
    public T get(final PK id) {
        return (T) getSession().get(entityClass, id);
    }

    public T load(final PK id) {
        return (T) getSession().load(entityClass, id);
    }

    /**
     * 按HQL查询对象列表.
     * 
     * @param hql
     *            hql语句
     * @param values
     *            数量可变的参数
     */
    public List find(String hql, Object... values) {
        return createQuery(hql, values).list();
    }

    /**
     * 按SQL查询对象列表.
     * 
     * @param sql
     *            sql语句
     * @param paramMap
     *            参数Map
     */
    public List findBySQL(String sql, Map<String, Object> paramMap) {
        return createSQLQuery(sql, paramMap).list();
    }

    /**
     * 按HQL分页查询. 暂不支持自动获取总结果数,需用户另行执行查询.
     * 
     * @param page
     *            分页参数.包括pageSize 和firstResult.
     * @param hql
     *            hql语句.
     * @param values
     *            数量可变的参数.
     * 
     * @return 分页查询结果,附带结果列表及所有查询时的参数.
     */
    public Page<T> find(Page<T> page, String hql, Object... values) {
        Assert.notNull(page);

        if (page.isAutoCount()) {
            logger.warn("HQL查询暂不支持自动获取总结果数,hql为{}", hql);
        }
        Query q = createQuery(hql, values);
        if (page.isFirstSetted()) {
            q.setFirstResult(page.getFirst());
        }
        if (page.isPageSizeSetted()) {
            q.setMaxResults(page.getPageSize());
        }
        page.setResult(q.list());
        return page;
    }

    /**
     * 按HQL查询唯一对象. 当无结果或结果多余一个时，返回null
     */
    public Object findUnique(String hql, Object... values) {
        Object res = null;
        try {
            res = createQuery(hql, values).uniqueResult();
        } catch (NoResultException ex) {
            return res;
        } catch (NonUniqueResultException ex) {
            LoggerUtils.error(logger,
                    "GenericDaoImp::findUnique() has no unique result", ex);
            return null;
        }
        return res;
    }

    /**
     * 按HQL查询Intger类形结果.
     */
    public Integer findInt(String hql, Object... values) {
        return (Integer) findUnique(hql, values);
    }

    /**
     * 按HQL查询Long类型结果.
     */
    public Long findLong(String hql, Object... values) {
        return (Long) findUnique(hql, values);
    }

    /**
     * 按Criterion查询对象列表.
     * 
     * @param criterion
     *            数量可变的Criterion.
     */
    public List<T> findByCriteria(Criterion... criterion) {
        return createCriteria(criterion).list();
    }

    /**
     * 按Criterion查询对象列表.
     * 
     * @param criterion
     *            数量可变的Criterion.
     */
    public List<T> findByCriteria(Criteria criteria) {
        if (criteria == null) {
            return null;
        }
        return criteria.list();
    }

    /**
     * 按Criterion分页查询.
     * 
     * @param page
     *            分页参数.包括pageSize、firstResult、orderBy、asc、autoCount.
     *            其中firstResult可直接指定,也可以指定pageNo. autoCount指定是否动态获取总结果数.
     * 
     * @param criterion
     *            数量可变的Criterion.
     * @return 分页查询结果.附带结果列表及所有查询时的参数.
     */
    public Page<T> findByCriteria(Page page, Criterion... criterion) {
        Assert.notNull(page);
        Criteria c = createCriteria(criterion);
        return this.findByCriteria(page, c);
    }

    /**
     * 按Criterion分页查询.
     * 
     * @param page
     *            分页参数.包括pageSize、firstResult、orderBy、asc、autoCount.
     *            其中firstResult可直接指定,也可以指定pageNo. autoCount指定是否动态获取总结果数.
     * 
     * @param criterion
     *            数量可变的Criterion.
     * @return 分页查询结果.附带结果列表及所有查询时的参数.
     */
    public Page<T> findByCriteria(Page page, Criteria c) {
        Assert.notNull(page);
        Assert.notNull(c);
        this.findByCriteria4List(page, c);
        return page;
    }

    /**
     * 这里之所以使用List作为返回值，是因为有时候做映射查询，结果不是bo的list而是一两个属性而已
     * 
     * @param page
     * @param c
     * @return
     */
    public List findByCriteria4List(Page page, Criteria c) {
        Assert.notNull(page);
        Assert.notNull(c);
        int totalCount = 0;
        if (page.isAutoCount()) {
            long startTime = System.currentTimeMillis();
            totalCount = countQueryResult(page, c);
            page.setTotalCount(totalCount);
            if (totalCount <= 0) {
                page.setResult(new ArrayList<T>());
                return new ArrayList<T>();
            }
            long usedTime = System.currentTimeMillis() - startTime;
            if (usedTime >= 10000) {
                TimeLogUtils.info("GenericDaoImpl",
                        "Query for list count exceeds 10s: " + c.toString());
            }
        }
        if (page.isFirstSetted()) {
            c.setFirstResult(page.getFirst());
        }
        if (page.isPageSizeSetted()) {
            c.setMaxResults(page.getPageSize());
        }
        if (page.isOrderBySetted()) {
            List<String> orderByList = page.getOrderByList();
            List<String> orderList = page.getOrderList();
            // 如果orderBy和order不成对出现则不排序
            if (orderByList != null && orderList != null
                    && orderByList.size() == orderList.size()) {
                for (int i = 0; i < orderByList.size(); i++) {
                    String orderBy = orderByList.get(i);
                    String order = orderList.get(i);
                    if (order.endsWith(QueryParameter.ASC)) {
                        c.addOrder(MySqlOrder.asc(orderBy));
                    } else {
                        c.addOrder(MySqlOrder.desc(orderBy));
                    }
                }
            }
        }
        long startTime = System.currentTimeMillis();
        List result = c.list();
        long usedTime = System.currentTimeMillis() - startTime;
        if (usedTime >= 10000) {
            TimeLogUtils.info("GenericDaoImpl", "Query for list exceeds 10s: "
                    + c.toString());
        }
        page.setResult(result);
        return result;
    }

    /**
     * 按属性查找对象列表.
     */
    public List<T> findByProperty(String propertyName, Object value) {
        Assert.hasText(propertyName);
        return createCriteria(Restrictions.eq(propertyName, value)).list();
    }

    /**
     * 按属性查找唯一对象.
     */
    public T findUniqueByProperty(String propertyName, Object value) {
        Assert.hasText(propertyName);
        return (T) createCriteria(Restrictions.eq(propertyName, value))
                .uniqueResult();
    }

    /**
     * 根据查询函数与参数列表创建Query对象,后续可进行更多处理,辅助函数.
     */
    public Query createQuery(String queryString, Object... values) {
        Assert.hasText(queryString);
        Query queryObject = getSession().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                queryObject.setParameter(i, values[i]);
            }
        }
        return queryObject;
    }

    /**
     * @param querySQL
     *            sql
     * @param parameterMap
     *            参数 根据查询函数与参数列表创建SQLQuery对象,后续可进行更多处理,辅助函数.
     */
    public Query createSQLQuery(String querySQL,
            Map<String, Object> parameterMap) {
        Assert.hasText(querySQL);
        Query queryObject = getSession().createSQLQuery(querySQL);
        if (parameterMap != null && parameterMap.size() > 0) {
            for (Entry<String, Object> entry : parameterMap.entrySet()) {
                queryObject.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return queryObject;
    }

    /**
     * 根据Criterion条件创建Criteria,后续可进行更多处理,辅助函数.
     * 
     * @param criterions
     *            查询条件
     */
    public Criteria createCriteria(Criterion... criterions) {
        Criteria criteria = this.getCriteria();
        for (Criterion c : criterions) {
            if (c != null) {
                criteria.add(c);
            }
        }
        return criteria;
    }

    /**
     * 创建Criteria,并返回.
     */
    public Criteria getCriteria() {
        Criteria criteria = getSession().createCriteria(entityClass);
        return criteria;
    }

    /**
     * 判断对象的属性值在数据库内是否唯一.
     * 
     * 在修改对象的情景下,如果属性新修改的值(value)等于属性原值(orgValue)则不作比较.
     * 传回orgValue的设计侧重于从页面上发出Ajax判断请求的场景 否则需要SS2里那种以对象ID作为第3个参数的isUnique函数.
     */
    public boolean isPropertyUnique(String propertyName, Object newValue,
            Object orgValue) {
        if (newValue == null || newValue.equals(orgValue))
            return true;

        Object object = findUniqueByProperty(propertyName, newValue);
        return (object == null);
    }

    /**
     * 通过count查询获得本次查询所能获得的对象总数.
     * 
     * @return page对象中的totalCount属性将赋值.
     */
    public int countQueryResult(Page<T> page, Criteria c) {
        CriteriaImpl impl = (CriteriaImpl) c;
        // 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
        Projection projection = impl.getProjection();
        ResultTransformer transformer = impl.getResultTransformer();
        List<CriteriaImpl.OrderEntry> orderEntries = null;
        try {
            orderEntries = (List) BeanUtils.getFieldValue(impl, "orderEntries");
            BeanUtils.setFieldValue(impl, "orderEntries", new ArrayList());
        } catch (Exception e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }
        // 执行Count查询
//        int totalCount = (Integer) c.setProjection(Projections.rowCount())
//                .uniqueResult();
        long count = (long) c.setProjection(Projections.rowCount()).uniqueResult();
        int totalCount = Integer.parseInt(String.valueOf(count));
        
        // 将之前的Projection和OrderBy条件重新设回去
        c.setProjection(projection);
        if (projection == null) {
            c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        if (transformer != null) {
            c.setResultTransformer(transformer);
        }
        try {
            BeanUtils.setFieldValue(impl, "orderEntries", orderEntries);
        } catch (Exception e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }

        if (totalCount < 1) {
            return -1;
        } else {
            return totalCount;
        }
    }

    /**
     * batchUpdateOrDelete: 根据hql语句批量插入或更新对象
     * 
     * @param hql
     *            hql语句
     * @param parameterMap
     *            参数对象
     * @author lichunping
     * @since 1.0.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int batchUpdateOrDelete(String hql, Map<String, Object> parameterMap) {
        Assert.hasText(hql);
        Query queryObject = getSession().createQuery(hql);
        if (parameterMap != null && parameterMap.size() > 0) {
            for (Entry<String, Object> entry : parameterMap.entrySet()) {
                queryObject.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return queryObject.executeUpdate();
    }

    /**
     * batchUpdateOrDeleteBySql: 根据sql语句批量插入或更新对象
     * 
     * @param sql
     *            sql语句
     * @param parameterMap
     *            参数对象
     * @author lichunping
     * @since 1.0.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int batchUpdateOrDeleteBySql(String sql,
            Map<String, Object> parameterMap) {
        Assert.hasText(sql);
        Query queryObject = getSession().createSQLQuery(sql);
        if (parameterMap != null && parameterMap.size() > 0) {
            for (Entry<String, Object> entry : parameterMap.entrySet()) {
                queryObject.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return queryObject.executeUpdate();
    }

    /**
     * 刷新数据
     * 
     */
    public void flush() {
        this.getSession().flush();

    }

    /**
     * 将对象从一级缓存中去除
     * 
     */
    public void evict(Object obj) {
        this.getSession().evict(obj);
    }

    public Page<T> findByPage(Page<T> page, Criteria criteria) {
        this.findByCriteria4List(page, criteria);
        return page;
    }

    /**
     * 根据hql返回结构，并手动设置数量。
     * 
     */
    public List find(Page page, String hql, int totalCount, Object... values) {
        Assert.notNull(page);
        Query q = createQuery(hql, values);
        if (page.isAutoCount()) {
            page.setTotalCount(totalCount);
            if (totalCount <= 0) {
                return new ArrayList();
            }
        }
        if (page.isFirstSetted()) {
            q.setFirstResult(page.getFirst());
        }
        if (page.isPageSizeSetted()) {
            q.setMaxResults(page.getPageSize());
        }

        return q.list();
    }

    /**
     * 按SQL查询对象列表.
     * 
     * @param sql
     *            sql语句
     * @param paramMap
     *            参数Map
     * @param clazz
     *            类型，用于转换list结果到java类
     */
    public List findBySQL(String sql, Map<String, Object> paramMap, Class clazz) {
        SQLQuery query = (SQLQuery) createSQLQuery(sql, paramMap);
        query.addEntity(clazz);
        return query.list();
    }

    /**
     * 按SQL查询唯一对象.
     * 
     * @param sql
     *            sql语句
     * @param paramMap
     *            参数Map
     * @param clazz
     *            类型，用于转换list结果到java类
     */
    public T findUniqueBySQL(String sql, Map<String, Object> paramMap,
            Class clazz) {
        SQLQuery query = (SQLQuery) createSQLQuery(sql, paramMap);
        query.addEntity(clazz);
        return (T) query.uniqueResult();
    }

}
