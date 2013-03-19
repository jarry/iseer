/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;

import com.jiae.iseer.basic.dao.impl.Page;

/**
 * ClassName: GenericDao
 * Description: Dao基类泛型接口
 *
 * @author   jarryli@gmail.com
 * @version  
 * @since    TODO
 * @Date     2011-11-1 下午07:15:32
 *
 * @see      
 */

public interface GenericDao<T, PK extends Serializable> {

    public Integer save(T entity);

    public Long saveWithLongKey(T entity);

    public void saveOrUpdate(T entity);

    public void delete(T entity);

    public void delete(PK id);

    public List<T> findAll();

    public Page<T> findAll(Page<T> page);

    /**
     * 按id获取对象.
     */
    public T get(final PK id);

    public T load(final PK id);

    /**
     * 按HQL查询对象列表.
     * 
     * @param hql
     *            hql语句
     * @param values
     *            数量可变的参数
     */
    @SuppressWarnings("unchecked")
    public List find(String hql, Object... values);

    /**
     * 按SQL查询对象列表.
     * 
     * @param sql
     *            sql语句
     * @param paramMap
     *            参数Map
     */
    @SuppressWarnings("unchecked")
    public List findBySQL(String sql, Map<String, Object> paramMap);

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
    @SuppressWarnings("unchecked")
    public List findBySQL(String sql, Map<String, Object> paramMap, Class clazz);

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
    public Page<T> find(Page<T> page, String hql, Object... values);

    /**
     * 按HQL查询唯一对象.
     */
    public Object findUnique(String hql, Object... values);

    /**
     * 按HQL查询Intger类形结果.
     */
    public Integer findInt(String hql, Object... values);

    /**
     * 按HQL查询Long类型结果.
     */
    public Long findLong(String hql, Object... values);

    /**
     * 按Criterion查询对象列表.
     * 
     * @param criterion
     *            数量可变的Criterion.
     */
    public List<T> findByCriteria(Criterion... criterion);

    /**
     * 按Criterion分页查询.
     * 
     * @param page
     *            分页参数.包括pageSize、firstResult、orderBy、asc、autoCount. 其中firstResult可直接指定,也可以指定pageNo.
     *            autoCount指定是否动态获取总结果数.
     * 
     * @param criterion
     *            数量可变的Criterion.
     * @return 分页查询结果.附带结果列表及所有查询时的参数.
     */
    @SuppressWarnings("unchecked")
    public Page<T> findByCriteria(Page page, Criterion... criterion);

    /**
     * 按属性查找对象列表.
     */
    public List<T> findByProperty(String propertyName, Object value);

    /**
     * 按属性查找唯一对象.
     */
    public T findUniqueByProperty(String propertyName, Object value);

    /**
     * 根据查询函数与参数列表创建Query对象,后续可进行更多处理,辅助函数.
     */
    public Query createQuery(String queryString, Object... values);

    /**
     * 根据Criterion条件创建Criteria,后续可进行更多处理,辅助函数.
     */
    public Criteria createCriteria(Criterion... criterions);

    /**
     * 判断对象的属性值在数据库内是否唯一.
     * 
     * 在修改对象的情景下,如果属性新修改的值(value)等于属性原值(orgValue)则不作比较. 传回orgValue的设计侧重于从页面上发出Ajax判断请求的场景.
     * 否则需要SS2里那种以对象ID作为第3个参数的isUnique函数.
     */
    public boolean isPropertyUnique(String propertyName, Object newValue, Object orgValue);

    /**
     * 通过count查询获得本次查询所能获得的对象总数.
     * 
     * @return page对象中的totalCount属性将赋值.
     */
    public int countQueryResult(Page<T> page, Criteria c);

    /**
     * batchUpdateOrDelete:
     * 根据hql语句批量插入或更新对象
     * @param hql hql语句
     * @param parameterMap 参数对象
     * @author lichunping    
     * @since 1.0.0
     */
    public int batchUpdateOrDelete(String hql, Map<String, Object> parameterMap);

    /**
     * batchUpdateOrDeleteBySql:
     * 根据sql语句批量插入或更新对象
     * @param sql sql语句
     * @param parameterMap 参数对象
     * @author lichunping    
     * @since 1.0.0
     */
    public int batchUpdateOrDeleteBySql(String sql, Map<String, Object> parameterMap);

    /**
     * 刷新数据
     * 
     * @since 1.0.0
     */
    public void flush();

    /**
     * 将对象从一级缓存中去除
     * 
     * @since 1.0.0
     */
    public void evict(Object obj);

    /**
     * 分页查询
     * 
     * @param page
     * @param criteria
     * @return
     */
    public Page<T> findByPage(Page<T> page, Criteria criteria);
}
