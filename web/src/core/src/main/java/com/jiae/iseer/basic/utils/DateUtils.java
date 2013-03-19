/*
 * Copyright (c) 2011, All Rights Reserved.
 */

/**
 * ClassName: DateUtils
 * Function: DateUtils的工具类
 *
 * @author   <a href="mailto:jarryli@gmail.com">lichunping</a>
 * @version  1.0.0
 * @since    1.0.0
 * @Date     2011-8-27 07:11:45
 *
 * @see      
 */

package com.jiae.iseer.basic.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {

    protected static Logger logger = LoggerFactory.getLogger(DateUtils.class);
    private static final long MS_IN_DAY = 24 * 3600 * 1000;

    /**
     * 获得给定时间的第N天零时的日期对象 例如：若给定时间为（2004-08-01 11:30:58），
     * 将获得（2004-08-02 00:00:00）的日期对象 若给定时间为（2004-08-31 11:30:58），
     * 将获得（2004-09-01 00:00:00）的日期对象
     * 
     * @param dt
     *            Date 给定的java.util.Date对象
     * @param n 
     * @return Date java.util.Date对象
     */
    public static Date getNextDay(Date dt, int n) {

        Calendar cal = new GregorianCalendar();
        cal.setTime(dt);

        return new GregorianCalendar(cal.get(Calendar.YEAR), 
                cal.get(Calendar.MONTH), 
                cal.get(Calendar.DAY_OF_MONTH)
                + n).getTime();

    }

    /**
     * 按照给定格式返回代表日期的字符串
     * 
     * @param pDate
     *            Date
     * @param format
     *            String 日期格式
     * @return String 代表日期的字符串
     */
    public static String formatDate(java.util.Date pDate, String format) {

        if (pDate == null) {
            pDate = new java.util.Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(pDate);
    }

    /**
     * 验证字符串是不是合法的日期；严格判断日期格式YYYYMMDD的正则表达式：
     * 包括闰年的判断、大月小月的判断
     * 
     * @param dateString
     *            待验证的日期字符串
     * @return 满足则返回true，不满足则返回false
     */
    public static boolean validateDateString(String dateString) {

        if (dateString == null || dateString.equals("")) {
            return false;
        }

        // 日期格式YYYYMMDD的正则表达式,世纪年为闰年，如2000
        String regDate = "^(((([02468][048])|([13579][26]))[0]{2})(02)(([0][1-9])|([1-2][0-9])))" 
        // 世纪年不为闰年如2100
               + "|(((([02468][1235679])|([13579][01345789]))[0]{2})(02)(([0][1-9])|([1][0-9])|([2][0-8])))" 
                // 非世纪年为闰年，如1996
               + "|(([0-9]{2}(([0][48])|([2468][048])|([13579][26])))(02)(([0][1-9])|([1-2][0-9])))" 
                // 非世纪年不为闰年，如1997
               + "|(([0-9]{2}(([02468][1235679])|([13579][01345789])))(02)(([0][1-9])|([1][0-9])|([2][0-8])))" 
                // 大月，有31天
               + "|(([0-9]{4})(([0]{1}(1|3|5|7|8))|10|12)(([0][1-9])|([1-2][0-9])|30|31))" 
                // 小月，只有30天
               + "|(([0-9]{4})(([0]{1}(4|6|9))|11)(([0][1-9])|([1-2][0-9])|30))$";

        return dateString.matches(regDate);
    }
    /**
     * 校验时间串
     * 
     * @param datetimeString 
     * @param format 
     * @return
     */
    public static boolean validateDatetimeString(String datetimeString, String format) {
        if (datetimeString == null || datetimeString.equals("")) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            sdf.parse(datetimeString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 获取两个日期之间的间隔日期
     * 
     * @param beginDate 
     * @param endDate 
     * @version 1.0.0
     */
    public static int getDaysBetween(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date bDate = format.parse(beginDate);
            Date eDate = format.parse(endDate);
            Calendar d1 = new GregorianCalendar();
            d1.setTime(bDate);
            Calendar d2 = new GregorianCalendar();
            d2.setTime(eDate);

            if (d1.after(d2)) {
                return 0;
            }

            int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
            int y2 = d2.get(Calendar.YEAR);
            if (d1.get(Calendar.YEAR) != y2) {
                d1 = (Calendar) d1.clone();
                do {
                    days += d1.getActualMaximum(Calendar.DAY_OF_YEAR); // 得到当年的实际天数
                    d1.add(Calendar.YEAR, 1);
                } while (d1.get(Calendar.YEAR) != y2);
            }
            return days;
        } catch (ParseException e) {
            LoggerUtils.error(logger, "while convert date in getDaysBetween!", e);
            return 0;
        }
    }

    /**
     * 获取从开始时间到结束时间的不同的天数 例如: 10月1号23点到10月2号1点是2天，
     * 经过了10月1号和10月2号。 10月1号23点到10月2号0点是1天，10月2号0点=10月1号24点
     * @param beginDate 
     * @param endDate 
     * @return
     */
    public static int getDaysAmong(String beginDate, String endDate) {
        if (beginDate == null || endDate == null || beginDate.length() != 14 || endDate.length() != 14) {
            return 0;
        }

        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");
            Date bDate = format1.parse(beginDate.substring(0, 8));
            Date eDate = format1.parse(endDate.substring(0, 8));
            if (eDate.getTime() < bDate.getTime()) {
                return 0;
            }

            long days = (eDate.getTime() - bDate.getTime()) / MS_IN_DAY;
            eDate = format2.parse(endDate);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(eDate);
            if (calendar.get(Calendar.HOUR_OF_DAY) != 0) {
                days++;
            }
            // 当天
            if (days == 0) {
                days = 1;
            }

            return (int) days;
        } catch (ParseException e) {
            LoggerUtils.error(logger, "while convert date in getDaysBetween!", e);
            return 0;
        }
    }

    /**
     * 获取两个日期之间的间隔日期
     * 
     * @param beginDate  
     * @param endDate 
     */
    public static int getDaysBetween(Date beginDate, Date endDate) {
        Calendar d1 = new GregorianCalendar();
        d1.setTime(beginDate);
        Calendar d2 = new GregorianCalendar();
        d2.setTime(endDate);

        if (d1.after(d2)) {
            return 0;
        }

        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            d1 = (Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR); // 得到当年的实际天数
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        return days;
    }

    /**
     * 获取两个日期之间的间隔毫秒数
     * 
     * @param beginDate 
     * @param endDate 
     * @return
     */
    public static long getMinllisBetween(Date beginDate, Date endDate) {
        Calendar d1 = new GregorianCalendar();
        d1.setTime(beginDate);
        Calendar d2 = new GregorianCalendar();
        d2.setTime(endDate);

        if (d1.after(d2)) {
            return 0;
        }

        long minllis = d2.getTimeInMillis() - d1.getTimeInMillis();
        return minllis;
    }

    /**
     * 获取解析8位时间格式的formater
     * 
     * @return
     */
    public static DateFormat get8BitTimeFormater() {
        return new SimpleDateFormat("yyyyMMdd");
    }

    /**
     * 获取解析23位格式化后时间格式的formater
     * 
     * @return
     */
    public static DateFormat getFullFormater() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    }

    /**
     * 获取解析17位格式化后时间格式的formater
     * @return
     */
    public static DateFormat getNormalFormater() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取解析14位时间格式的formater
     * 
     * @return
     */
    public static DateFormat get14BitTimeFormater() {
        return new SimpleDateFormat("yyyyMMddHHmmss");
    }

    /**
     * 判断是否是一天的开始。
     * 
     * @param d 
     * @return
     */
    public static boolean isBeginOfDay(Date d) {
        Calendar d1 = new GregorianCalendar();
        d1.setTime(d);
        return d1.get(Calendar.HOUR_OF_DAY) == 0;
    }

    /**
     * 判断是否是一天的结束。系统中只精确到小时，因此23:00后面的时间都被当成一天的结束。
     * 
     * @param d 
     * @return
     */
    public static boolean isEndOfDay(Date d) {
        Calendar d1 = new GregorianCalendar();
        d1.setTime(d);
        return d1.get(Calendar.HOUR_OF_DAY) == 23 && d1.get(Calendar.MINUTE) > 0;
    }

    /**
     * 将14位的时间串表示成yyyy-MM-dd HH:mm的形式
     * 
     * @param time 
     * @return
     */
    public static String formatTime14To12String(String time) {
        StringBuffer sb = new StringBuffer();
        sb.append(time.substring(0, 4) + "-" + time.substring(4, 6));
        sb.append("-" + time.substring(6, 8) + " ");
        sb.append(time.substring(8, 10) + ":" + time.substring(10, 12));
        return sb.toString();
    }

    /**
     * 将14位的时间串表示成yyyy-MM-dd的形式
     * 
     * @param time 
     * @return
     */
    public static String formatTime14To8String(String time) {
        StringBuffer sb = new StringBuffer();
        sb.append(time.substring(0, 4) + "-" + time.substring(4, 6));
        sb.append("-" + time.substring(6, 8));
        return sb.toString();
    }
    
    /**
     * 获取两天间所有日期字符串,且从yyyyMMdd转为yyyy-MM-dd
     * 
     * @param beginDate
     *            yyyyMMdd
     * @param endDate
     *            yyyyMMdd
     * @return yyyy-MM-dd
     */
    public static List<String> getAllDate(String beginDate, String endDate) {
        List<String> allDate = new ArrayList<String>();

        SimpleDateFormat inFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = inFormat.parse(beginDate);
            Date date2 = inFormat.parse(endDate);

            int days = getDaysBetween(date1, date2) + 1;
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);

            for (int i = 0; i < days; i++) {
                allDate.add(outFormat.format(cal.getTime()));
                cal.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (ParseException e) {
            LoggerUtils.error(logger, "while convert date in getAllDate!", e);
        }
        return allDate;
    }
    
    /**
     * 获取给定日期前后若干天的日期
     * 
     * @param strDate yyyyMMdd
     * @param delta 
     * @return
     */
    public static String addDate(String strDate, int delta) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String result = null;
        Date date = null;
        try {
            date = format.parse(strDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_YEAR, delta);
            result = format.format(cal.getTime());
        } catch (ParseException e) {
            LoggerUtils.error(logger, "while add date!", e);
        }
        
        return result;
    }
    
    /**
     * 获取当前时间，格式是yyyyMMddHHmm
     * 
     * @return
     */
    public static String getCurTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        
        Calendar cal = Calendar.getInstance();
        String result = format.format(cal.getTime());
        
        return result;
    }
    /**
     * 根据时间获取间隔天数的8位时间表示
     * @param date 
     *        时间
     * @param delta
     *        间隔时间，以天为单位
     * @return
     */
    public static String get8BitTime(Date date, int deltaDay) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, deltaDay);
        return format.format(cal.getTime()); 
    }
    /**
     * 获取上周的第一天。
     * 
     * @return
     */
    public static String getFirstDayOfLastWeek() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(new Date());
        cal.add(Calendar.WEEK_OF_YEAR, -1);                        
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return format.format(cal.getTime());
    }

    /**
     * 获取上周的最后一天。
     * 
     * @return
     */
    public static String getLastDayOfLastWeek() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(new Date());
        cal.add(Calendar.WEEK_OF_YEAR, -1);    
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return format.format(cal.getTime());
    }

    /**
     * 获取上个月的第一天。
     * 
     * @return
     */
    public static String getFirstDayOfLastMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return format.format(cal.getTime());
    }

    /**
     * 获取上个月的最后一天。
     * 
     * @return
     */
    public static String getLastDayOfLastMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return format.format(cal.getTime());
    }

    /**
     * 将8位yyyymmdd的时间格式转换成yyyy-mm-dd的格式，主要用于定制报告发送。
     * @param bit8Time 
     *        8位时间
     * @return
     */
    public static String change8BitTimeToStandard(String bit8Time){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try{
            date = format.parse(bit8Time);
        } catch(Exception e){
            //ignore
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}
