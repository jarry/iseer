/**
 * Copyright 2012 Jiae Inc. All rights reserved.
 * 
 * @file:   Upload.Config.js
 * @path:   js-src/upload/
 * @desc:   Upload公共配置集合，键值对象
 *          一级键名大写，之间名采用驼峰式
 * @author: jarryli@gmail.com
 * @date:   2012-04-11
 */
 
///import js-src/lib/
///import js-src/com/

Upload.Config = {

    Upload : {
        className : '.Upload-card'
    },
    // 模板的id
    TPL_Upload_ID : 'TPL_Upload',
    TPL_WeiboFriend_ID : 'TPL_WeiboFriend',

    MESSAGE : {
        upload : {
            failed : '上传出现异常,请检查文件格式或网络设置'
        },
        name : {
            format : '4-25 字符，支持数字、中英文以及“_”',
            effective : '<strong class="correct"></strong>',
            duplicate : '该昵称已经被占用'
       },
       domain : {
            format : '5-15字符，仅支持英文和数字，首字母须为英文<br>永久地址，设置后将不能更改',
            formatShort : '5-15字符，仅支持英文数字，首字母须为英文',
            effective : '<strong class="correct"></strong>',
            duplicate : '该域名已经被占用'
       },
       email : {
            format : '邮箱格式不正确',
            duplicate : '该邮箱已经被占用'
       },
       password : {
            format : '密码不能少于6个字符'    
       },
       rePassword : {
            different : '两次输入不符'
       }
       
    }

};
