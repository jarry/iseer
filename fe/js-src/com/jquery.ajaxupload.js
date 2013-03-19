/*
 * Copyright 2012 Jiae.com All rights reserved.
 * 
 * file: jquery.ajaxupload.js
 * path: js-src/
 * description: 简易ajax上传文件
 * see: http://www.phpletter.com/Our-Projects/AjaxFileUpload/
 *      http://valums.com/ajax-upload/ (another upload)
 *      https://github.com/valums/ajax-upload
 *      http://blueimp.github.com/jQuery-File-Upload/
 * 
 * date: 2012-4-5
 */

jQuery.extend({
    

    createUploadIframe: function(id, uri)
    {
            //create frame
            var frameId = 'jUploadFrame' + id;
            var iframeHtml = '<iframe id="' + frameId + '" name="' + frameId + '" style="position:absolute; top:-9999px; left:-9999px"';
            if(window.ActiveXObject)
            {
                if(typeof uri== 'boolean'){
                    iframeHtml += ' src="' + 'javascript:false' + '"';

                }
                else if(typeof uri== 'string'){
                    iframeHtml += ' src="' + uri + '"';

                }   
            }
            iframeHtml += ' />';
            jQuery(iframeHtml).appendTo(document.body);

            return jQuery('#' + frameId).get(0);            
    },
    createUploadForm: function(id, fileElementId, data)
    {
        //create form   
        var formId = 'jUploadForm' + id;
        var fileId = 'jUploadFile' + id;
        var form = jQuery('<form  action="" method="POST" name="' + formId + '" id="' + formId + '" enctype="multipart/form-data"></form>');    
        if(data)
        {
            for(var i in data)
            {
                jQuery('<input type="hidden" name="' + i + '" value="' + data[i] + '" />').appendTo(form);
            }           
        } 
        var oldElement = jQuery('#' + fileElementId);
        var newElement = jQuery(oldElement).clone();
        jQuery(oldElement).attr('id', fileId);
        jQuery(oldElement).before(newElement);
        jQuery(oldElement).appendTo(form);
        
        //set attributes
        jQuery(form).css('position', 'absolute');
        jQuery(form).css('top', '-10000px');
        jQuery(form).css('left', '-10000px');
        jQuery(form).appendTo('body');      
        return form;
    },

    ajaxFileUpload: function(s) {
        // TODO introduce global settings, allowing the client to modify them for all requests, not only timeout        
        s = jQuery.extend({}, jQuery.ajaxSettings, s);
        var id = new Date().getTime()        
        var form = jQuery.createUploadForm(id, s.fileElementId, (typeof(s.data)=='undefined'?false:s.data));
        var io = jQuery.createUploadIframe(id, s.secureuri);
        var frameId = 'jUploadFrame' + id;
        var formId = 'jUploadForm' + id;        
        // Watch for a new set of requests
        if ( s.global && ! jQuery.active++ )
        {
            jQuery.event.trigger( "ajaxStart" );
        }            
        var requestDone = false;
        // Create the request object
        var xml = {}   
        if ( s.global )
            jQuery.event.trigger("ajaxSend", [xml, s]);
        // Wait for a response to come back
        var uploadCallback = function(isTimeout)
        {           
            var io = document.getElementById(frameId);
            try 
            {               
                if(io.contentWindow)
                {
                     xml.responseText = io.contentWindow.document.body?io.contentWindow.document.body.innerHTML:null;
                     xml.responseXML = io.contentWindow.document.XMLDocument?io.contentWindow.document.XMLDocument:io.contentWindow.document;
                     
                }else if(io.contentDocument)
                {
                     xml.responseText = io.contentDocument.document.body?io.contentDocument.document.body.innerHTML:null;
                    xml.responseXML = io.contentDocument.document.XMLDocument?io.contentDocument.document.XMLDocument:io.contentDocument.document;
                }                       
            } catch(e) {
                jQuery.handleError(s, xml, null, e);
            }

            if ( xml || isTimeout == "timeout") 
            {               
                requestDone = true;
                var status;
                try {
                    status = isTimeout != "timeout" ? "success" : "error";
                    // Make sure that the request was successful or notmodified
                    if ( status != "error" )
                    {
                        // process the data (runs the xml through httpData regardless of callback)
                        var data = jQuery.uploadHttpData( xml, s.dataType );    
                        // If a local callback was specified, fire it and pass it the data
                        if ( s.success )
                            s.success( data, status );
    
                        // Fire the global callback
                        if( s.global )
                            jQuery.event.trigger( "ajaxSuccess", [xml, s] );
                    } else {
                        jQuery.handleError(s, xml, status);
                    }
                } catch(e) {
                    status = "error";
                    jQuery.handleError(s, xml, status, e);
                }

                // The request was completed
                if( s.global )
                    jQuery.event.trigger( "ajaxComplete", [xml, s] );

                // Handle the global AJAX counter
                if ( s.global && ! --jQuery.active )
                    jQuery.event.trigger( "ajaxStop" );

                // Process result
                if ( s.complete )
                    s.complete(xml, status);

                jQuery(io).unbind()

                setTimeout(function()
                                    {   try 
                                        {
                                            jQuery(io).remove();
                                            jQuery(form).remove();  
                                            
                                        } catch(e) 
                                        {
                                            jQuery.handleError(s, xml, null, e);
                                        }                                   

                                    }, 100)

                xml = null

            }
        }
        // Timeout checker
        if ( s.timeout > 0 ) 
        {
            setTimeout(function(){
                // Check to see if the request is still happening
                if( !requestDone ) uploadCallback( "timeout" );
            }, s.timeout);
        }
        try 
        {

            var form = jQuery('#' + formId);
            jQuery(form).attr('action', s.url);
            jQuery(form).attr('method', 'POST');
            jQuery(form).attr('target', frameId);
            if(form.encoding)
            {
                jQuery(form).attr('encoding', 'multipart/form-data');               
            }
            else
            {   
                jQuery(form).attr('enctype', 'multipart/form-data');            
            }           
            jQuery(form).submit();

        } catch(e) 
        {           
            jQuery.handleError(s, xml, null, e);
        }
        
        jQuery('#' + frameId).load(uploadCallback   );
        return {abort: function () {}}; 

    },

    // by jarryli@gmail.com
    uploadHttpData: function( r, type ) {
        var data = !type;
        data = type == "xml" || data ? r.responseXML : r.responseText;

        // If the type is "script", eval it in global context
        if ( type == "script" ) {
            jQuery.globalEval( data );

        } else if ( type == "json" ) {
        // Get the JavaScript object, if JSON is used.
            //eval( "data = " + data );
            data = (new Function('return (' + data + ')'))();
            /*
            data = (r.responseXML.body.getElementsByTagName('pre')[0].innerHTML);
            data = (new Function("return (" + data + ")"))();
            // direcotor parse to JSON
            //data = jQuery.parseJSON($(data).text());
            */

        } else if ( type == "html" ) {
            // evaluate scripts within html
            jQuery("<div>").html(data).evalScripts();
        }
        return data;
    },

    // added by jarryli@gmail.com
    handleError: function( s, xhr, status, e )      {
    // If a local callback was specified, fire it
        if ( s.error ) {
            s.error.call( s.context || s, xhr, status, e );
        }
        // Fire the global callback
        if ( s.global ) {
            (s.context ? jQuery(s.context) : jQuery.event).trigger( "ajaxError", [xhr, s, e] );
        }
    }

});

