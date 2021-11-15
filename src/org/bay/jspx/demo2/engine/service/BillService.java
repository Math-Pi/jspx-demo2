package org.bay.jspx.demo2.engine.service;

import org.apache.log4j.Logger;

import org.bay.jspx.demo2.engine.ServiceLocator;
import org.bay.jspx.demo2.engine.dao.BillDAO;
import org.bay.jspx.demo2.engine.db.DBHelper;
import org.bay.jspx.demo2.engine.error.BusinessException;
import org.bay.jspx.demo2.engine.error.ErrorCode;
import org.bay.jspx.demo2.engine.model.Bill;
import org.bay.jspx.demo2.engine.model.Customer;
import org.bay.jspx.demo2.engine.model.User;

public class BillService
{
	private static final Logger logger = Logger.getLogger(BillService.class);

	BillDAO billDAO = new BillDAO();

	public int addBill(Bill bill)
	{
		Customer customer = ServiceLocator.customerService.getCustomer(bill.getCustomerId());
		if (customer.getDeleted() == 1)
			throw new BusinessException(ErrorCode.CUSTOMER_IS_DELETED);
		if (billDAO.checkPrevBill(bill))
			throw new BusinessException(ErrorCode.BILL_ALREADY_EXISIT);
		return billDAO.addBill(bill);
	}

	public Bill getBill(long id)
	{
		return billDAO.getBill(id);
	}

	public int deleteBill(Bill bill, User user)
	{
		return billDAO.deleteBill(bill, user);
	}

	public double getBillSum(String sql, String filedName)
	{
		return DBHelper.getSum(sql, filedName);
	}

	public double getAllCustomersBillSum(String sql, long id, String fieldName)
	{
		return DBHelper.getSum(sql.replace("B.CUSTOMER_ID= " + id, " 1=1"), fieldName);
	}
}
