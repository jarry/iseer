///*
// * Copyright (c) 2011, All Rights Reserved.
// */
//
package com.jiae.iseer.basic.utils;
//
//import java.io.File;
//import java.io.StringWriter;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Map;
//import java.util.Properties;
//import java.util.Map.Entry;
//
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
//import org.apache.velocity.Template;
//import org.apache.velocity.VelocityContext;
//import org.apache.velocity.app.Velocity;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.mail.MailException;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.mail.javamail.MimeMessageHelper;
//
///**
// * ClassName: MailUtils
// * Description: TODO Add function description
// *
// * @author   jarryli@gmail.com
// * @version  
// * @since    TODO
// * @Date     2011-11-8 下午02:38:36
// *
// * @see      
// */
//
public class MailUtils {
//    protected static Logger logger = LoggerFactory.getLogger(MailUtils.class);
//
//    private static JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//    static {
//        mailSender.setHost(Variables.MAILHOST);
//    }
//
//    /**
//     * 发送普通邮件
//     * 
//     * @param from 
//     * @param to 
//     * @param title 
//     * @param text 
//     * @throws Exception 
//     */
//    public static void sendMail(String from, String to, String title, String text) throws Exception {
//        try {
//            long start = System.currentTimeMillis();
//            if (from == null || to == null) {
//                throw new Exception("from is null or to is null");
//            }
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "GBK");
//
//            messageHelper.setFrom(new InternetAddress(from));
//            messageHelper.setTo(new InternetAddress(to));
//            messageHelper.setSubject(title);
//            messageHelper.setText(text);
//            mimeMessage = messageHelper.getMimeMessage();
//            mailSender.send(mimeMessage);
//            long end = System.currentTimeMillis();
//            logger.info("send mail start:" + start + " end :" + end);
//        } catch (MailException e) {
//            logger.error("send mail failed", e);
//            throw new Exception("send Mail Failed", e);
//        }
//    }
//    
//    /**
//     * 发送html邮件,带附件
//     * 
//     * @param from 
//     * @param to 
//     * @param title 
//     * @param text 
//     * @param filesMap 
//     * @throws Exception 
//     */
//    public static void sendHtmlAttachMail(String from, String[] to, String title, String text,
//            Map<String, String> filesMap) throws Exception {
//        try {
//            long start = System.currentTimeMillis();
//            if (from == null || to == null) {
//                throw new Exception("from is null or to is null");
//            }
//
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "GBK");
//
//            InternetAddress[] toArray = new InternetAddress[to.length];
//            for (int i = 0; i < to.length; i++) {
//                toArray[i] = new InternetAddress(to[i]);
//            }
//
//            messageHelper.setFrom(new InternetAddress(from));
//            messageHelper.setTo(toArray);
//            messageHelper.setSubject(title);
//            messageHelper.setText(text, true);
//            // 加入附件列表
//            if (filesMap != null && filesMap.size() > 0) {
//                for (Entry<String, String> entry : filesMap.entrySet()) {
//                    File theFile = new File(entry.getValue());
//                    if (theFile != null
//                            && theFile.length() < Constants.MAX_MAILFILE_SIZE) {
//                        messageHelper.addAttachment(entry.getKey(), theFile);
//                    }
//                }
//            }
//            mimeMessage = messageHelper.getMimeMessage();
//            mailSender.send(mimeMessage);
//            long end = System.currentTimeMillis();
//            logger.info("send mail start:" + start + " end :" + end);
//        } catch (MailException e) {
//            logger.error("send mail failed", e);
//            throw new Exception("send Mail Failed", e);
//        }
//    }
//    
//    /**
//     * 发送html邮件
//     * 
//     * @param from 
//     * @param to 
//     * @param title 
//     * @param text 
//     * @throws Exception 
//     */
//    public static void sendHtmlMail(String from, String[] to, String title, String text) throws Exception {
//        try {
//            long start = System.currentTimeMillis();
//            if (from == null || to == null) {
//                throw new Exception("from is null or to is null");
//            }
//
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "GBK");
//
//            InternetAddress[] toArray = new InternetAddress[to.length];
//            for (int i = 0; i < to.length; i++) {
//                toArray[i] = new InternetAddress(to[i]);
//            }
//
//            messageHelper.setFrom(new InternetAddress(from));
//            messageHelper.setTo(toArray);
//            messageHelper.setSubject(title);
//            messageHelper.setText(text, true);
//            mimeMessage = messageHelper.getMimeMessage();
//            mailSender.send(mimeMessage);
//            long end = System.currentTimeMillis();
//            logger.info("send mail start:" + start + " end :" + end);
//        } catch (MailException e) {
//            logger.error("send mail failed", e);
//            throw new Exception("send Mail Failed", e);
//        }
//    }
//    
//    /**
//     * 以暗送方式发送html邮件
//     * 
//     * @param from 
//     * @param tos 
//     * @param title 
//     * @param text 
//     * @throws Exception 
//     */
//    public static void sendHtmlMailInBcc(String from, String[] tos, String title, String text) throws Exception {
//        try {
//            long start = System.currentTimeMillis();
//            if (from == null || tos == null) {
//                throw new Exception("from is null or to is null");
//            }
//
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "GBK");
//
//            messageHelper.setFrom(new InternetAddress(from));
//
//            if (tos == null || tos.length == 0) {
//                return;
//            } else {
//                for (int i = 0; i < tos.length; i++) {
//                    messageHelper.addBcc(new InternetAddress(tos[i]));
//                }
//
//            }
//
//            messageHelper.setSubject(title);
//            messageHelper.setText(text, true);
//            mimeMessage = messageHelper.getMimeMessage();
//            mailSender.send(mimeMessage);
//            long end = System.currentTimeMillis();
//            logger.info("send mail start:" + start + " end :" + end);
//
//        } catch (MailException e) {
//            logger.error("send mail failed", e);
//            throw new Exception("send Mail Failed", e);
//        }
//    }
//
//    
//    /**
//     *  以暗送方式发送普通邮件
//     *  
//     * @param from 
//     * @param tos 
//     * @param title 
//     * @param text 
//     * @throws Exception 
//     */
//    public static void sendMailInBcc(String from, String[] tos, String title, String text) throws Exception {
//        try {
//            long start = System.currentTimeMillis();
//            if (from == null || tos == null) {
//                throw new Exception("from is null or to is null");
//            }
//
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "GBK");
//
//            messageHelper.setFrom(new InternetAddress(from));
//
//            if (tos == null || tos.length == 0) {
//                return;
//            } else {
//                for (int i = 0; i < tos.length; i++) {
//                    messageHelper.addBcc(new InternetAddress(tos[i]));
//                }
//
//            }
//
//            messageHelper.setSubject(title);
//            messageHelper.setText(text);
//            mimeMessage = messageHelper.getMimeMessage();
//            mailSender.send(mimeMessage);
//            long end = System.currentTimeMillis();
//            logger.info("send mail start:" + start + " end :" + end);
//
//        } catch (MailException e) {
//            logger.error("send mail failed", e);
//            throw new Exception("send Mail Failed", e);
//        }
//
//    }
//
//
//    /**
//     * 获得velocity的上下文对象
//     * 
//     * @author lichunping
//     * @since 1.0.0
//     * @return
//     * @throws Exception 
//     */
//    public static VelocityContext getVelocityContext() throws Exception {
//        Properties p = new Properties();
//        p.setProperty(Velocity.INPUT_ENCODING, "gbk");
//        p.setProperty(Velocity.RESOURCE_LOADER, "class");
//        p.setProperty("class.resource.loader.class",
//                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
//
//        Velocity.init(p);
//        return new VelocityContext();
//    }
//
//    /**
//     * 发送注册验证邮件
//     * 
//     * @author lichunping
//     * @since 1.0.0
//     * @param user  
//     * @param token 
//     * @param deadline 
//     * @return
//     * @throws Exception 
//     */
//    public static boolean sendRegisterValidationMail(RegisterUser user, String token, Date deadline) throws Exception {
//        if (null == user) {
//            return false;
//        }
//        String content = "";
//
//        VelocityContext context = MailUtils.getVelocityContext();
//        DateFormat deadlineFormat = new SimpleDateFormat("yyyy年MM月dd日");
//
//        context.put("userName", StringUtils.escape4Html(user.getUserName()));
//        context.put("token", token);
//        context.put("deadline", deadlineFormat.format(deadline));
//        context.put("indexUrl", Variables.INDEX_URL);
//        Template template = Velocity.getTemplate(Constants_user.REGISTER_VALIDATION_MAIL_VM);
//
//        StringWriter stringWriter = new StringWriter();
//        template.merge(context, stringWriter);
//        stringWriter.flush();
//        stringWriter.close();
//        content = stringWriter.toString();
//
//        String from = Variables.SERVICE_MAIL_FROM;
//        String[] to = new String[] { user.getMail() };
//        String title = Constants_user.REGISTER_VALIDATION_MAIL_TITLE;
//
//        sendHtmlMail(from, to, title, content);
//        return true;
//    }
//    
//    /**
//     * 发送修改邮箱的验证邮件
//     * 
//     * @author lichunping
//     * @since 1.0.0
//     * @param event 邮箱修改的状态  
//     * @param token 验证信息
//     * @param deadline 截止日期 
//     */
//    public static boolean sendModifyMailValidationMail(
//            ModifyMailEvent event, String token, Date deadline) throws Exception {
//        String content = "";
//
//        VelocityContext context = MailUtils.getVelocityContext();
//        DateFormat deadlineFormat = new SimpleDateFormat("yyyy年MM月dd日");
//
//        context.put("userName", StringUtils.escape4Html(event.getUserName()));
//        context.put("token", token);
//        context.put("deadline", deadlineFormat.format(deadline));
//        context.put("indexUrl", Variables.INDEX_URL);
//        Template template = Velocity.getTemplate(Constants_user.MODIFY_MAIL_MAIL_CONTENT_VM);
//
//        StringWriter stringWriter = new StringWriter();
//        template.merge(context, stringWriter);
//        stringWriter.flush();
//        stringWriter.close();
//        content = stringWriter.toString();
//
//        String from = Variables.SERVICE_MAIL_FROM;
//        String[] to = new String[] { event.getNewMail() };
//        String title = Constants_user.MODIFY_MAIL_MAIL_TITLE;
//
//        sendHtmlMail(from, to, title, content);
//        return true;
//    }
}
