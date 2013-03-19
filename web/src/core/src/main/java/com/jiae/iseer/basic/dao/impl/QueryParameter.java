/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.dao.impl;

import java.util.List;
import com.jiae.iseer.basic.utils.StringUtils;

/**
 * ClassName: QueryParameter
 * Description: 封装分页和排序查询参数
 *
 * @author   jarryli@gmail.com
 * @version  1.0.0
 * @since    1.0.0
 * @Date     2011-10-8 下午02:31:32
 *
 * @see      
 */

public class QueryParameter {

    public static final String ASC = "asc";
    public static final String DESC = "desc";
    public static final String ORDER_TOKEN = ",";

    protected int pageNo = 1;
    protected int pageSize = -1;
    protected String orderBy = null;
    protected String order = DESC;

    protected boolean autoCount = false;

    /**
     * 获得每页的记录数量,无默认值.
     */
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 是否已设置每页的记录数量.
     */
    public boolean isPageSizeSetted() {
        return pageSize > -1;
    }

    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始.
     */
    public int getFirst() {
        if (pageNo < 1 || pageSize < 1)
            return -1;
        else
            return ((pageNo - 1) * pageSize);
    }

    /**
     * 是否已设置第一条记录记录在总结果集中的位置.
     */
    public boolean isFirstSetted() {
        return (pageNo > 0 && pageSize > 0);
    }

    /**
     * 获得排序字段,无默认值.
     */
    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * 是否已设置排序字段.
     */
    public boolean isOrderBySetted() {
        return StringUtils.isNotBlank(orderBy);
    }

    /**
     * 获得排序方向,默认为asc.
     */
    public String getOrder() {
        return order;
    }

    /**
     * 设置排序方式向.支持多了用逗号分隔
     * 
     * @param order
     *            可选值为desc或asc.
     */
    public void setOrder(String order) {
        List<String> orderList = StringUtils.parseStringToStringList(order, ORDER_TOKEN);
        if (null == orderList) {
            return;
        }
        for (String orderToken : orderList) {
            if (!(ASC.equalsIgnoreCase(orderToken) || DESC.equalsIgnoreCase(orderToken))) {
                throw new IllegalArgumentException("order should be 'desc' or 'asc'");
            }
        }
        this.order = order.toLowerCase();
    }

    /**
     * 是否自动获取总页数,默认为false. 注意本属性仅于query by Criteria时有效,query by HQL时本属性无效.
     */
    public boolean isAutoCount() {
        return autoCount;
    }

    public void setAutoCount(boolean autoCount) {
        this.autoCount = autoCount;
    }

    /** 为了支持多列排序，且每列可以设置是否需要按中文排序，设置下述三个list，这三个list分别切分三个字符串获得，顺序要求匹配 **/

    public List<String> getOrderByList() {
        return StringUtils.parseStringToStringList(this.orderBy, ORDER_TOKEN);
    }

    public List<String> getOrderList() {
        return StringUtils.parseStringToStringList(this.order, ORDER_TOKEN);
    }
}
