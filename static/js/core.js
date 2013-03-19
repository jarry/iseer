/**
 * Copyright 2012 Jiae.com All rights reserved.
 * 
 * file: core.js
 * path: js-src/
 * description: 核心库以及公共方法合并文件
 * author: jarryli@gmail.com
 * date: 2012-4-5
 */

var DevConfig = {

    // lichunping@development server
    STATIC_IP   : 'http://127.0.0.1:8081/'


};

///////////////////

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
                'lib/jquery-1.7.2.js'
             ];
       write(SCRIPT_PATH, SCRIPT_LIST);

 
       // for com
       SCRIPT_PATH = STATIC_ADDR + 'com/';
       SCRIPT_LIST = [
                'jquery.ajaxupload.js',
                'jquery.fn.expand.js',
                'jquery.url.js',
                'jquery.lazyload.js',

                'AjaxUpload.js',
                'Com.js',
                'Validator.js',
                'Template.js',
                'Scroll.js',
                'Log.js'
             ];
       write(SCRIPT_PATH, SCRIPT_LIST);

})();
