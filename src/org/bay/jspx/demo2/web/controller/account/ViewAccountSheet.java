package org.bay.jspx.demo2.web.controller.account;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import org.bay.jspx.demo2.engine.ServiceLocator;
import org.bay.jspx.demo2.engine.error.BusinessException;
import org.bay.jspx.demo2.engine.error.ErrorCode;
import org.bay.jspx.demo2.engine.model.Customer;
import org.bay.jspx.demo2.engine.util.JspxUtil;

import eg.java.net.web.jspx.engine.annotation.JspxBean;
import eg.java.net.web.jspx.engine.annotation.JspxWebControl;
import eg.java.net.web.jspx.ui.controls.WebControl;
import eg.java.net.web.jspx.ui.controls.html.elements.dataitem.DataTable;
import eg.java.net.web.jspx.ui.pages.ContentPage;

public class ViewAccountSheet extends ContentPage
{
	private static final Logger logger = Logger.getLogger(ViewAccountSheet.class);

	@JspxBean(scope = JspxBean.REQUEST, name = "cust")
	public Customer customer = new Customer();

	@JspxWebControl(name = "customerBills")
	DataTable customerBills;

	Double totalAmount;
	String ratio;

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
				throw new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND);
			}
		}
		else
			throw new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND);
		request.setAttribute("fileName", customer.getName());
	}

	public void refresh(WebControl s, String a)
	{
		customerBills.setPageIndex(1);
		customerBills.dataBind();
	}

	public double getTotalAmount()
	{
		if (totalAmount == null)
			totalAmount = ServiceLocator.billService.getBillSum(customerBills.getFinalSql(), "AMOUNT_DEPIT");
		return totalAmount;
	}

	public String getRatio()
	{
		if (ratio == null)
			ratio = new DecimalFormat("##.##").format(getTotalAmount()
					/ ServiceLocator.billService.getAllCustomersBillSum(customerBills.getFinalSql(), customer.getId(), "AMOUNT_DEPIT") * 100)
					+ "%";
		return ratio;
	}

	public String getShowPrint()
	{
		if (customerBills.getRowsCount() > 0)
			return customerBills.getRendered();
		return "false";
	}

	public void printReport(WebControl s, String a)
	{
		request.setAttribute(JspxUtil.CUSTOMER_BEAN, customer);
		request.setAttribute(JspxUtil.RATIO, getRatio());
		request.setAttribute(JspxUtil.REPORT_SQL, customerBills.getFinalSql());
		dispatch("/pages/report/accountSheetReport.jspx");
	}

}
