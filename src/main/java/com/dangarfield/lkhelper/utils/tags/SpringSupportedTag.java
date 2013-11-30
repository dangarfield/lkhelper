package com.dangarfield.lkhelper.utils.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;


public abstract class SpringSupportedTag extends SimpleTagSupport {
	
	protected WebApplicationContext _applicationContext;

	protected WebApplicationContext getSpringContext(){
	    PageContext pageContext = (PageContext) getJspContext();
	    if(_applicationContext==null){
	        _applicationContext = RequestContextUtils.getWebApplicationContext(
	                pageContext.getRequest(),
	                pageContext.getServletContext()
	            );
	        initCustomBeans();
	    }
	    return _applicationContext;
	}

	protected abstract void initCustomBeans();

	/**
	 * Deprecated for inserting extra logic. Use {@link #doTagWithSpring()} instead.
	 */
	@Override
	@Deprecated
	public void doTag() throws JspException {
	    getSpringContext();
	    doStartTagWithSpring();
	}


	abstract void doStartTagWithSpring() throws JspException;
	
}
