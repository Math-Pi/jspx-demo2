package org.bay.jspx.demo2.web.controller.report;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import org.bay.jspx.demo2.engine.ServiceLocator;
import org.bay.jspx.demo2.engine.model.Customer;
import org.bay.jspx.demo2.engine.util.JspxUtil;

import eg.java.net.web.jspx.engine.annotation.JspxBean;
import eg.java.net.web.jspx.engine.annotation.JspxWebControl;
import eg.java.net.web.jspx.engine.util.StringUtility;
import eg.java.net.web.jspx.ui.controls.html.elements.dataitem.DataTable;
import eg.java.net.web.jspx.ui.pages.ContentPage;

public class ReportController extends ContentPage
{
	private static final Logger logger = Logger.getLogger(ReportController.class);

	@JspxBean(scope = JspxBean.REQUEST, name = "cust")
	public Customer customer = new Customer();

	@JspxWebControl(name = "reportTable")
	DataTable reportTable;

	@Override
	protected void pageDispatched()
	{
		reportTable.setSql((String) request.getAttribute(JspxUtil.REPORT_SQL));
		reportTable.setPageIndex(1);
		reportTable.dataBind();
		customer = (Customer) request.getAttribute(JspxUtil.CUSTOMER_BEAN);
	}

	public double getTotalAmount()
	{
		return ServiceLocator.billService.getBillSum((String) request.getAttribute(JspxUtil.REPORT_SQL), "AMOUNT_DEPIT");
	}

	public String getRef()
	{
		long id = -1;
		try
		{
			id = Long.parseLong(request.getParameter("ref"));
		}
		catch (Exception e)
		{
			logger.error("Error in getting ref [" + request.getParameter("ref") + "]");
			return "ﬂ«›… «·√ﬁ”«„";
		}
		return ServiceLocator.lookupService.refrenceDAO.getLookup(id).getName();
	}

	public String getDateFrom()
	{
		String issueDate = request.getParameter("issueDateFrom");
		if (StringUtility.isNullOrEmpty(issueDate))
			issueDate = "01/11/2010";
		return issueDate;
	}

	public String getDateTo()
	{
		String issueDate = request.getParameter("issueDateTo");
		if (StringUtility.isNullOrEmpty(issueDate))
			issueDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		return issueDate;
	}
}
