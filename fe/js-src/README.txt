
 Jiae前端环境说明
=======================================
 Description: 本文件是前端开发环境的说明文件
 Author: jarryli
 Mail: jarryli@gmail.com
 Date: 2012-5-5
-------------------------
 List:
 1. 目录说明
 2. 文件命名与结构说明
-------------------------

*  目录与根目录文件说明： 
=======================================

js-src
                 ## 辅助工具
 + build           # 放置JavaScript合并压缩的执行脚本，主要指ant 
   - tmp           # 合并压缩时用到的临时目录

                 ## 项目通用功能
 + com             # 存放项目公共的JavaScript静态类和静态方法，在lib的基础上提供给整个项目的公共服务，com新增与修改时由修改人说明理由。

 + lib             # 存放公共基础库。如JQuery, Prototype, Tangram等。本项目采用jquery-1.7.2.js, 其他js仅用作参考
 + ui              # 项目用到的UI组件库，如cover、menu、button、popup、select、table等
   - fx            # JavaScript效果库，如滑动、淡如淡出、矢量图形等。

 + template        # 所有业务功能与组件的静态HTML模板，通过Ajax异步加载，如果把模板直接放在页面上则
 + connector       # 开放平台连接器，负责处理与外部网络连接的处理，比如SNS账号登陆等

                 ## 项目特别的工具
 + collect         # 创意采集工具
 + tool            # 辅助开发的一些工具，如Logger等

                 ## 配置文件
 + conf            # 全局的配置文件

                 ## 项目的业务功能模块
 + activity        # 用户活动、Feed流以及通知等
 + board           # 创意白板模块，包括创建、编辑、删除等
 + comment         # 评论模块，含浏览以及详情页面的评论等
 + category        # 发现模块，最新最有趣的pin展示页
 + index           # 首页模块，按关注以及推荐的pin展示页
 + pin             # pin(创意)模块，供首页、发现以及详情等各种页面调用
 + popular         # 热门模块，最流行的pin浏览页
 + template        # 前端模板文件，有时模板可以ajax加载
 + user            # 用户模块，含个人设置以及profile页面
 + message         # 私信，用于用户之间的沟通。

## 其他模块根据情况增删


*  根目录文件说明： 
    - moduleName.js    # 对应模块添加一个js，把该模块下全部js包含进来，
                       # 上线时通过合并压缩工具合并到Web Server运行目录。如(ROOT/webapp/js/)
                       # 为了减少js请求数，根据需要再把同类的js引入合并到一起
                       # 如core.js包含com以及lib等



 *  文件命名与结构说明： 
=======================================

* JS开发分层说明
    # 业务模块分层
        每一个业务模块, 分为action, service, dao三层
        Action：  主要负责输入输出与事件注册与控制，一般一个即可。均为静态工厂对象。
        Service： 负责该模块业务功能的处理，一般有多个，按功能命名。均为prototype原型对象。
        Dao：     负责获取模板以及Ajax请求数据等，一个或多个。均为静态工厂对象。

        有访问入口的设立单独的Action，无独立入口的一般仅有Service，Action与Dao可以与主Action共用

    # 公共与核心模块
        如：core,com,lib等，不按业务模块分层，一般都是静态封装对象

    # 组件类
        UI组件fx效果器可不按业务模块分层，但都需要在ui或fx的命名空间下
        示例：
        // ui作为一个包，下面分各种组件
        ui.Select.js
        ui.Menu.js


