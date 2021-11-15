package org.bay.jspx.demo2.web.controller.user;

import org.apache.log4j.Logger;

import eg.java.net.web.jspx.ui.pages.ContentPage;

public class Logout extends ContentPage
{
	private static final Logger logger = Logger.getLogger(Logout.class);

	@Override
	protected void pageLoaded()
	{
		logger.info("User is logging Off");
		session.invalidate();
		redirect("/");
	}
}
