package org.bay.jspx.demo2.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bay.jspx.demo2.engine.error.ErrorCode;
import org.bay.jspx.demo2.web.util.Security;

import eg.java.net.web.jspx.engine.RequestHandler;
import eg.java.net.web.jspx.ui.pages.Page;

public class JspxDemoController extends RequestHandler
{

	public JspxDemoController()
	{
		charSet = "windows-1256";
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doServe(request, response, false);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doServe(request, response, true);
	}

	private void doServe(HttpServletRequest request, HttpServletResponse response, boolean post) throws ServletException, IOException
	{
		request.setCharacterEncoding(charSet);

		try
		{
			Security.AuthenticateUser(request);
			if (post)
				super.doPost(request, response);
			else
				super.doGet(request, response);
		}
		catch (Exception e)
		{
			request.setAttribute(ErrorCode.GLOBAL_ERROR, e.getCause().getMessage());
			try
			{
				Page source = (Page) request.getAttribute(PageAttribute);
				if (source != null)
					source.skip();
				RequestHandler.start(request, response, false, "/error.jspx", getServletContext(), source);
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}

	}
}
