/**
 * Copyright 2012 Jiae Inc. All rights reserved.
 * 
 * @file:   Upload.Base.js
 * @path:   js-src/upload/
 * @desc:   Upload主要Prototype原型对象，处理本模块基本的业务逻辑
 * @author: jarryli@gmail.com
 * @date:   2012-05-02
 */
 
///import js-src/lib/
///import js-src/com/

Upload.Base = function () {
    this.dao = Upload.Dao;
    this.tempUrl = Config.TEMP_URL;
    this.loadingImg = Config.IMG_URL + '/' + 'loading.gif';
};

Upload.Base.prototype = {

    resizeImage : function(width, height, flag) {
        if (!width || !height) {
            return;
        }
        if (!flag) {
            flag = 100;
        }
        size = {};
        var proportion = 0;
        if (width < height) {
            size['width'] =  flag;
            proportion = (width / flag);
            height = height > flag ? (height / proportion) : height;
            size['height'] = height;
        } else {
            size['height'] = flag;
            proportion = (height / flag);
            width = width > flag ? (width / proportion) : width;
            size['width'] = width;
        }
        return size;
    }
};
