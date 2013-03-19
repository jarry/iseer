/**
 * Copyright 2012 Jiae Inc. All rights reserved.
 * 
 * @file:   Com.js
 * @path:   js-src/com
 * @desc:   项目的全局公共静态小函数集合，所有函数均为公开
 *          函数采取全部键值对象式声明，方法名采用驼峰式命名
 * @author: jarryli@gmail.com
 * @date:   2012-04-11
 */
 
///import js-src/lib/
///import js-src/com/

var Com = Com || {};

Com = {

    /**
     * 绑定返回顶部滚动按钮的方法
     * @param {HTMLElement} elementId 默认为ID为ScrollToTop
     */
    bindScrollToTop : function(elementId) {
        var $ScrollToTop = (elementId) ? $('#' + elementId) : $('#ScrollToTop');
        $(window).bind('smartscroll.scrollToTop', function () {
            Scroll.toTop($ScrollToTop);
        }); 
    },

    /**
     * 遍历父节点,获得含有指定css名称的某个父节点(最近的)
     * 最顶层为document，如果没有返回则null
     * 
     * @param {HTMLElement} elem DOM自己
     * @param {String} cssName 父节点的css名称，可以带.
     * @return {HTMLElement} 指定的父节点 或者 Null
     */
    getParentByCss : function(elem, cssName) {
        var firstChar = cssName.substr(0, 1);
        if (firstChar === '.') {
            cssName = cssName.substr(1, cssName.length);
        }

        var classes = '';
        while (( elem = elem.parentNode) && elem != document.documentElement) {

            if ( elem.className == cssName) {
                return elem;
            }

            // 如果css名称多个，含有即可
            classes = elem.className.split(' ');
            if( classes.length > 0 ) {

                for (var i = 0, l = classes.length; i < l; i++) {
                    if (classes[i] == cssName) {
                        return elem;
                    }
                }

            }

        }
        return null;
    },

    /**
     * 交换a与b元素的内容
     * 
     * @param {Object} a 
     * @param {Object} b
     */
    exchange : function(a, b) {
        var tmp = a;
        b = a;
        a = tmp;
    },

    /**
     * 遍历父节点,获得含有指定tag名称的某个父节点(最近的)
     * 最顶层为document，如果没有返回则null
     * 
     * @param {HTMLElement} elem DOM自己
     * @param {String} tagName 父节点的tag名称
     * @return {HTMLElement} 指定的父节点 或者 Null
     */
    getParentByTag : function(elem, tagName) {
        while (elem != document.documentElement) {
            if (elem.tagName.toLowerCase() == tagName.toLowerCase()) {
                return elem;
            }
            elem = elem.parentNode;
        }
        return null;
    },

    /**
     * 获取字符串的长度，中英算作2个，英文算作1个 
     * 根据标准ASCII码计算
     * 
     * @param {String} str 字符串
     * @return {Number} 字符串长度
     */
    getStrLength : function(str) {

        // return String(str).replace(/[^\x00-\xff]/g, "ci").length;

        if( typeof(str) != 'string') {
            str = str.toString();
        }
        var count = 0;

        for (var i = 0, l = str.length; i < l; i++) {
            // 0-127 ASCII char
            // 128-255 ASCII expand char
            if ( str.charCodeAt(i) > 255 ) {
                count += 2;   
            } else {
                count += 1;
            }
        }

        return count;
   },

    /**
     * 截取字符串，中文按2个，英文按1个长度计算
     * 
     * @param {String} str 字符串
     * @return {Number} 字符串长度
     */
    substrCn : function (str, length) {
        if( typeof(str) != 'string') {
            str = str.toString();
        }

        var _tmpStr = [];
        var j = 0;
        var i = 0;
        var strCnLen = 0;
        if (str.length < length) {
            return str;
        }
        // 全部按照双字节长度来处理
        // 截取的长度 x 2       
        length = length * 2;
        while (j < length) {
            if (str.charCodeAt(i) > 255) {
                j += 2;
            } else {
                j ++;
            }
            _tmpStr.push(str.charAt(i));
            // _tmpStr.push(str[i]);
            i++;
        }
        
        // 得到截取字符是否为奇数
        // 如是奇数则表示有1个单字节字符多余
        // 最后1个单字节字符按照1个双字节处理
        // 多余的删除掉
        strCnLen = i;
        if (strCnLen % 2 != 0) {
            _tmpStr.pop();
        }

        return _tmpStr.join('');
   },

    /**
     * 字符替换，不区分大小写，替换所有
     * 
     * @param {string} strObj 字符串
     * @param {string} oldWord 原字符
     * @param {string} newWord 新字符
     * @return {string} 替换后的字符串
     */
    replaceAll : function(strObj, oldWord, newWord) {
        var reg = new RegExp(oldWord, 'gmi');
        return strObj.replace(reg, newWord);
    },

    /**
     * 原型继承类，将父类全部prototype复制到子类，同名方法之类覆盖父类。
     * TODO：来自baidu tangram，仅在原型继承时使用，运行时继承采用jQuery.extend
     *      
     *
     * @name baidu.lang.inherits
     * @function
     * @grammar baidu.lang.inherits(subClass, superClass[, type])
     * @param {Function} subClass 子类构造器
     * @param {Function} superClass 父类构造器
     * @param {string} type 类名标识
     * @remark
     * 
     * 使subClass继承superClass的prototype，因此subClass的实例能够使用superClass的prototype中定义的所有属性和方法。<br>
     * 这个函数实际上是建立了subClass和superClass的原型链集成，并对subClass进行了constructor修正。<br>
     * <strong>注意：如果要继承构造函数，需要在subClass里面call一下，具体见下面的demo例子</strong>

     * @shortcut inherits
     * @meta standard
     * @see baidu.lang.Class
     */
   inherits : function (subClass, superClass, type) {
        var key, proto, 
            selfProps = subClass.prototype, 
            clazz = new Function();
            
        clazz.prototype = superClass.prototype;
        proto = subClass.prototype = new clazz();
    
        for (key in selfProps) {
            proto[key] = selfProps[key];
        }
        subClass.prototype.constructor = subClass;
        subClass.superClass = superClass.prototype;
    
        // 类名标识，兼容Class的toString，基本没用
        typeof type == "string" && (proto.__type = type);
    
        subClass.extend = function(json) {
            for (var i in json) proto[i] = json[i];
            return subClass;
        };

        return subClass;
    },


    showError : function($dom, $tip, message) {
         try {
             if ($tip) {
                 $tip.addClass('error');
             }
             if ($dom) {
                 $dom.addClass('error');
                 $dom[0].focus();
             }
             if (message && $tip ) {
                 $tip.html(message);
             }
        } catch(e) {
            // alert(e);
        }
    },

    showMessage : function($dom, $tip, message) {
         try {
             if ($tip) {
                 $tip.removeClass('error');
             }
             if ($dom) {
                 $dom.removeClass('error');
             }
             if (message && $tip) {
                 $tip.html(message);
             }
        } catch(e) {
            // alert(e);
        }
    },

    /**
     * 判断JOSN对象是否为空
     *
     * @function
     * @param {object} obj json object对象
     * @return {boolean}
     */
    hasProperty : function(obj) {
        for (var prop in obj) {
            return true;
        }
        return false;
    },

    /**
     * 得到json及其子json的某个属性值（第一个），递归遍历整个对象
     *
     * @function
     * @param {string} prop property名称
     * @param {object} obj json object对象
     * @return {value} 对象value或者null
     */
    getProperty : function(prop, obj) {

        if ( 'string' != typeof prop || 'object' != typeof obj ) {
            return null;
        }

        if( obj.hasOwnProperty(prop) ) {
            return obj[prop];
        }
        
        for (var _prop in obj) {
            if ( 'object' == typeof obj[_prop] ) {
                return Com.getProperty( prop, obj[_prop] );
            }
        }

        return null;
    },

    /**
     * 当json某个属性值为number时，如果是0或'0'，则输出为空字符串
     * 更改传递引用，不保留原来的json
     * 
     * @function
     * @param {string OR array} prop property名称或名称数组
     * @param {object} json object对象
     */
     removeZeroNumberForJson : function(prop, json) {
        if ( ('string' != typeof prop && !(prop instanceof Array) ) ||
             'object' != typeof json ) {
            return;
        }
        var props = [];
        if ('string' == typeof props) {
            props[0] = props;
        } else {
            props = prop;
        }

        for (var prop in json) { 
            if ( 'object' == typeof json[prop] ) {
                Com.removeZeroNumberForJson(props, json[prop]);
            } else {

                for (var i = 0, l = props.length; i < l; i++) {
                    if ( prop == props[i] && parseInt(json[prop]) == 0 ) {
                        json[prop] = '';
                    }
                }
            }
        }

    },

   setPlaceHoler : function(elem) {
        if (!elem || !elem.nodeName) {
            return;
        }
        var input = elem;
        var supportPlaceholder = 'placeholder' in elem;
        var _placeholder = function (input) {
            var text = input.getAttribute('placeholder'),
                defaultValue = input.defaultValue;
            if (defaultValue == '') {
                input.value = text;
            }
            input.onfocus = function () {
                if (input.value === text) {
                    this.value = '';
                }
            };
            input.onblur = function () {
                if (input.value === '') {
                    this.value = text;
                }
            };
        };

        if ($.browser.msie || !supportPlaceholder) {
               if (input.type === 'text' && 
                   input.getAttribute('placeholder') ) {
                   _placeholder(input);
               }
        }
    },

    getMousePosition : function (e){
        if(e.pageX || e.pageY) {
            return {
                x : e.pageX,
                y : e.pageY
            };
        }
        return {
            x : e.clientX + document.body.scrollLeft - document.body.clientLeft,
            y : e.clientY + document.body.scrollTop  - document.body.clientTop
        };
    },

    /**
     * 显示help的提示
     * @function
     * @param {string} self dom对象自己
     * @param {string} tipid tip对象id
     * @param {string} title tip里面的title [可选]
     * @param {string} content tip里面的content [可选]
     * @param {object} position 坐标[可选]
     *
     */
    showHelpTip : function(self, tipid, title, content, position) {
        var $help = $('#' + tipid);  
        if (title) {
           $help.find('dt').html(title);
        } 
        if (content) {
           $help.find('dd').html(content);
        }
        var left = 0;
        var top  = 0;
        if (position) {
            left = position.x;
            top  = position.y;
        } else {
            // 通过获得事件对象的绝对坐标来设定tip的坐标
            var position = Com.dom.getPosition(self);
            left = position.x - $help.width() / 2 - 40 - $help.offset().left;
            top  = position.y - $help.height() - 12 - $help.offset().top;
          /* 
            var position = Com.dom.getPosition( $('.PinForm')[0] ); 
            left = position.x + $help.offset().left;
            top  = position.y - $help.offset().top;
           */
        }

        $help.css('left', left + 'px');
        $help.css('top', top + 'px');
        $help.show(); 
    },

    hideHelpTip : function(id) {
        var $help = $('#' + id);  
        $help.hide(); 
    },

    /*fix IE 6PNG 透明*/
    fixIE6PNG : function() {                          
        var arVersion = navigator.appVersion.split("MSIE");
        var version = parseFloat(arVersion[1]);
        var _isPNG = function() {
            var imgName = img.src.toUpperCase();
            var ext = imgName.substring( imgName.length-3, imgName.length);
            return (ext == 'PNG');
        };

        var _replaceImg = function(img) {
                img.onload = function() {
                    var width = img.width || img.offsetWidth || img.style.width;
                    var height = img.height || img.offsetHeight || img.style.height;
                    var imgID = (img.id) ? "id='" + img.id + "' " : "";
                    var imgClass = (img.className) ? "class='" + img.className + "' " : "";
                    var imgTitle = (img.title) ? "title='" + img.title + "' " : "title='" + img.alt + "' ";
                    var imgStyle = "display:inline-block;" + img.style.cssText;
                    if ( img.align == "left" ) imgStyle = "float:left;" + imgStyle;
                    if ( img.align == "right" ) imgStyle = "float:right;" + imgStyle;
                    if ( img.parentElement.href ) imgStyle = "cursor:hand;" + imgStyle;
                    var strNewHTML = "<span " + imgID + imgClass + imgTitle
                        + " style=\"" + "width:" + width + "px; height:" + height + "px;" + imgStyle + ";"
                        + "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader"
                        + "(src=\'" + img.src + "\', sizingMethod='scale');\"></span>";
                    img.outerHTML = strNewHTML;
                };

        };

        if ( version && ( version <= 6 ) && ( document.body.filters ) ) {
            for( var i = 0, l = document.images.length; i < l; i++ ) {
                var img = document.images[i];
                if ( _isPNG(img) ) {
                    _replaceImg(img);
                }
            }
        }
    }
};

