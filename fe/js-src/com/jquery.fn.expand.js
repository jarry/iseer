/**
 * Copyright 2012 Jiae Inc. All rights reserved.
 * 
 * @file:    jquery.fn.expand.js
 * @path:    js-src/com/
 * @desc:    jquery的扩展函数for Jiae
 * @author:  jarryli@gmail.com
 * @date:    2012-04-11
 */


/**
 * 拖动页面超过改层的top
 * 该对象悬浮并固定在页面某个位置
 * 
 */
$.fn.floatFixed = function() {
    var position = function(element) {
        var top = element.position().top;
//        var pos = element.css('position');
        
        $(window).scroll(function() {
//        $(window).bind('smartscroll.floatFixed', function () {
            var scrolls = $(this).scrollTop();
            if (scrolls > top) {
                // if not IE
                if (window.XMLHttpRequest) {
                    element.css({
                        position: 'fixed',
                        top: 0
                    });
                } else {
                    element.css({
                        top: scrolls
                    });
                }
            }else {
                element.css({
                    position: 'absolute',
                    top: top
                });
            }
        });
    };
    
    return $(this).each(function() {
        position($(this));
    });
};



  /*
   * smartresize: debounced resize event for jQuery
   *
   * latest version and complete README available on Github:
   * https://github.com/louisremi/jquery.smartresize.js
   * https://github.com/louisremi/jquery-smartresize/blob/master/jquery.throttledresize.js
   *
   * Copyright 2011 @louis_remi
   * Licensed under the MIT license.
   */
(function($) {
  var $event = $.event,
      resizeTimeout;

  $event.special.smartresize = {
    setup: function() {
      $(this).bind( "resize", $event.special.smartresize.handler );
    },
    teardown: function() {
      $(this).unbind( "resize", $event.special.smartresize.handler );
    },
    handler: function( event, execAsap ) {
      // Save the context
      var context = this,
          args = arguments;

      // set correct event type
      event.type = "smartresize";

      if ( resizeTimeout ) { clearTimeout( resizeTimeout ); }
      resizeTimeout = setTimeout(function() {
        jQuery.event.handle.apply( context, args );
      }, execAsap === "execAsap"? 0 : 100 );
    }
  };

  $.fn.smartresize = function( fn ) {
    return fn ? this.bind( "smartresize", fn ) : this.trigger( "smartresize", ["execAsap"] );
  };

})(jQuery);


/* 
* smartscroll: debounced scroll event for jQuery *
* https://github.com/lukeshumard/smartscroll
* based on smartresize by @louis_remi: https://github.com/lrbabe/jquery.smartresize.js *
* Copyright 2011 Louis-Remi & lukeshumard * Licensed under the MIT license. *
*/
(function($) {
    var event = $.event,
        scrollTimeout;

    event.special.smartscroll = {
        setup: function() {
          $(this).bind( "scroll", event.special.smartscroll.handler );
        },
        teardown: function() {
          $(this).unbind( "scroll", event.special.smartscroll.handler );
        },
        handler: function( event, execAsap ) {
          // Save the context
          var context = this,
              args = arguments;

          // set correct event type
          event.type = "smartscroll";

          if (scrollTimeout) { clearTimeout(scrollTimeout); }
          scrollTimeout = setTimeout(function() {
            jQuery.event.handle.apply( context, args );
          }, execAsap === "execAsap"? 0 : 100);
        }
    };

    $.fn.smartscroll = function( fn ) {
        return fn ? this.bind( "smartscroll", fn ) : this.trigger( "smartscroll", ["execAsap"] );
    };
})(jQuery);


/* 
* select text
*/
(function($) {
    $.fn.selectRange = function(start, end) {
        return this.each(function() {
            if (this.setSelectionRange) {
                this.focus();
                this.setSelectionRange(start, end);
            } else if (this.createTextRange) {
                var range = this.createTextRange();
                range.collapse(true);
                range.moveEnd('character', end);
                range.moveStart('character', start);
                range.select();
            }
        });
    };
})(jQuery);


  
  // ======================= imagesLoaded Plugin ===============================
  /*
   * jQuery imagesLoaded plugin v1.1.0
   * http://github.com/desandro/imagesloaded
   *
   * MIT License. by Paul Irish et al.
   */


  // $('#my-container').imagesLoaded(myFunction)
  // or
  // $('img').imagesLoaded(myFunction)

  // execute a callback when all images have loaded.
  // needed because .load() doesn't work on cached images

  // callback function gets image collection as argument
  //  `this` is the container

  $.fn.imagesLoaded = function( callback ) {
    var $this = this,
        $images = $this.find('img').add( $this.filter('img') ),
        len = $images.length,
        blank = 'data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==',
        loaded = [];

    function triggerCallback() {
      callback.call( $this, $images );
    }

    function imgLoaded( event ) {
      var img = event.target;
      if ( img.src !== blank && $.inArray( img, loaded ) === -1 ){
        loaded.push( img );
        if ( --len <= 0 ){
          setTimeout( triggerCallback );
          $images.unbind( '.imagesLoaded', imgLoaded );
        }
      }
    }

    // if no images, trigger immediately
    if ( !len ) {
      triggerCallback();
    }

    $images.bind( 'load.imagesLoaded error.imagesLoaded',  imgLoaded ).each( function() {
      // cached images don't fire load sometimes, so we reset src.
      var src = this.src;
      // webkit hack from http://groups.google.com/group/jquery-dev/browse_thread/thread/eee6ab7b2da50e1f
      // data uri bypasses webkit log warning (thx doug jones)
      this.src = blank;
      this.src = src;
    });

    return $this;
  };


  // helper function for logging errors
  // $.error breaks jQuery chaining
  var logError = function( message ) {
    if ( window.console ) {
      window.console.error( message );
    }
  };


