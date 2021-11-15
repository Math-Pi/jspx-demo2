/**
 * 
 */
package org.bay.jspx.demo2.web.controller.admin;

import eg.java.net.web.jspx.engine.annotation.JspxWebControl;
import eg.java.net.web.jspx.ui.controls.WebControl;
import eg.java.net.web.jspx.ui.controls.html.elements.dataitem.DataTable;
import eg.java.net.web.jspx.ui.pages.ContentPage;

/**
 * @author Amr.ElAdawy
 *
 */
public class SearchUser extends ContentPage
{
	@JspxWebControl(name = "usersList")
	DataTable usersList;

	public void showResults(WebControl sender, String args)
	{
		usersList.setPageIndex(1);
		usersList.setRendered(true);
		usersList.dataBind();
	}

}
