/**
 * Copyright 2012 Jiae Inc. All rights reserved.
 * 
 * @file:   Search.Action.js
 * @path:   js-src/search/
 * @desc:   Search静态对象，对外接口，对内流程控制与调用以及事件管理等
 * @author: jarryli@gmail.com
 * @date:   2012-05-02
 */
 
///import js-src/lib/
///import js-src/com/

Search.Action = (function() {
    
    var searchBase = null;
    var initialized = false;
    /**
     * 每个Action都设置一个初始化方法
     * 用于初始化base 原型对象，默认不重复初始化
     * @param {Boolean} force 是否强制初始化
     *
     */
    var init = function(force) {
        if (force || (!force && !initialized)) {
            searchBase = new Search.Base();
            initialized = true;
        }
    };

    var getSearchBase = function() {
        if (!searchBase) {
            searchBase = new Search.Base();
        }
        return searchBase;
    };

    var getBase = function() {
        return getSearchBase();
    };

    var run = function() {
        var base = getBase();
        init();
        base.bindTabLinkEvent();
        base.uploadImage($('.search-form')[0]);
    };

    return {
        init : init,
        run : run
    };


})();



