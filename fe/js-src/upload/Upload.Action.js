/**
 * Copyright 2012 Jiae Inc. All rights reserved.
 * 
 * @file:   Upload.Action.js
 * @path:   js-src/upload/
 * @desc:   Upload静态对象，对外接口，对内流程控制与调用以及事件管理等
 * @author: jarryli@gmail.com
 * @date:   2012-05-02
 */
 
///import js-src/lib/
///import js-src/com/

Upload.Action = (function() {
    
    var uploadBase = null;
    var initialized = false;
    /**
     * 每个Action都设置一个初始化方法
     * 用于初始化base 原型对象，默认不重复初始化
     * @param {Boolean} force 是否强制初始化
     *
     */
    var init = function(force) {
        if (force || (!force && !initialized)) {
            uploadBase = new Upload.Base();
            initialized = true;
        }
    };

    var getUploadBase = function() {
        if (!uploadBase) {
            uploadBase = new Upload.Base();
        }
        return uploadBase;
    };

    var getBase = function() {
        return getUploadBase();
    };

    var uploadImage = function(me) {
        if (!me) {
            return;
        }
        var _form = Com.getParentByTag(me, 'form');
        // TODO
        // getBase().uploadImage(_form);
    };


    return {
        init : init,
        getBase : getBase,
        uploadImage : uploadImage

    };


})();



