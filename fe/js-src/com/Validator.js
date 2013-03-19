/**
 * Copyright 2012 Jiae Inc. All rights reserved.
 * 
 * @file:   Com.js
 * @path:   js-src/com
 * @desc:   项目的公共验证函数集合，所有函数均为公开
 *          函数采取全部键值对象式声明，方法名采用驼峰式命名
 * @author: jarryli@gmail.com
 * @date:   2012-06-06
 */
 
///import js-src/lib/
///import js-src/com/

var Validator = Validator || {};

Validator = {
   
    formulae : {
       // reg : /^[a-zA-Z0-9]+$/,
       // reg = /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/;

       number :  /^\d+$/,
       english : /^[A-Za-z]+$/,
       numAndEn : /^[a-zA-Z0-9]+$/,
       numAndEnAndCn : /^[a-zA-Z0-9_\u4E00-\u9FA5]+$/,
      // email :  /^(.+)@(.+)$/,  
      // email : /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/, 
       email : /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i,
      // chinese : /[\u4E00-\u9FA5\uF900-\uFA2D]/, 
       chinese : /^[\u0391-\uFFE5]+$/,
       
      // special : /^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/
      // specailSimple : /[^\\\/\*\?\|\<\>\:]+/
       specailSimple : /[\\\/:*?\"<>|\']+/g
    },

    isEmail : function(str) {
        var reg = Validator.formulae.email;
        if ( !str || 'string' != typeof str  || $.trim(str) == '' ) {
            return false;
        }
        var minLen = 4;
        if (str.length < minLen) {
            return false;
        }
        return str.match(reg) ? true : false;
    },

    /**
     * 验证合法的name也就是昵称
     * 规则：4-25 字符，支持数字、中英文以及“_” 
     * 
     * @param {String} str 字符串
     * @return {Boolean} 是否正确
     */
    isName : function(str) {
        if ( !str || 'string' != typeof str  || $.trim(str) == '' ) {
            return false;
        }
        var len = Com.getStrLength(str);
        var maxLen = 25;
        var minLen = 4;
        if (len < minLen || len > maxLen) {
            return false;
        }

        var reg = Validator.formulae.numAndEnAndCn; 
        return reg.test(str);
    },

    /**
     * 验证合法的password
     * 规则： 6个字符以上 
     * 
     * @param {String} str 字符串
     * @return {Boolean} 是否正确
     */
    isPassWord : function(str) {
        if ( !str || 'string' != typeof str  || $.trim(str) == '' ) {
           return false;
        }
        var len = Com.getStrLength(str);
        var minLen = 6;
        if (len < minLen) {
            return false;
        }
        return true;
    },

    isBoardName : function(str) {
        if ( !str || 'string' != typeof str  || $.trim(str) == '' ) {
           return false;
        }
        var reg = Validator.formulae.specailSimple;
        return !reg.test(str);
    },

    /**
     * 验证合法的domain
     * 规则：5-15字符，仅支持英文和数字，首字母须为英文 
     * 
     * @param {String} str 字符串
     * @return {Boolean} 是否正确
     */
    isDomain : function(str) {
        if ( !str || 'string' != typeof str  || $.trim(str) == '' ) {
            return false;
        }

        var len = str.length;
        var minLen = 5;
        var maxLen = 15;

        if (len < minLen || len > maxLen) {
            return false;
        }


        var en = Validator.formulae.english;
        var numAndEn = Validator.formulae.numAndEn; 
        var firstChar = str.substr(0, 1);

        if ( !en.test(firstChar) ) {
            return false;
        }

        return numAndEn.test(str);
    } 


};

Validator.isPassword = Validator.isPassword;
