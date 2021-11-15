package org.bay.jspx.demo2.web.controller.customer;

import org.apache.log4j.Logger;
import org.bay.jspx.demo2.engine.ServiceLocator;
import org.bay.jspx.demo2.engine.error.BusinessException;
import org.bay.jspx.demo2.engine.error.ErrorCode;
import org.bay.jspx.demo2.engine.model.Customer;

import eg.java.net.web.jspx.engine.annotation.JspxBean;
import eg.java.net.web.jspx.engine.annotation.JspxWebControl;
import eg.java.net.web.jspx.engine.util.StringUtility;
import eg.java.net.web.jspx.ui.controls.WebControl;
import eg.java.net.web.jspx.ui.controls.html.elements.Label;
import eg.java.net.web.jspx.ui.controls.html.elements.inputs.FileUpload;
import eg.java.net.web.jspx.ui.pages.ContentPage;

public class AddNewCustomer extends ContentPage
{
	private static final Logger logger = Logger.getLogger(AddNewCustomer.class);

	boolean success = false;
	boolean error = false;

	boolean editUser = false;

	@JspxBean(scope = JspxBean.REQUEST, name = "cust")
	public Customer customer = new Customer();

	@JspxWebControl
	public FileUpload customerImg;

	@JspxWebControl
	public FileUpload passportImg;

	@JspxWebControl
	public Label mobileLen;

	@Override
	protected void pageLoaded()
	{
		editUser = !StringUtility.isNullOrEmpty(request.getQueryString()) && request.getQueryString().contains("id=");
		if (!isPostBack)
		{
			if (editUser)
			{
				try
				{

					customer = ServiceLocator.customerService.getCustomer(Long.parseLong(request.getParameter("id")));
				}
				catch (Exception e)
				{
					logger.error(e);
					throw new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND);
				}
			}
			else
				customer.setId(ServiceLocator.customerService.getNewCustomerId());
		}
	}

	public void saveCustomer(WebControl sender, String args)
	{
		if (!StringUtility.isNullOrEmpty(customer.getPhone()) && (customer.getPhone().length() < 9))
		{
			mobileLen.setRendered(true);
			return;
		}
		mobileLen.setRendered(false);
		if (customerImg.getFileData() != null && customerImg.getFileData().length != 0)
		{
			customer.setPhoto(customerImg.getFileData());
			customer.setMime(customerImg.getFileType());
		}
		if (passportImg.getFileData() != null && passportImg.getFileData().length != 0)
		{
			customer.setPassportImg(passportImg.getFileData());
			customer.setPassImgMime(passportImg.getFileType());
		}
		try
		{
			if (editUser)
			{
				customer.setId(Long.parseLong(request.getParameter("id")));
				ServiceLocator.customerService.updateCustomer(customer);
			}
			else
				ServiceLocator.customerService.addCustomer(customer);
			success = true;

		}
		catch (BusinessException e)
		{
			if (e.getMessage().equals(ErrorCode.CUSTOMER_ALREADY_DEFINED))
				error = true;
			else
				throw e;
		}
	}

	public boolean getSuccess()
	{
		return success;
	}

	public boolean getError()
	{
		return error;
	}

	public String getNavTitle()
	{
		if (editUser)
			return "${bundle.editCustomer";
		return "${bundle.newCustomer";
	}

	public boolean getIsEditUser()
	{
		return editUser;
	}

	public boolean getIsNewUser()
	{
		return !editUser;
	}

}