* 文件列表示例
js-src/
    + moduleName[folder]              # 业务模块文件夹
        // 标准情况
        - ModuleName.js               # 该模块声明静态类，声明该模块下所有的对象
        - ModuleName.Action.js        # 该模块Action静态类
        - ModuleName.Base.js          # 该模块的业务基本功能动态对象
        - ModuleName.ServiceName1.js  # 该模块的子独立业务功能动态对象，用于独立的业务功能但又不失子模块的情况，共用Action与Dao
        - ModuleNameServiceName1.Base.js  # 该模块子功能的基本动态对象，用于相对较独立但是又不是子模块的情况，一般需要配置单独的Action或者Dao
        - ModuleName.ServiceName2.js  # 该模块的业务功能动态对象
        - ModuleName.Dao.js           # 该模块调用模板数据以及Ajax请求静态类
        // 公共与配置项
        - ModuleName.Com.js           # 该模块公共功能集合的静态类
        - ModuleName.Config.js        # 该模块功能配置项，JSON对象

        // 如果该模块下有独立的子功能，可以为独立的子功能建立单独的Action、Servic或Dao
        // Action、Dao有时可以与模块共用而不用单独建立
        - ModuleNameFunctionName.Action.js        # 该子功能Action静态类[可选]
        - ModuleNameFunctionName.ServiceName1.js  # 该子功能的业务功能动态对象
        - ModuleNameFunctionName.ServiceName2.js  # 该子功能的业务功能动态对象
        - ModuleNameFunctionName.Dao.js           # 该子功能调用模板数据以及Ajax请求静态类[可选]
        
        // 如果遇到有子模块(业务功能较多但还不适合提取出来作为单独模块)
        // 需要为子模块单独建立文件夹
        + subModuleName[folder] # 子模块或者功能模块的名称
            - ModuleNameSubModuleName.js               # 该子模块的声明静态类
            - ModuleNameSubModuleName.Action.js        # 该子模块Action静态类
            - ModuleNameSubModuleName.ServiceName1.js  # 该子模块的业务功能动态对象
            - ModuleNameSubModuleName.ServiceName2.js  # 该子模块的业务功能动态对象
            - ModuleNameSubModuleName.Dao.js           # 该子模块调用模板数据以及Ajax请求静态类
            // 子模块公共与配置项
            - ModuleNameSubModuleName.Com.js           # 该模块公共功能集合的静态类
            - ModuleNameSubModuleName.Config.js        # 该模块功能配置项，JSON对象



* 规范说明，其他编码规范参考百度JavaScript编码规范

1. 文件夹命名统一采用驼峰Camel-Case形式命名:
   如: pin, board, message

   
2. 文件命名和里面声明的类相同:
   如: Pin.js, Pin.Config.js, Pin.Com.js

   
3. 每个模块都有自己的命名空间，以模块名命名，防止变量、函数冲突;
   如：Pin = {}; Board = {};
 
 
4. 类名和命名空间统一采用帕斯卡命名法(首字母大写)：
   如Pin.js: 
   Pin = {};
   Pin.Config = {};
   PinDetail = {};
   Pin.Base = function(options) { 
       this.varName = 'xxx';
        // do something
   };

   
5. 一个模块的输入出入接口放入Action静态类中，只暴露需要对外的方法
   如Pin.Action.js:
   Pin.Action = (function() {

       // private method
       var _getPinId = function() {
            // do something
       }

       // public method
       var getPinId = function() {
            // do something
       }

       ......
       
       // open properties and method

       return {
           getPinId : getPinId,
           ......
       }
   })();

6. 一个模块的业务功能动态对象，Prototype对象

   如Pin.Base.js:
    Pin.Base = function (options){

        // public properties and method

        this.id = options.id;
        this.container = options.contariner;

    };

    Pin.Base.prototype = {

        // public
        layout : function(params){

            // do something

        },

       // private
        _layout : function(params){

            // do something

        },

    }

    // 原型继承 inheriting
    Com.inherits(PinGrid.Base, Pin.Base);
    
    // 对象运行时继承，推荐此方法
     var  pinBase = Pin.Action.getPinBase();
     var userFollowersBase;
     jQuery.extend(userFollowersBase, pinBase);

  
7. 一个模块的公共配置统一放入Config JSON对象中(默认标题名字，默认提示文字, 错误链接图片等等)
   如Pin.Config.js：
   Pin.Config = {
       selectorCss: '.pin' 
   };

   
8. 一个模块的程序中所需要载入的数据、模版、ajax请求等数据，统一放入Dao类中
   如Board.Dao.js: 
   Board.Dao = (function() {
       ......
       var create = function( params, callBackFun ) {
           $.ajax({
              type : 'POST',
               url :  '/board/create',
               data : params,
               dataType : 'json',
               success : function(data, status) {
                callBackFun.call(this, data);
               },
               error: function (data, status, e) {
                 alert(e);
              }
           });
       }
       ......

       return {
           create : create,
           ...... 
       }       
   })();

   
9. 一个模块的公共属性、方法集合统一放入Com静态类中
   如Pin.com.js：
   Pin.Com = {
       .......
       removeChild : function( element ){
       
       },
       ......       
   };

   
10. Action中的方法，类中供别人使用的方法参数注释要清楚详细，文档注释严格遵照编码规范


11. 公共文件代码比如Dao类、Com类等重要修改的地方要注明修改日期及其人员

12. JSON传递规范详见JSON规范手册
