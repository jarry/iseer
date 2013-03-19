/*
 * Copyright (c) 2011, All Rights Reserved.
 */

package com.jiae.iseer.basic.velocity;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.VelocityStrutsUtil;
import org.apache.struts2.views.util.ContextUtil;
import org.apache.struts2.views.velocity.StrutsVelocityContext;
import org.apache.struts2.views.velocity.VelocityManager;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
//import org.apache.velocity.tools.view.ViewToolContext;
import org.apache.velocity.tools.view.context.ChainedContext;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * ClassName: MyVelocityManager
 * Description: 支持toolbox
 * 
 * @author   <a href="mailto:lichunpin@baidu.com">LiChunping</a>
 * @version  1.0.0
 * @since    TODO
 * @Date     2011-9-27 下午06:53:03
 *
 * @see      
 */

@SuppressWarnings({ "unchecked", "deprecation" })
public class MyVelocityManager extends VelocityManager {
    
    /* (non-Javadoc)
     * @see org.apache.struts2.views.velocity.VelocityManager#createContext
     * (com.opensymphony.xwork2.util.ValueStack, javax.servlet.http.HttpServletRequest, 
     * javax.servlet.http.HttpServletResponse)
     */
    @Override    
    public Context createContext(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        VelocityContext[] chainedContexts = prepareChainedContexts(req, res, stack.getContext());
        StrutsVelocityContext context = new StrutsVelocityContext(chainedContexts, stack);
        Map standardMap = ContextUtil.getStandardContext(stack, req, res);
        for (Iterator iterator = standardMap.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            context.put((String) entry.getKey(), entry.getValue());
        }
        context.put(STRUTS, new VelocityStrutsUtil(getVelocityEngine(), context, stack, req, res));

//        ViewToolContext ctx = null;
        ServletContext ctx = null;
        
        try {
            ctx = ServletActionContext.getServletContext();
//            ctx = new ViewToolContext(getVelocityEngine(), req, res, getServletContext());
        } catch (NullPointerException npe) {
            // in case this was used outside the lifecycle of struts servlet
        }

        if (toolboxManager != null && ctx != null) {

            // here is the new constructor :
            ChainedContext chained = new ChainedContext(getVelocityEngine(), req, res, (ServletContext) ctx);
            

            chained.setToolbox(toolboxManager.getToolbox(chained));
            return chained;
        } else {
            return context;
        }

    }
    
   }  