package org.bay.jspx.demo2.web.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.bay.jspx.demo2.engine.model.User;

public class Security
{
	private static final Logger logger = Logger.getLogger(Security.class);

	public static void AuthenticateUser(HttpServletRequest request)
	{
		logger.info("AuthenticateUser(request=" + request + ") - start");

		User user = getAuthenticatedUser(request);
		if (user == null)
		{
			user = new User();
			user.setId(0);
			user.setName("Jspx Admin");
			request.getSession().setAttribute("user", user);
		}
		Thread.currentThread().setName(user.getName());
		logger.info("AuthenticateUser(HttpServletRequest) - end");
	}

	public static User getAuthenticatedUser(HttpServletRequest request)
	{
		return (User) request.getSession().getAttribute("user");
	}

}
