package org.bay.jspx.demo2.web.controller.account;

import org.apache.log4j.Logger;

import org.bay.jspx.demo2.engine.ServiceLocator;
import org.bay.jspx.demo2.engine.error.BusinessException;
import org.bay.jspx.demo2.engine.error.ErrorCode;
import org.bay.jspx.demo2.engine.model.Bill;
import org.bay.jspx.demo2.engine.model.Customer;
import org.bay.jspx.demo2.engine.model.User;
import org.bay.jspx.demo2.web.util.Security;

import eg.java.net.web.jspx.engine.annotation.JspxBean;
import eg.java.net.web.jspx.engine.util.StringUtility;
import eg.java.net.web.jspx.ui.controls.WebControl;
import eg.java.net.web.jspx.ui.pages.ContentPage;

public class AddNewBill extends ContentPage
{
	private static final Logger logger = Logger.getLogger(AddNewBill.class);

	boolean editBill = false;
	boolean showCust = false;

	boolean success = false;
	boolean error = false;

	@JspxBean(scope = JspxBean.REQUEST, name = "cust")
	public Customer customer = new Customer();

	@JspxBean(scope = JspxBean.REQUEST, name = "bill")
	public Bill bill = new Bill();

	@Override
	protected void pageLoaded()
	{
		//		if (!isPostBack)
		{
			showCust = !StringUtility.isNullOrEmpty(request.getQueryString()) && request.getQueryString().contains("custId=");
			editBill = !StringUtility.isNullOrEmpty(request.getQueryString()) && request.getQueryString().contains("id=");
			if (editBill)
			{
				try
				{
					bill = ServiceLocator.billService.getBill(Long.parseLong(request.getParameter("id")));
					customer.setId(bill.getCustomerId());
					updateCustomer(null, null);
				}
				catch (BusinessException e)
				{
					throw e;
				}
				catch (Exception e)
				{
					logger.error(e);
					throw new BusinessException(ErrorCode.BILL_NOT_FOUND);
				}
			}
			else
			{
				if (showCust)
				{
					try
					{
						customer.setId(Long.parseLong(request.getParameter("custId")));
						updateCustomer(null, null);

					}
					catch (Exception e)
					{
						logger.error(e);
					}
				}
			}
		}
		//		else
		//			showCust = true;
	}

	public void saveBill(WebControl s, String a)
	{
		logger.info("custmer id in request :" + request.getParameter("custId"));
		logger.info("custmer id in request :" + customer.getId());
		User user = Security.getAuthenticatedUser(request);
		bill.setCustomerId(customer.getId());
		bill.setUser(user);
		ServiceLocator.billService.addBill(bill);
		success = true;
		bill = new Bill();
	}

	public void updateCustomer(WebControl s, String a)
	{
		if (customer.getId() != null)
			customer = ServiceLocator.customerService.getCustomer(customer.getId());
		showCust = customer != null && customer.getId() != null && customer.getDeleted() == 0;
	}

	public String getNavTitle()
	{
		if (editBill)
			return " ⁄œÌ· »Ì«‰«  ›« Ê—…";
		return "≈÷«›… ›« Ê—… ÃœÌœ";
	}

	public boolean getIsEditBill()
	{
		return editBill;
	}

	public boolean getIsNewBill()
	{
		return !editBill;
	}

	public boolean getShowCust()
	{
		return showCust;
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
