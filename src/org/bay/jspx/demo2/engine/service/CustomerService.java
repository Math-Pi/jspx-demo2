package org.bay.jspx.demo2.engine.service;

import java.sql.Connection;

import org.bay.jspx.demo2.engine.dao.AddressDAO;
import org.bay.jspx.demo2.engine.dao.CustomerDAO;
import org.bay.jspx.demo2.engine.error.BusinessException;
import org.bay.jspx.demo2.engine.error.ErrorCode;
import org.bay.jspx.demo2.engine.model.Address;
import org.bay.jspx.demo2.engine.model.Customer;
import org.bay.jspx.demo2.engine.model.CustomFile;
import org.bay.jspx.demo2.engine.model.User;

public class CustomerService
{

	CustomerDAO customerDAO = new CustomerDAO();
	AddressDAO addressDAO = new AddressDAO();

	public long getNewCustomerId()
	{
		return customerDAO.getNewCustomerId();
	}

	public boolean isCustomerIdUsed(long id)
	{
		return customerDAO.isCustomerIdUsed(id);
	}

	public int addAddress(Customer customer, Connection dbConn)
	{
		return addressDAO.addAddress(customer, dbConn);
	}

	public int updateAddress(Customer customer, Connection dbConn)
	{
		return addressDAO.updateAddress(customer, dbConn);
	}

	public Address getAddress(long custId, Connection dbCon)
	{
		return addressDAO.getAddress(custId, dbCon);
	}

	public void addCustomer(Customer customer)
	{
		if (isCustomerIdUsed(customer.getId()))
			throw new BusinessException(ErrorCode.CUSTOMER_ALREADY_DEFINED);
		customerDAO.addNewCustomer(customer);
	}

	public void updateCustomer(Customer customer)
	{
		customerDAO.updateCustomer(customer);

	}

	public Customer getCustomer(long id)
	{
		return customerDAO.getCustomer(id);
	}

	public CustomFile getCustomerPhoto(long id)
	{
		return customerDAO.getCustomerImage(id);
	}

	public CustomFile getCustomerBarcodeImage(long id)
	{
		return customerDAO.getCustomerBarcodeImage(id);
	}

	public int deleteCustomer(Customer customer, User user)
	{
		return customerDAO.deleteCustomer(customer, user);
	}

	public int updateCustomerBarcode(byte[] barcode, long id)
	{
		return customerDAO.updateCustomerBarcode(barcode, id);
	}

	public CustomFile getPassportImage(long id)
	{
		return customerDAO.getPassportImage(id);
	}
}
