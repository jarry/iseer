/**
 * Copyright 2012 Jiae Inc. All rights reserved.
 * 
 * @file:    Scroll.js
 * @path:    js-src/com/
 * @desc:    Scroll页面滚动控制器
 * @author:  lichunping
 * @date:    2012-04-11
 */
 

var Scroll = Scroll || {};

Scroll = function() {

    /**
     * 跟随页面滚动器，指向页面顶部
     * @param {JQuery Object} $ScrollToTop 页面滚动对象
     */
    var toTop = function($ScrollToTop) {
        // if object is not existed
        // get the object from Index moudle config while every scrolling
        if (!$ScrollToTop || $ScrollToTop.length <= 0) {
            $ScrollToTop = $('#' + Index.Config.SCROLL_TOP_ID);
        }
        var distance = (window.innerWidth ? 
                        window.pageYOffset : 
                        document.documentElement.scrollTop);

        if (distance > $(window).height() / 2) {
           // $ScrollToTop.fadeIn();
            $ScrollToTop.removeClass('scroll-to-top-hide');
            $ScrollToTop.addClass('scroll-to-top');

        } else {
           // $ScrollToTop.fadeOut();
            $ScrollToTop.addClass('scroll-to-top-hide');
        }
    };

    return {
        toTop : toTop
        
    };


}();

