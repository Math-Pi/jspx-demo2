package org.bay.jspx.demo2.engine;

import org.bay.jspx.demo2.engine.service.BillService;
import org.bay.jspx.demo2.engine.service.CustomerService;
import org.bay.jspx.demo2.engine.service.LogService;
import org.bay.jspx.demo2.engine.service.LookupService;
import org.bay.jspx.demo2.engine.service.UserService;

public class ServiceLocator
{

	private ServiceLocator()
	{
	}

	public static CustomerService customerService = new CustomerService();
	public static UserService userService = new UserService();
	public static LookupService lookupService = new LookupService();
	public static LogService logService = new LogService();
	public static BillService billService = new BillService();

}