Com.event = {
    preventDefault : function( evt ) {
        evt = evt || window.event;
        try {
            evt.preventDefault();   
        } catch ( e ) {
            evt.returnValue = false;
        }
    }
};

Com.array = {
    trim : function(arr) {
        var l = arr.length;
        while(--l > 0) {
            if(!arr[l] || arr[l] == '') {
                arr.splice(l, 1);
            }
        }
        return arr;
    },
    unique : function(arr) {
        var l = arr.length;
        while(--l > 0) {
            for(var i = 0; i < l; i++) {
                if(arr[l] == arr[i]) {
                    arr.splice(l, 1);
                    break;
                }
            }
        }
        return arr;
    },

    removeItem : function(arr, item) {
        var partA;
        var partB;
        for (var i = 0, l = arr.length; i < l; i++) {
            if (arr[i] === item) {
                partA = arr.slice(0, i);
                partB = arr.slice( (i + 1), l);
                return partA.concat(partB);
            }
        }
        return arr;
    }

}

Com.cookie = {
    /**
     * 验证字符串是否合法的cookie键名
     * 
     * @param {string} source 需要遍历的数组
     * @meta standard
     * @return {boolean} 是否合法的cookie键名
     */
    _isValidKey : function (key) {
        // http://www.w3.org/Protocols/rfc2109/rfc2109
        // Syntax:  General
        // The two state management headers, Set-Cookie and Cookie, have common
        // syntactic properties involving attribute-value pairs.  The following
        // grammar uses the notation, and tokens DIGIT (decimal digits) and
        // token (informally, a sequence of non-special, non-white space
        // characters) from the HTTP/1.1 specification [RFC 2068] to describe
        // their syntax.
        // av-pairs   = av-pair *(";" av-pair)
        // av-pair    = attr ["=" value] ; optional value
        // attr       = token
        // value      = word
        // word       = token | quoted-string
        
        // http://www.ietf.org/rfc/rfc2068.txt
        // token      = 1*<any CHAR except CTLs or tspecials>
        // CHAR       = <any US-ASCII character (octets 0 - 127)>
        // CTL        = <any US-ASCII control character
        //              (octets 0 - 31) and DEL (127)>
        // tspecials  = "(" | ")" | "<" | ">" | "@"
        //              | "," | ";" | ":" | "\" | <">
        //              | "/" | "[" | "]" | "?" | "="
        //              | "{" | "}" | SP | HT
        // SP         = <US-ASCII SP, space (32)>
        // HT         = <US-ASCII HT, horizontal-tab (9)>
            
        return (new RegExp("^[^\\x00-\\x20\\x7f\\(\\)<>@,;:\\\\\\\"\\[\\]\\?=\\{\\}\\/\\u0080-\\uffff]+\x24")).test(key);
    },

    /**
     * 获取cookie的值，不对值进行解码
     * @name Com.cookie.getRaw
     * @function
     * @grammar Com.cookie.getRaw(key)
     * @param {string} key 需要获取Cookie的键名
     * @meta standard
     * @see Com.cookie.get,Com.cookie.setRaw
     *             
     * @returns {string|null} 获取的Cookie值，获取不到时返回null
     */
    getRaw : function (key) {
        if (Com.cookie._isValidKey(key)) {
            var reg = new RegExp("(^| )" + key + "=([^;]*)(;|\x24)"),
                result = reg.exec(document.cookie);
                
            if (result) {
                return result[2] || null;
            }
        }

        return null;
    },

    /**
     * 获取cookie的值，用decodeURIComponent进行解码
     * @name Com.cookie.get
     * @function
     * @grammar Com.cookie.get(key)
     * @param {string} key 需要获取Cookie的键名
     * @remark
     * <b>注意：</b>该方法会对cookie值进行decodeURIComponent解码。如果想获得cookie源字符串，请使用getRaw方法。
     * @meta standard
     * @see Com.cookie.getRaw,Com.cookie.set
     *             
     * @returns {string|null} cookie的值，获取不到时返回null
     */
    get : function (key) {
        var value = Com.cookie.getRaw(key);
        if ('string' == typeof value) {
            value = decodeURIComponent(value);
            return value;
        }
        return null;
    },


    /**
     * 设置cookie的值，不对值进行编码
     * @name Com.cookie.setRaw
     * @function
     * @grammar Com.cookie.setRaw(key, value[, options])
     * @param {string} key 需要设置Cookie的键名
     * @param {string} value 需要设置Cookie的值
     * @param {Object} [options] 设置Cookie的其他可选参数
     * @config {string} [path] cookie路径
     * @config {Date|number} [expires] cookie过期时间,如果类型是数字的话, 单位是毫秒
     * @config {string} [domain] cookie域名
     * @config {string} [secure] cookie是否安全传输
     * @remark
     * 
    <b>options参数包括：</b><br>
    path:cookie路径<br>
    expires:cookie过期时间，Number型，单位为毫秒。<br>
    domain:cookie域名<br>
    secure:cookie是否安全传输
            
     * @meta standard
     * @see Com.cookie.set,Com.cookie.getRaw
     */
    setRaw : function (key, value, options) {
        if (!Com.cookie._isValidKey(key)) {
            return;
        }
        
        options = options || {};

        // 计算cookie过期时间
        var expires = options.expires;
        if ('number' == typeof options.expires) {
            expires = new Date();
            expires.setTime(expires.getTime() + options.expires);
        }
        
        document.cookie =
            key + "=" + value
            + (options.path ? "; path=" + options.path : "")
            + (expires ? "; expires=" + expires.toGMTString() : "")
            + (options.domain ? "; domain=" + options.domain : "")
            + (options.secure ? "; secure" : ''); 
    },

    /**
     * 删除cookie的值
     * @name Com.cookie.remove
     * @function
     * @grammar Com.cookie.remove(key, options)
     * @param {string} key 需要删除Cookie的键名
     * @param {Object} options 需要删除的cookie对应的 path domain 等值
     * @meta standard
     */
    remove : function (key, options) {
        options = options || {};
        options.expires = new Date(0);
        Com.cookie.setRaw(key, '', options);
    },

    /**
     * 设置cookie的值，用encodeURIComponent进行编码
     * @name Com.cookie.set
     * @function
     * @grammar Com.cookie.set(key, value[, options])
     * @param {string} key 需要设置Cookie的键名
     * @param {string} value 需要设置Cookie的值
     * @param {Object} [options] 设置Cookie的其他可选参数
     * @config {string} [path] cookie路径
     * @config {Date|number} [expires] cookie过期时间,如果类型是数字的话, 单位是毫秒
     * @config {string} [domain] cookie域名
     * @config {string} [secure] cookie是否安全传输
     * @remark
     * 
    1. <b>注意：</b>该方法会对cookie值进行encodeURIComponent编码。如果想设置cookie源字符串，请使用setRaw方法。<br><br>
    2. <b>options参数包括：</b><br>
    path:cookie路径<br>
    expires:cookie过期时间，Number型，单位为毫秒。<br>
    domain:cookie域名<br>
    secure:cookie是否安全传输
            
     * @meta standard
     * @see Com.cookie.setRaw,Com.cookie.get
     */
    set : function (key, value, options) {
        Com.cookie.setRaw(key, encodeURIComponent(value), options);
    }
};

