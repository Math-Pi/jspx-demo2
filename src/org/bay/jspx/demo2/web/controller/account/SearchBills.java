package org.bay.jspx.demo2.web.controller.account;

import org.bay.jspx.demo2.engine.ServiceLocator;
import org.bay.jspx.demo2.engine.util.JspxUtil;

import eg.java.net.web.jspx.engine.annotation.JspxWebControl;
import eg.java.net.web.jspx.ui.controls.WebControl;
import eg.java.net.web.jspx.ui.controls.html.elements.dataitem.DataTable;
import eg.java.net.web.jspx.ui.pages.ContentPage;

public class SearchBills extends ContentPage
{

	@JspxWebControl(name = "bills")
	DataTable bills;

	public void showResults(WebControl sender, String args)
	{
		bills.setPageIndex(1);
		bills.setRendered(true);
		bills.dataBind();
	}

	public double getTotalAmount()
	{
		return ServiceLocator.billService.getBillSum(bills.getFinalSql(), "AMOUNT_DEPIT");
	}

	public String getShowTotal()
	{
		return bills.getRendered();
	}

	public String getShowPrint()
	{
		if (bills.getRowsCount() > 0)
			return bills.getRendered();
		return "false";
	}

	public void printReport(WebControl sender, String args)
	{
		request.setAttribute(JspxUtil.REPORT_SQL, bills.getFinalSql());
		dispatch("/pages/report/billsReport.jspx");
	}

}
