/**
 * Copyright 2012 Jiae Inc. All rights reserved.
 * 
 * @file:   Index.Base.js
 * @path:   js-src/index/
 * @desc:   Index主要Prototype原型对象，处理本模块基本的业务逻辑
 * @author: jarryli@gmail.com
 * @date:   2012-05-02
 */
 
///import js-src/lib/
///import js-src/com/

Index.Base = function () {
    this.dao = Index.Dao;
    this.uploadDao = Upload.Dao;
    this.tempUrl = Config.TEMP_URL;
    this.loadingImg = Config.IMG_URL + '/' + 'loading.gif';
};

Index.Base.prototype = {

    uploadImage : function(_form) {
        var self = this;
        var $form = $(_form);
        var button = $form.find('.upload-button')[0];
        var $button = $(button);
        var $uploadFile = $form.find('.upload-file');
        var $inputFile = $form.find('.input-file');
        var $preview = $form.find('.preview').find('p'); 
        var $loading = $uploadFile.find('.loading');
        var $error = $uploadFile.find('.error');
        var $img     = $preview.find('img');
        var fileName = $inputFile[0].name;
        var inputId = $inputFile[0].id;

        var _submit = function(file, ext) {
            $loading.show();
            $img.attr('src', self.loadingImg);
            $img.removeAttr('width');
            $img.removeAttr('height');
        };

        var _setFormValue = function(image) {
            _form.name.value = image.name;
            _form.type.value = image.type;
            _form.width.value = image.width;
            _form.height.value = image.height;
            $error.hide();
            $loading.hide();
        };

        var _setError = function() {
            $error.text('上传出现错误。请检查图片格式。');
            $error.show();
        };

        var _setPreivewImage = function(image, flag) {
            $img.removeAttr('src');
            var flag = flag || 100;
            if (image.width > image.height) {
                $img.attr('width', flag);
            } else {
                $img.attr('height', flag);
            }
            $img.attr('src', self.tempUrl + '/' + image.name);
            $preview.show();
        };

        var _callBack = function(data, file) {
            try {
                var state = data.state;
                var _data  = data.data;
                var image = _data.image;
                if (state && state == true) {
                    _setPreivewImage(image);
                    _setFormValue(image);
                } else {
                    
                }
            } catch (e) {
                // console.log(e.message);
            }
        };

        this.uploadDao.uploadImage(inputId, fileName, 'json', _submit, _callBack);
    },

    bindTabLinkEvent : function($container) {
        var self = this;
        var $container = $container || $('.index-container');
        var $form = $container.find('.index-form');
        var $uploadFile = $container.find('.upload-file');
        var $inputUrl = $container.find('.input-url');
        var $searchButton = $inputUrl.find('.search-button');

        var $loading = $uploadFile.find('.loading');
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
            $searchForm.submit();
        };

        $urlLink.click(
            _toggleLink
        );

        $uploadLink.click(
            _toggleLink
        );

        $searchButton.click(
            function() {
                self.getImageByUrl($form);
            }
        );
    },

    getImageByUrl : function($form) {
        var self = this;
        var _form = $form[0];
        var $inputUrl = $form.find('.input-url');
        var urlString = _form.url.value;
        if ($.trim(urlString) == '') {
            _form.url.focus();
            return;
        }

        var $preview = $form.find('.preview').find('p');
        var $img     = $preview.find('img');
        var $loading = $inputUrl.find('.loading');

        var _setFormValue = function(image) {
            _form.name.value = image.name;
            _form.type.value = image.type;
            _form.width.value = image.width;
            _form.height.value = image.height;
            $loading.hide();
        };

        var _setPreivewImage = function(image, flag) {
            $img.removeAttr('src');
            var flag = flag || 100;
            if (image.width > image.height) {
                $img.attr('width', flag);
            } else {
                $img.attr('height', flag);
            }
            $img.attr('src', self.tempUrl + '/' + image.name);
            $preview.show();
        };

        function callBack(data) {
            try {
                var state = data.state;
                var _data  = data.data;
                var image = _data.image;
                if (state && state == true) {
                    _setPreivewImage(image);
                    _setFormValue(image);
                } else {
                    
                }
            } catch (e) {
                console.log(e.message);
            }
        }

        $loading.show();
        this.uploadDao.downloadImage({ 'url' : urlString}, callBack);
    },

};
