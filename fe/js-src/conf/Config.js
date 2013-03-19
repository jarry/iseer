/**
 * Copyright 2012 Jiae Inc. All rights reserved.
 * 
 * @file:    config.js
 * @path:    js/
 * @desc:    iseer全局配置文件
 * @author:  jarryli@gmail.com
 * @date:    2012-04-11
 */
 
///import js-src/
var Config = Config || {};
// window.DEBUG_MODE = true;
window.DEBUG_MODE = false;
Config['WEB_HOST'] = 'http://127.0.0.1:8080';
Config['STATIC_HOST'] = 'http://127.0.0.1:8081';
Config['IMAGES_URL'] = Config.STATIC_HOST + '/images';
Config['TEMP_URL'] = Config.STATIC_HOST + '/temp';
Config['INDEX_URL'] = Config.STATIC_HOST + '/index';
Config['IMG_URL'] = Config.STATIC_HOST + '/img';
Config['CSS_URL'] = Config.STATIC_HOST + '/css';
Config['JS_URL'] = Config.STATIC_HOST + '/js';

