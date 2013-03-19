/**
 * Copyright 2012 Jiae Inc. All rights reserved.
 * 
 * @file:   Search.Base.js
 * @path:   js-src/search/
 * @desc:   Search主要Prototype原型对象，处理本模块基本的业务逻辑
 * @author: jarryli@gmail.com
 * @date:   2012-05-02
 */
 
///import js-src/lib/
///import js-src/com/

Search.Base = function () {
    this.dao = Search.Dao;
    this.uploadDao = Upload.Dao;
};

Search.Base.prototype = {

    uploadImage : function(_form) {
        var self = this;
        var $form = $(_form);
        var button = $form.find('.upload-button')[0];
        var $button = $(button);
        var $uploadFile = $form.find('.upload-file');
        var $inputFile = $form.find('.input-file');
        var $loading = $uploadFile.find('.loading');
        var $error = $uploadFile.find('.error');
        var fileName = $inputFile[0].name;
        var inputId = $inputFile[0].id;

        var _submit = function(file, ext) {
            $loading.show();
            $error.hide();
        };

        var _setFormValue = function(image) {
            _form.name.value = image.name;
            $error.hide();
            $loading.hide();
        };

        var _setError = function() {
            $error.text('上传出现错误。请检查图片格式或者网络连接。');
            $error.show();
        };

        var _callBack = function(data, file) {
            try {
                var state = data.state;
                var _data  = data.data;
                var image = _data.image;
                if (state && state == true) {
                    _setFormValue(image);
                    setTimeout(
                        function() {
                            $form.submit();
                        }, 10
                    );
                } else {
                    
                }
            } catch (e) {
                // console.log(e.message);
            }
        };

        this.uploadDao.uploadImage(inputId, fileName, 'json', _submit, _callBack);
    },

    bindTabLinkEvent : function($container) {
        var $container = $container || $('.search-container');
        var $uploadFile = $container.find('.upload-file');
        var $inputUrl = $container.find('.input-url');
        var $searchButton = $inputUrl.find('.search-button');
        var $tabLink = $container.find('.tab-link');
        var $urlLink = $tabLink.find('.url-link');
        var $uploadLink = $tabLink.find('.upload-link');

        var _toggleLink = function(evt) {
            var me = evt.target || evt.srcElment;
            if (me !== $urlLink[0]) {
                $uploadFile.show();
                $urlLink.show();
                $inputUrl.hide();
                $uploadLink.hide();
            } else {
                $uploadFile.hide();
                $urlLink.hide();
                $inputUrl.show();
                $uploadLink.show();
            }
        };

        var _submit = function() {
            var $searchForm = $container.find('.search-form');
            var url = $searchForm[0].url.value;
            if ($.trim(url) == '') {
                $searchForm[0].url.focus();
            } else {
                setTimeout( function() {
                    $searchForm.submit();
                }, 1);
            }
        };

        $urlLink.click(
            _toggleLink
        );

        $uploadLink.click(
            _toggleLink
        );

        $searchButton.click(
            _submit
        );
    }

};
