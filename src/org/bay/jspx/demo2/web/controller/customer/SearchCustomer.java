/**
 * 
 */
package org.bay.jspx.demo2.web.controller.customer;

import org.bay.jspx.demo2.engine.util.JspxUtil;

import eg.java.net.web.jspx.engine.annotation.JspxWebControl;
import eg.java.net.web.jspx.ui.controls.WebControl;
import eg.java.net.web.jspx.ui.controls.html.elements.dataitem.DataTable;
import eg.java.net.web.jspx.ui.pages.ContentPage;

/**
 * @author Amr.ElAdawy
 *
 */
public class SearchCustomer extends ContentPage
{
	@JspxWebControl(name = "customersList")
	DataTable customersList;

	public void showResults(WebControl sender, String args)
	{
		customersList.setPageIndex(1);
		customersList.setRendered(true);
		customersList.dataBind();
	}

	public String getShowPrint()
	{
		if (customersList.getRowsCount() > 0)
			return customersList.getRendered();
		return "false";
	}

	public void printReport(WebControl sender, String args)
	{
		request.setAttribute(JspxUtil.REPORT_SQL, customersList.getFinalSql());
		dispatch("/pages/report/searchCustomersReport.jspx");
	}

}
