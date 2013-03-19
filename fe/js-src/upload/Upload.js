/**
 * Copyright 2012 Jiae Inc. All rights reserved.
 * 
 * @file:   Upload.js
 * @path:   js-src/upload/
 * @desc:   Upload对象与版本声明
 * @author: jarryli@gmail.com
 * @date:   2012-04-11
 */
 
///import js-src/lib/
///import js-src/com/

var Upload = Upload || { version : "1.0" };

//Upload公共配置集合，键值对象
Upload.Config = Upload.Config || {};

//Upload静态对象，对外接口，对内流程控制与调用以及事件管理等
Upload.Action = Upload.Action || {};

//Upload Prototype原型对象，处理本模块的业务逻辑
Upload.Base = Upload.Base || {};

//Upload静态对象，负责Ajax请求处理以及调用其他模块Dao等
Upload.Dao = Upload.Dao || {};

