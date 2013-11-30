package com.dangarfield.lkhelper.utils.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
public class CreateQueryStringForImage extends TagSupport {

	private String clear;
	private String thickness;
	private String base;
	private String colour;

	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			final String clearString = clear;
			final String thicknessString = thickness;
			final String baseString = base;
			final String colourString = colour;
			
			final List<String> queryStringList = new ArrayList<String>(); 
			
			if (clearString != null && !clearString.isEmpty() && clearString.equals("true")) {
				queryStringList.add("clear=true");
			}
			if (thicknessString != null && !thicknessString.isEmpty()) {
				queryStringList.add("thickness=" + thicknessString);
			}
			if (baseString != null && !baseString.isEmpty() && baseString.equals("true")) {
				queryStringList.add("base=true");
			}
			if (colourString != null && !colourString.isEmpty()) {
				queryStringList.add("colour=" + thicknessString);
			}
			
			if(!queryStringList.isEmpty()) {
				String queryString = "?" + StringUtils.join(queryStringList, "&");
				out.print(queryString);
			}

		} catch (IOException ioe) {
			throw new JspException("Error: " + ioe.getMessage());
		}
		return SKIP_BODY;
	}

	/**
	 * @return the clear
	 */
	public String getClear() {
		return clear;
	}

	/**
	 * @param clear the clear to set
	 */
	public void setClear(String clear) {
		this.clear = clear;
	}

	/**
	 * @return the thickness
	 */
	public String getThickness() {
		return thickness;
	}

	/**
	 * @param thickness the thickness to set
	 */
	public void setThickness(String thickness) {
		this.thickness = thickness;
	}

	/**
	 * @return the base
	 */
	public String getBase() {
		return base;
	}

	/**
	 * @param base the base to set
	 */
	public void setBase(String base) {
		this.base = base;
	}

	/**
	 * @return the colour
	 */
	public String getColour() {
		return colour;
	}

	/**
	 * @param colour the colour to set
	 */
	public void setColour(String colour) {
		this.colour = colour;
	}


}
