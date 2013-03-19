/**
 * Copyright 2012 Jiae Inc. All rights reserved.
 * 
 * @file:   Base.Action.js
 * @path:   js-src/base/
 * @desc:   Base静态对象，对外接口，对内流程控制与调用以及事件管理等
 * @author: jarryli@gmail.com
 * @date:   2012-05-02
 */
 
///import js-src/lib/
///import js-src/com/

Base.Action = (function() {

    var initialized = false;

    /**
     * 每个Action都设置一个初始化方法
     * 用于初始化base 原型对象，默认不重复初始化
     * @param {Boolean} force 是否强制初始化
     *
     */
    var init = function(force) {
        if (force || (!force && !initialized)) {
            initialized = true;
        }
    };
        
    return {
    
    };
})();


