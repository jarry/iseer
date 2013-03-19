/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.cons;

/**
 * ClassName: Constants
 * Description: TODO Add function description
 *
 * @author   jarryli@gmail.com
 * @version  
 * @since    TODO // 需要重新定义项目常量配置
 * @Date     2011-10-7 下午09:43:12
 *
 * @see      
 */

public class Constants {
    /**
     * 列表页默认每页数据量
     */
    public static int PAGE_SIZE = 15;

    /**
     * 工程使用的变量文件名
     */
    public static String PROPERTIES_FILE_NAME = "variables.properties";

    /**
     * 发送邮件中附件的最大大小，10M
     */
    public static long MAX_MAILFILE_SIZE = 2000000;

    /**
     * 发送报警邮件中标题的最大长度，255
     */
    public static int ALARM_MAIL_TITLE_LENGTH = 255;

    /**
     * 发送报警邮件中邮件标题信息
     */
    public static final String ALARM_MAIL_TITLE = "fatal message mail ";

    /**
     * 异常参数返回的错误信息
     */
    public static final String GLOBAL_PARAMETER_ERROR = "您没有权限访问该页";

    /**
     * 实体的五角星标志位
     */
    public static int STAR_FLAG_CHECKED = 1;
    public static int STAR_FLAG_NOTCHECKED = 0;

    /**
     * 读取、写入文件的缓冲区大小
     */
    public static final int FILE_BUFFER_SIZE = 4 * 1024;

    /**
     * 用户操作的统一namespace头
     */
//    public static final String CUSTOMER_NAMESPACE_HEAD = "/client/";

    /**
     * 用户操作请求中包含的用户id参数名，用来在拦截器中进行校验防止session冲突
     */
    public static final String CUSTOMER_REQUEST_USERID4CHECK = "userId4Check";

    /**
     * 出错信息类型
     */
    public static String GLOBAL_ERROR = "global";
    public static String FIELD_ERROR = "field";


    public static Integer WITH_ME_FLAG_YES = 1;
    public static Integer WITH_ME_FLAG_NO = 0;

    /**
     * 删除提示信息中超过5个name用省略号
     */
    public static final Integer DEL_MAX_NAME = 5;
    public static final String DEL_TXT_MORE_NAME = "...";

    public static final String[] BLOCK_LIST = { "'", "\"", "\\", " " };

    /**
     * 定义打包用到的action地址。
     */
    public static final String TUNNEL_ACTION_URL = "/tunnel.action";

}
