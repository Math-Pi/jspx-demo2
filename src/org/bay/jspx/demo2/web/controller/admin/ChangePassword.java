package org.bay.jspx.demo2.web.controller.admin;

import org.apache.log4j.Logger;

import org.bay.jspx.demo2.engine.ServiceLocator;
import org.bay.jspx.demo2.web.util.Security;

import eg.java.net.web.jspx.engine.annotation.JspxWebControl;
import eg.java.net.web.jspx.ui.controls.WebControl;
import eg.java.net.web.jspx.ui.controls.html.elements.Label;
import eg.java.net.web.jspx.ui.controls.html.elements.inputs.Password;
import eg.java.net.web.jspx.ui.pages.ContentPage;

/**
 * Change user password.
 * 
 * @author amr.eladawy
 * 
 */
public class ChangePassword extends ContentPage
{

	private static final Logger logger = Logger.getLogger(ChangePassword.class);

	public void changePassword(WebControl invoker, String args)
	{
		if (newPass.getValue().equals(newPAss1.getValue()))
		{
			ServiceLocator.userService.changePassword(Security.getAuthenticatedUser(request), oldPass.getValue(), newPass.getValue());
			result.setCssClass("success");
			result.setValue(" „  €ÌÌ— ﬂ·„… «·”— »‰Ã«Õ");
		}
		else
		{
			result.setCssClass("error");
			result.setValue("ﬂ·„… «·”— «·ÃœÌœ… €Ì— „ ÿ«»ﬁ…");
		}

	}

	@JspxWebControl
	private Label result;
	@JspxWebControl
	private Password oldPass;
	@JspxWebControl
	private Password newPass;
	@JspxWebControl
	private Password newPAss1;

}
