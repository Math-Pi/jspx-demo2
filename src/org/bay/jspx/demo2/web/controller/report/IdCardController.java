package org.bay.jspx.demo2.web.controller.report;

import org.apache.log4j.Logger;

import org.bay.jspx.demo2.engine.model.Customer;
import org.bay.jspx.demo2.engine.util.JspxUtil;

import eg.java.net.web.jspx.engine.annotation.JspxBean;
import eg.java.net.web.jspx.ui.pages.ContentPage;

public class IdCardController extends ContentPage
{
	private static final Logger logger = Logger.getLogger(IdCardController.class);

	@JspxBean(scope = JspxBean.REQUEST, name = "cust")
	public Customer customer = new Customer();

	@Override
	protected void pageDispatched()
	{
		customer = (Customer) request.getAttribute(JspxUtil.CUSTOMER_BEAN);
	}
}
