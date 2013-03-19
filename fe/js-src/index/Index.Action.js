/**
 * Copyright 2012 Jiae Inc. All rights reserved.
 * 
 * @file:   Index.Action.js
 * @path:   js-src/index/
 * @desc:   Index静态对象，对外接口，对内流程控制与调用以及事件管理等
 * @author: jarryli@gmail.com
 * @date:   2012-05-02
 */
 
///import js-src/lib/
///import js-src/com/

Index.Action = (function() {
    
    var indexBase = null;
    var initialized = false;
    /**
     * 每个Action都设置一个初始化方法
     * 用于初始化base 原型对象，默认不重复初始化
     * @param {Boolean} force 是否强制初始化
     *
     */
    var init = function(force) {
        if (force || (!force && !initialized)) {
            indexBase = new Index.Base();
            initialized = true;
        }
    };

    var getIndexBase = function() {
        if (!indexBase) {
            indexBase = new Index.Base();
        }
        return indexBase;
    };

    var getBase = function() {
        return getIndexBase();
    };

    var run = function() {
        var base = getBase();
        init();
        base.bindTabLinkEvent();
        base.uploadImage($('.index-form')[0]);
    };

    return {
        init : init,
        run : run
    };


})();



