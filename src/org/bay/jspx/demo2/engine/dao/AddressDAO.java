package org.bay.jspx.demo2.engine.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import org.bay.jspx.demo2.engine.ServiceLocator;
import org.bay.jspx.demo2.engine.db.DBConnection;
import org.bay.jspx.demo2.engine.db.DBHelper;
import org.bay.jspx.demo2.engine.model.Address;
import org.bay.jspx.demo2.engine.model.Customer;

public class AddressDAO
{
	private static final Logger logger = Logger.getLogger(AddressDAO.class);

	public int addAddress(Customer customer, Connection dbConn)
	{
		Address address = customer.getAddress();
		String sql = "INSERT INTO jspx.address (CUSTOMER_ID,COUNTRY_ID,EMIRATE_ID,LOCATION,POST_OFFICE_NUM,HOME) VALUES(" + customer.getId() + ","
				+ address.getCountry().getId() + "," + address.getEmirate().getId() + ",'" + address.getLocation() + "','"
				+ address.getPostOfficeNumber() + "','" + address.getHome() + "')";
		return DBHelper.executeUpdate(sql, "Failed to Insert Address", dbConn);
	}

	public int updateAddress(Customer customer, Connection dbConn)
	{
		Address address = customer.getAddress();
		String sql = "UPDATE jspx.address set COUNTRY_ID=" + address.getCountry().getId() + ",EMIRATE_ID=" + address.getEmirate().getId()
				+ " ,LOCATION='" + address.getLocation() + "' ,POST_OFFICE_NUM='" + address.getPostOfficeNumber() + "' ,HOME='" + address.getHome()
				+ "' where CUSTOMER_ID= " + customer.getId();
		return DBHelper.executeUpdate(sql, "Failed to update Address", dbConn);
	}

	public Address getAddress(long custId, Connection dbCon)
	{
		String sql = "SELECT * FROM ADDRESS WHERE CUSTOMER_ID=" + custId;
		Statement s = null;
		try
		{
			logger.info(sql);
			s = dbCon.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if (rs.next())
				return parseAddress(rs, dbCon);

		}
		catch (Exception e)
		{
			logger.error("", e);
		}
		finally
		{
			DBConnection.closeStatementOnly(s);
		}
		return new Address();
	}

	public Address parseAddress(ResultSet rs, Connection dbCon) throws SQLException
	{
		Address address = new Address();
		address.setEmirate(ServiceLocator.lookupService.emirateDAO.getLookup(rs.getLong("emirate_ID"), dbCon));
		address.setHome(rs.getString("HOME"));
		address.setLocation(rs.getString("LOCATION"));
		address.setPostOfficeNumber(rs.getString("POST_OFFICE_NUM"));
		return address;
	}
}
