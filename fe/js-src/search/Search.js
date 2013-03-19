/**
 * Copyright 2012 Jiae Inc. All rights reserved.
 * 
 * @file:   Search.js
 * @path:   js-src/search/
 * @desc:   Search对象与版本声明
 * @author: jarryli@gmail.com
 * @date:   2012-04-11
 */
 
///import js-src/lib/
///import js-src/com/

var Search = Search || { version : "1.0" };

//Search公共配置集合，键值对象
Search.Config = Search.Config || {};

//Search静态对象，对外接口，对内流程控制与调用以及事件管理等
Search.Action = Search.Action || {};

//Search Prototype原型对象，处理本模块的业务逻辑
Search.Base = Search.Base || {};

//Search静态对象，负责Ajax请求处理以及调用其他模块Dao等
Search.Dao = Search.Dao || {};

