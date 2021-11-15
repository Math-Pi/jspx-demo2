package org.bay.jspx.demo2.web.controller.customer;

import org.apache.log4j.Logger;

import org.bay.jspx.demo2.engine.ServiceLocator;
import org.bay.jspx.demo2.engine.error.BusinessException;
import org.bay.jspx.demo2.engine.error.ErrorCode;
import org.bay.jspx.demo2.engine.model.Customer;
import org.bay.jspx.demo2.engine.model.CustomFile;
import org.bay.jspx.demo2.engine.util.JspxUtil;
import org.bay.jspx.demo2.web.util.Security;

import eg.java.net.web.jspx.engine.annotation.JspxBean;
import eg.java.net.web.jspx.engine.annotation.JspxWebControl;
import eg.java.net.web.jspx.engine.util.StringUtility;
import eg.java.net.web.jspx.ui.controls.WebControl;
import eg.java.net.web.jspx.ui.controls.html.elements.dataitem.DataTable;
import eg.java.net.web.jspx.ui.pages.ContentPage;

public class ViewCustomer extends ContentPage
{
	private static final Logger logger = Logger.getLogger(ViewCustomer.class);

	@JspxBean(scope = JspxBean.REQUEST, name = "cust", dateformat = "dd/MM/yyyy")
	public Customer customer = new Customer();

	@JspxWebControl(name = "customerBills")
	DataTable customersList;

	@Override
	protected void pageLoaded()
	{
		if (request.getQueryString().contains("id="))
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
			throw new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND);

		request.setAttribute("fileName", customer.getName());
	}

	public boolean getIsDeleted()
	{
		return customer.getDeleted() == 1;
	}

	public boolean getIsActive()
	{
		return !getIsDeleted();
	}

	public void deleteUser(WebControl sender, String arg)
	{
		ServiceLocator.customerService.deleteCustomer(customer, Security.getAuthenticatedUser(request));
	}

	public void printId(WebControl sender, String arg)
	{
		//		String fileName = new StringBuilder("CustomerIdCard").append(System.currentTimeMillis()).append(".html").toString();
		//		HashMap<String, Object> map = new HashMap<String, Object>();
		//		map.put("id", customer.getId());
		//		map.put("name", customer.getName());
		//		map.put("stockShare", customer.getStockShare());
		//		map.put("birthDate", customer.getBirthDate());
		//		map.put("photo", customer.getPhoto());
		//		WebUtil.generateReport(this, fileName, new ArrayList<Object>(), map, "/jasper/IDCF.jasper");
		request.setAttribute(JspxUtil.CUSTOMER_BEAN, customer);
		dispatch("/pages/report/IDCF.jspx");
	}

	public void printIdBack(WebControl sender, String arg)
	{
		request.setAttribute(JspxUtil.CUSTOMER_BEAN, customer);
		dispatch("/pages/report/IDCB.jspx");
	}

	public void printIdAll(WebControl sender, String arg)
	{
		request.setAttribute(JspxUtil.CUSTOMER_BEAN, customer);
		dispatch("/pages/report/IDCA.jspx");
	}

	public String getShowPrint()
	{
		if (customersList.getRowsCount() > 0)
			return customersList.getRendered();
		return "false";
	}

	public void printReport(WebControl sender, String args)
	{
		request.setAttribute(JspxUtil.CUSTOMER_BEAN, customer);
		request.setAttribute(JspxUtil.REPORT_SQL, customersList.getFinalSql());
		dispatch("/pages/report/customerDetailsReport.jspx");
	}

	public boolean getShowPassImg()
	{
		return !StringUtility.isNullOrEmpty(customer.getPassImgMime());
	}

	public void downloadPassImg(WebControl sender, String args)
	{
		CustomFile file = ServiceLocator.customerService.getPassportImage(customer.getId());
		if (file != null && !StringUtility.isNullOrEmpty(file.getFileType()))
		{
			writeFile(file.getFileData(), customer.getName(), file.getFileType());
			this.skip();
		}
	}
}
