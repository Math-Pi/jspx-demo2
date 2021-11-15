/**
 * 
 */
package org.bay.jspx.demo2.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.bay.jspx.demo2.web.util.Security;

import eg.java.net.web.jspx.engine.annotation.JspxWebControl;
import eg.java.net.web.jspx.ui.controls.WebControl;
import eg.java.net.web.jspx.ui.controls.attrbs.Attribute;
import eg.java.net.web.jspx.ui.controls.html.GenericWebControl;
import eg.java.net.web.jspx.ui.pages.MasterPage;

/**
 * @author amr.eladawy
 * 
 */
public class Master extends MasterPage
{

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

	@JspxWebControl(name = "mainNav")
	GenericWebControl mainNav = new GenericWebControl("table");
	@JspxWebControl(name = "customerNav")
	GenericWebControl customerNav = new GenericWebControl("table");
	@JspxWebControl(name = "accountNav")
	GenericWebControl accountNav = new GenericWebControl("table");
	@JspxWebControl(name = "adminNav")
	GenericWebControl adminNav = new GenericWebControl("table");
	@JspxWebControl(name = "helpNav")
	GenericWebControl helpNav = new GenericWebControl("table");

	public String getUserName()
	{
		return Security.getAuthenticatedUser(request).getName();
	}

	public void changeLanguage(WebControl sender, String arguments)
	{
		if (getLocale() == null || getLocale().getLanguage().startsWith("en"))
			setLocale(new Locale("ar"));
		else
			setLocale(new Locale("en"));
		localizeMasterPage();
	}

	public String getLang()
	{
		if (getLocale().getLanguage().startsWith("en"))
			return "ÚÑÈí";
		return "English";
	}

	@Override
	protected void pagePreRender()
	{
		Attribute navMouseOut = new Attribute("onmouseout", "this.className='navSec'");
		Attribute navMouseIn = new Attribute("onmouseover", "this.className='navSecHover'");
		String url = request.getRequestURI().toLowerCase();
		if (url.contains("welcome.jspx") || url.endsWith("/jspx-demo2/"))
		{
			mainNav.setCssClass("navSecSel");
		}
		else
		{
			mainNav.setCssClass("navSec");
			mainNav.addAttribute(navMouseIn);
			mainNav.addAttribute(navMouseOut);
		}
		if (url.contains("/customers/"))
		{
			customerNav.setCssClass("navSecSel");
		}
		else
		{
			customerNav.setCssClass("navSec");
			customerNav.addAttribute(navMouseIn);
			customerNav.addAttribute(navMouseOut);
		}
		if (url.contains("/accounts/"))
		{
			accountNav.setCssClass("navSecSel");
		}
		else
		{
			accountNav.setCssClass("navSec");
			accountNav.addAttribute(navMouseIn);
			accountNav.addAttribute(navMouseOut);
		}
		if (url.contains("/admin/"))
		{
			adminNav.setCssClass("navSecSel");
		}
		else
		{
			adminNav.setCssClass("navSec");
			adminNav.addAttribute(navMouseIn);
			adminNav.addAttribute(navMouseOut);
		}
		if (url.contains("/help.jspx"))
		{
			helpNav.setCssClass("navSecSel");
		}
		else
		{
			helpNav.setCssClass("navSec");
			helpNav.addAttribute(navMouseIn);
			helpNav.addAttribute(navMouseOut);
		}

	}

	public String getYear()
	{
		return sdf.format(new Date());
	}
}
