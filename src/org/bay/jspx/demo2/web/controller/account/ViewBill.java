package org.bay.jspx.demo2.web.controller.account;

import org.apache.log4j.Logger;

import org.bay.jspx.demo2.engine.ServiceLocator;
import org.bay.jspx.demo2.engine.error.BusinessException;
import org.bay.jspx.demo2.engine.error.ErrorCode;
import org.bay.jspx.demo2.engine.model.Bill;
import org.bay.jspx.demo2.engine.model.Customer;
import org.bay.jspx.demo2.web.util.Security;

import eg.java.net.web.jspx.engine.annotation.JspxBean;
import eg.java.net.web.jspx.ui.controls.WebControl;
import eg.java.net.web.jspx.ui.pages.ContentPage;

public class ViewBill extends ContentPage
{
	private static final Logger logger = Logger.getLogger(ViewBill.class);

	@JspxBean(scope = JspxBean.REQUEST, name = "cust")
	public Customer customer = new Customer();

	@JspxBean(scope = JspxBean.REQUEST, name = "bill")
	public Bill bill = new Bill();

	@Override
	protected void pageLoaded()
	{
		if (request.getQueryString().contains("id="))
		{
			try
			{
				bill = ServiceLocator.billService.getBill(Long.parseLong(request.getParameter("id")));
				customer = ServiceLocator.customerService.getCustomer(bill.getCustomerId());
			}
			catch (Exception e)
			{
				logger.error(e);
				throw new BusinessException(ErrorCode.BILL_NOT_FOUND);
			}
		}
		else
			throw new BusinessException(ErrorCode.BILL_NOT_FOUND);
	}

	public boolean getIsDeleted()
	{
		return bill.getDeleted() == 1;
	}

	public boolean getIsActive()
	{
		return !getIsDeleted();
	}

	public void deleteBill(WebControl sender, String arg)
	{
		ServiceLocator.billService.deleteBill(bill, Security.getAuthenticatedUser(request));
	}
}
