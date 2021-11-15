package org.bay.jspx.demo2.web.controller;

import org.bay.jspx.demo2.engine.error.ErrorCode;

import eg.java.net.web.jspx.engine.annotation.JspxWebControl;
import eg.java.net.web.jspx.engine.util.StringUtility;
import eg.java.net.web.jspx.ui.controls.html.elements.Label;
import eg.java.net.web.jspx.ui.pages.ContentPage;

public class ErrorController extends ContentPage
{

	@Override
	protected void pageLoaded()
	{
		String error = (String) request.getAttribute(ErrorCode.GLOBAL_ERROR);
		error = StringUtility.isNullOrEmpty(error) ? "" : error;
		error = "${bundle." + error + "}";
		errorMessage.setValue(error);
	}

	@JspxWebControl
	private Label errorMessage;
}
