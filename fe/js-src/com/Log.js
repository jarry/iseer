/**
 * Copyright 2012 Jiae Inc. All rights reserved.
 * 
 * @file:   Log.js
 * @path:   js-src/com
 * @desc:   项目的log日志静态对象
 *          函数采取全部键值对象式声明，方法名采用驼峰式命名
 * @author: jarryli@gmail.com
 * @date:   2012-07-22
 */
 
///import js-src/lib/
///import js-src/com/

var Log = Log || {};

Log = {
  
    getLogImgSrc : function() {
        return Config.IMG_HOST + '/log/none.html';
    },
 
    /**
     * 添加一条日志 
     * @param {String} href 链接
     * @param {Number} code 状态码
     * @param {Object} params 参数对象，可以传递多个参数
     */
    add : function(href, code, params) {
        var src = this.getLogImgSrc();
        var img = new Image();
        var time   = (new Date()).getTime();
        src += '?h=' + (href || '');
        src += '&t=' + time;
        src += '&l=' + window.location.href;
        src += '&c=' + (code || '');

        for (var p in params) {
            src += '&' + p + '=' + params[p];
        }
        img.src = src;

    }


};