Com.dom = {
    getPosition : function(elem) {
        var left = 0;
        var top  = 0;
        while ( elem && !isNaN(elem.offsetLeft) && !isNaN(elem.offsetTop) ) {
            left += elem.offsetLeft;
            top  += elem.offsetTop;
            elem = elem.offsetParent;
        }
        return { x : left, y : top };
    },

    setSizeToScroll : function(elem) {
        var w = Math.max(document.documentElement.scrollWidth, document.body.scrollWidth);
        var h = Math.max(document.documentElement.scrollHeight, document.body.scrollHeight);
        w = Math.max(w, document.body.offsetWidth);
        h = Math.max(h,  document.body.offsetHeight)
        elem.style.width = w + 'px';
        elem.style.height = h + 'px';
    }
};

Com.string = {
   /**
    * 对目标字符串进行html解码
    * @name baidu.string.decodeHTML
    * @function
    * @grammar baidu.string.decodeHTML(source)
    * @param {string} source 目标字符串
    * @shortcut decodeHTML
    * @meta standard
    * @see baidu.string.encodeHTML
    *             
    * @returns {string} html解码后的字符串
    */
    decodeHTML : function (source) {
       var str = String(source)
                   .replace(/&quot;/g,'"')
                   .replace(/&lt;/g,'<')
                   .replace(/&gt;/g,'>')
                   .replace(/&amp;/g, "&");
       //处理转义的中文和实体字符
       return str.replace(/&#([\d]+);/g, function(_0, _1){
           return String.fromCharCode(parseInt(_1, 10));
       });
    },

    filterHTML : function(source) {
        if (!source || source == '') {
            return source;
        }

        var str = source.replace(/<\/?[^>]*>/g,'');
        str = str.replace(/[ | ]*\n/g,'\n');
        str = str.replace(/\n[\s| | ]*\r/g,'\n');
        str = str.replace(/&nbsp;/ig,'');
        return str;
    },

   /**
    * 对目标字符串进行html编码
    * @name baidu.string.encodeHTML
    * @function
    * @grammar baidu.string.encodeHTML(source)
    * @param {string} source 目标字符串
    * @remark
    * 编码字符有5个：&<>"'
    * @shortcut encodeHTML
    * @meta standard
    * @see baidu.string.decodeHTML
    *             
    * @returns {string} html编码后的字符串
    */
    encodeHTML : function (source) {
       return String(source)
                   .replace(/&/g,'&amp;')
                   .replace(/</g,'&lt;')
                   .replace(/>/g,'&gt;')
                   .replace(/"/g, "&quot;")
                   .replace(/'/g, "&#39;");
    },
    
    /**
     * 对目标字符串进行unicode编码
     * @param {String} str 须要转换为unicode的字符
     *
     * @returns {string} html编码后的unicode串
     */
    enUnicode : function(str) {
        var strList = [];
        var _char = '';
        var tmp = '';
        /*
        for( var i = 0, l = str.length; i < l; i++ )  {
            _char = str.charCodeAt(i).toString(16); 
            tmp = '\\u' + new Array(5 - _char.length).join('0') + _char;       
            strList.push( tmp );
        };
         */
        for( var i = 0, l = str.length; i < l; i++ )  { 
            _char = str.charCodeAt(i);
            tmp   = '\\u' + ('00' + _char.toString(16)).slice(-4);
            strList.push(tmp);
        }


        return strList.join('');
        
    },
    /**
     * 对目标unicode串解码
     * @param {String} str unicode的字符
     *
     * @returns {string} 编码后的字符串
     */
    deUnicode : function(str) {
       str = str.replace(/\\/g, '%');
       return unescape(str);
    }
    
};

Com.lang = {

   /**
    * 判断目标参数是否为Element对象
    * @name baidu.lang.isElement
    * @function
    * @grammar baidu.lang.isElement(source)
    * @param {Any} source 目标参数
    * @meta standard
    * @see baidu.lang.isString,baidu.lang.isObject,baidu.lang.isNumber,baidu.lang.isArray,baidu.lang.isBoolean,baidu.lang.isDate
    *             
    * @returns {boolean} 类型判断结果
    */
    isElement : function (source) {
       return !!(source && source.nodeName && source.nodeType == 1);
    }

};

Com.url = {

    parse : function(url) {
        if (!url || url.length <= 0 ) {
            return false;
        } 
        var reg = '/(\w+):\/\/([^/:]+)(:\d*)?([^#]*)/';
        var result = reg.exec(url);
        var key = ['protocol', 'host', 'port', 'path'];
        var attr = {};
        for (var i = 1, l =  result.length; i < l; i++){
            attr[key[i - 1]] = result[i] || '';
        }
        return attr;
    },

    protocol : function(url) {
        var attr = this.parse(url);
        return attr['protocol'];
    },

    host : function(url) {
        var attr = this.parse(url);
        return attr['host'];
    },

    port : function(url) {
        var attr = this.parse(url);
        return attr['port'];
    },

    path : function(url) {
        var attr = this.parse(url);
        return attr['path'];
    }

};

Com.page = {

    fullScreenForIE : function(){
        alert("请按键盘上的F11全屏观看！");
        /*if($.browser.msie) {
            if(!!new ActiveXObject('WScript.Shell')) {
               // var WsShell = new ActiveXObject('WScript.Shell');
               // WsShell.SendKeys('{F11}');
            }
        }*/
    },

    loadFullScreen : function() {
        var docElm = document.documentElement;
        if (docElm.requestFullscreen) {
            docElm.requestFullscreen();
        } 
        else if (docElm.mozRequestFullScreen) {
            docElm.mozRequestFullScreen();
        }
        else if (docElm.webkitRequestFullScreen) {
            docElm.webkitRequestFullScreen();
        }
        else {
            this.fullScreenForIE();
        }              
    },
  
    exitFullScreen : function() {
        if (document.exitFullscreen) {
            document.exitFullscreen();
        }
        else if (document.mozCancelFullScreen) {
            document.mozCancelFullScreen();
        }
        else if (document.webkitCancelFullScreen) {
            document.webkitCancelFullScreen();
        } else {
            this.fullScreenForIE();
        }
    }

}

Com.isElement = Com.lang.isElement;

Com.imageAlign = {
    IMG_SIZE : {
        width : '150',
        height : '150'
    },
    // align clip position
    // l is left, r is right, c is center, m is middle
    // LT, RT, LB, RB, CM, LM, RM, TC, BC
    CLIP_ALIGN_TYPE : 'TC',

    align : function(img, type) {
        var width = this.IMG_SIZE.width;
        var height = this.IMG_SIZE.height;
        var ca = type || this.CLIP_ALIGN_TYPE;
        // set proportion for width and height
        var proportion = 1;

        var alignLeft = function(img) {
            // css default
            // do nothing
        };
            
        var alignTop = function(img) {
            // css default
            // do nothing
        };
            
        var alignRight = function(img) { 
            // var gap = (img.width - width);
            var gap = (img.width / proportion) - width;
            img.style.marginLeft = -(gap) + 'px';
        };

        var alignBottom = function(img) { 
            // var gap = (img.height - height);
            var gap = (img.height / proportion) - height;
            img.style.marginTop = -(gap) + 'px';
        };

        var alignMiddle = function(img) { 
            var gap = (img.height / proportion) - height;
            img.style.marginTop = - Math.floor( gap / 2) + 'px';
        };

        var alignCenter = function(img) { 
            var gap = (img.width / proportion) - width;
            // if don't use lazyload that the gap don't / proportion 
            // var gap = (img.width - width);
            // console.log(img.src + '?'+ img.width + ' x '+ img.height); 
            img.style.marginLeft = - Math.floor( gap / 2) + 'px';
        };

        // LT, RT, LB, RB, CM, LM, RM, TC, BC
        if (img.width < img.height) {
            proportion = img.width / width;
            if (ca.indexOf('T') != - 1) { 
                alignTop(img);
            } else if (ca.indexOf('M') != - 1) { 
                alignMiddle(img);
            } else if (ca.indexOf('B') != - 1) { 
                alignBottom(img);
            }

            img.width = width;

        } else {
            proportion = img.height / height;
            if (ca.indexOf('L') != - 1) { 
                alignLeft(img);
            } else if (ca.indexOf('C') != - 1) { 
                alignCenter(img);
            } else if (ca.indexOf('R') != - 1) { 
                alignRight(img);
            }

            img.height = height; 

        }
    }, 

    fitImageSize : function($imgList) { 
        var self = this;
        $imgList.each(function(i, img) {
            $(img).bind('load', function() {
                self.align(img);
            });
        });
    }


}; 

