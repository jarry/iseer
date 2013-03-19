/**
 * Copyright 2012 Jiae Inc. All rights reserved.
 * 
 * @file:   Index.js
 * @path:   js-src/index/
 * @desc:   Index对象与版本声明
 * @author: jarryli@gmail.com
 * @date:   2012-04-11
 */
 
///import js-src/lib/
///import js-src/com/

var Index = Index || { version : "1.0" };

//Index公共配置集合，键值对象
Index.Config = Index.Config || {};

//Index静态对象，对外接口，对内流程控制与调用以及事件管理等
Index.Action = Index.Action || {};

//Index Prototype原型对象，处理本模块的业务逻辑
Index.Base = Index.Base || {};

//Index静态对象，负责Ajax请求处理以及调用其他模块Dao等
Index.Dao = Index.Dao || {};

