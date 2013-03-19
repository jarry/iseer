/**
 * Copyright 2012 Jiae Inc. All rights reserved.
 * 
 * @file:   Upload.Dao.js
 * @path:   js-src/upload/
 * @desc:   负责Ajax请求处理以及调用其他模块Dao的静态对象
 * @author: jarryli@gmail.com
 * @date:   2012-05-02
 */
 
///import js-src/lib/
///import js-src/com/

Upload.Dao = (function() {

    var uploadImage = function(buttonId, fileName, responseType, 
                onSubmit, callBack) {
        new AjaxUpload(buttonId, {
            // multiple: true,
            action: '/iseer/upload?d=' + new Date().getTime(),
            // action: '/iseer/upload',
            name: fileName || 'uploadFile',
            responseType: responseType || 'json',
            onSubmit : function(file, ext) {
                if (ext && /^(jpg|png|jpeg|gif|bmp)$/.test(ext)){
                    if ('function' == typeof onSubmit) {
                        onSubmit.call(this, file, ext); 
                    }
                } else {
                    alert('图片格式不正确。');
                    return false;
                }
            },
            onComplete: function(file, response){
                if ('function' == typeof callBack) {
                    callBack.call(this, response, file);
                }
            }
        });

    };

    var downloadImage = function(data, callBack) {
        $.ajax({
          type: 'POST',
          url: '/iseer/download?d=' + new Date().getTime(),
          data: data,
          dataType: 'json',
          success : function(data, status) {
                callBack.call(this, data);
          },
          error: function (data, status, e) {
              // alert(e);
          }
        });
    };

    var upload = function(buttonId, fileName, responseType, 
                onSubmit, callBack) {
        new AjaxUpload(buttonId, {
            // multiple: true,
            action: '/iseer/upload',
            name: fileName || 'uploadFile',
            responseType: responseType || 'json',
            onSubmit : function(file, ext) {
                if ('function' == typeof onSubmit) {
                    onSubmit.call(this, file, ext); 
                }
            },
            onComplete: function(file, response){
                if ('function' == typeof callBack) {
                    callBack.call(this, response, file);
                }
            }
        });

    };

    return {
        upload : upload,
        uploadImage : uploadImage,
        downloadImage : downloadImage

    }

})();



