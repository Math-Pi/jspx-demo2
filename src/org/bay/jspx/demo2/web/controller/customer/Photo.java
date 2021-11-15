package org.bay.jspx.demo2.web.controller.customer;

import org.apache.log4j.Logger;

import org.bay.jspx.demo2.engine.ServiceLocator;
import org.bay.jspx.demo2.engine.model.Customer;
import org.bay.jspx.demo2.engine.model.CustomFile;
import org.bay.jspx.demo2.engine.util.JspxUtil;

import eg.java.net.web.jspx.ui.pages.Page;

public class Photo extends Page
{
	private static final Logger logger = Logger.getLogger(Photo.class);

	@Override
	protected void pageLoaded()
	{
		try
		{
			long id = Long.parseLong(request.getParameter("id"));
			int type = Integer.parseInt(request.getParameter("gender"));

			CustomFile photo = ServiceLocator.customerService.getCustomerPhoto(id);

			byte[] file = photo.getFileData();

			if (file == null || file.length == 0)
			{
				if (type == Customer.FEMALE)
					file = JspxUtil.getByteArrayFromInputStream(context.getResourceAsStream("/images/prev/f_user.png"));
				else
					file = JspxUtil.getByteArrayFromInputStream(context.getResourceAsStream("/images/prev/m_user.png"));
				writeFile(file, "userPhoto", "image/png");
			}
			else
				writeFile(file, "userPhoto", photo.getFileType());
		}
		catch (Exception e)
		{
			logger.error(e);
		}
	}
}
