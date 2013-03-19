/**
 * Copyright 2012 Jiae.com All rights reserved.
 * 
 * file: iseer.js
 * path: js-src/
 * description: 前端交互与业务功能合并文件，包含配置、UI、功能以及个模块代码
 * author: jarryli@gmail.com
 * date: 2012-4-5
 */


/* load pin script list */ 
(function() {
       function write(path, scriptList) {
           for (var i = 0; i < scriptList.length; i++) {
            document.write('<script type="text/javascript" src="' + path + scriptList[i] + ' "></script>');
           }
       }

       var STATIC_ADDR = DevConfig.STATIC_IP + 'iseer-dev/js-src/';

       var SCRIPT_PATH = '';
       var SCRIPT_LIST = '';
       // scripts list
       SCRIPT_PATH = STATIC_ADDR + '';
       SCRIPT_LIST = [
                'conf/Config.js',
                'base/Base.js',
                'base/Base.Action.js'
             ];
       write(SCRIPT_PATH, SCRIPT_LIST);

       // for upload
       SCRIPT_PATH = STATIC_ADDR + 'upload/';
       SCRIPT_LIST = [
             'Upload.js',
             'Upload.Config.js',
             'Upload.Action.js',
             'Upload.Base.js',
             'Upload.Dao.js'

             ];
       write(SCRIPT_PATH, SCRIPT_LIST);

       // for search
       SCRIPT_PATH = STATIC_ADDR + 'search/';
       SCRIPT_LIST = [
             'Search.js',
             'Search.Config.js',
             'Search.Action.js',
             'Search.Base.js',
             'Search.Dao.js'

             ];
       write(SCRIPT_PATH, SCRIPT_LIST);



       // for upload
       SCRIPT_PATH = STATIC_ADDR + 'index/';
       SCRIPT_LIST = [
             'Index.js',
             'Index.Config.js',
             'Index.Action.js',
             'Index.Base.js',
             'Index.Dao.js'

             ];
       write(SCRIPT_PATH, SCRIPT_LIST);

})();

/* load comment script list */ 
