package org.bay.jspx.demo2.web.controller.admin;

import org.apache.log4j.Logger;

import org.bay.jspx.demo2.engine.ServiceLocator;
import org.bay.jspx.demo2.engine.error.BusinessException;
import org.bay.jspx.demo2.engine.error.ErrorCode;
import org.bay.jspx.demo2.engine.model.User;
import org.bay.jspx.demo2.web.util.Security;

import eg.java.net.web.jspx.engine.annotation.JspxBean;
import eg.java.net.web.jspx.ui.controls.WebControl;
import eg.java.net.web.jspx.ui.pages.ContentPage;

public class ViewUser extends ContentPage
{
	private static final Logger logger = Logger.getLogger(ViewUser.class);

	@JspxBean(scope = JspxBean.REQUEST, name = "user", dateformat = "dd/MM/yyyy")
	public User user = new User();

	@Override
	protected void pageLoaded()
	{
		if (request.getQueryString().contains("id="))
		{
			try
			{
				user = ServiceLocator.userService.getUserById(Long.parseLong(request.getParameter("id")));
			}
			catch (Exception e)
			{
				logger.error(e);
				throw new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND);
			}
			if (user == null)
				throw new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND);
		}
		else
			throw new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND);
	}

	public boolean getIsDeleted()
	{
		return user.getDeleted() == 1;
	}

	public boolean getIsActive()
	{
		return !getIsDeleted();
	}

	public void deleteUser(WebControl sender, String arg)
	{
		ServiceLocator.userService.deleteUser(user, Security.getAuthenticatedUser(request));
	}
}
