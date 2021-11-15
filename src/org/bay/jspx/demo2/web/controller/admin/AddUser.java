package org.bay.jspx.demo2.web.controller.admin;

import org.bay.jspx.demo2.engine.ServiceLocator;
import org.bay.jspx.demo2.engine.error.BusinessException;
import org.bay.jspx.demo2.engine.model.User;

import eg.java.net.web.jspx.engine.annotation.JspxBean;
import eg.java.net.web.jspx.engine.annotation.JspxWebControl;
import eg.java.net.web.jspx.ui.controls.WebControl;
import eg.java.net.web.jspx.ui.controls.html.elements.inputs.Password;
import eg.java.net.web.jspx.ui.pages.ContentPage;

public class AddUser extends ContentPage
{
	boolean success = false;
	boolean error = false;

	@JspxBean(name = "user")
	User user = new User();

	@JspxWebControl
	Password password2 = new Password();

	public void save(WebControl s, String a)
	{
		if (user.getPassword().equals(password2.getValue()))
			ServiceLocator.userService.addUser(user);
		else
			throw new BusinessException("${bundle.form_pass_no_match");
		success = true;
	}

	public boolean getSuccess()
	{
		return success;
	}

	public boolean getError()
	{
		return error;
	}
}
