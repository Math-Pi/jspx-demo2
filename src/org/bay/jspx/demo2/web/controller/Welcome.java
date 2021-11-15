package org.bay.jspx.demo2.web.controller;

import org.apache.log4j.Logger;

import eg.java.net.web.jspx.engine.annotation.JspxWebControl;
import eg.java.net.web.jspx.ui.controls.WebControl;
import eg.java.net.web.jspx.ui.controls.html.elements.inputs.TextBox;
import eg.java.net.web.jspx.ui.pages.ContentPage;

public class Welcome extends ContentPage
{
	private static final Logger logger = Logger.getLogger(Welcome.class);

	@JspxWebControl
	TextBox barCode;

	public void goToCustomer(WebControl s, String a)
	{
		logger.info("The Barcode is =" + barCode.getValue());

		String[] parts = barCode.getValue().split("-");
		if (parts != null && parts.length == 3)
		{
			try
			{
				logger.info("The parts[2]=" + parts[2]);
				redirect("/pages/customers/viewCustomer.jspx?id=" + Long.parseLong(parts[2]));
			}
			catch (Exception e)
			{
				logger.error(e);
			}
		}
	}
}
