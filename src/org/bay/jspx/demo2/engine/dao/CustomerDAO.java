package org.bay.jspx.demo2.engine.dao;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.apache.log4j.Logger;

import org.bay.jspx.demo2.engine.ServiceLocator;
import org.bay.jspx.demo2.engine.db.DBConnection;
import org.bay.jspx.demo2.engine.error.BusinessException;
import org.bay.jspx.demo2.engine.error.ErrorCode;
import org.bay.jspx.demo2.engine.model.ActionLog;
import org.bay.jspx.demo2.engine.model.Customer;
import org.bay.jspx.demo2.engine.model.CustomFile;
import org.bay.jspx.demo2.engine.model.User;
import org.bay.jspx.demo2.engine.util.JspxUtil;

public class CustomerDAO
{
	public static final Logger logger = Logger.getLogger(CustomerDAO.class);

	public Customer getCustomer(long id)
	{
		String sql = "SELECT * FROM CUSTOMER WHERE ID = " + id;
		Connection dbCon = null;
		try
		{
			dbCon = DBConnection.getConnection();
			logger.info(sql);
			Statement s = dbCon.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if (rs.next())
			{
				Customer customer = praseCutomer(rs, dbCon);
				customer.setAddress(ServiceLocator.customerService.getAddress(customer.getId(), dbCon));
				return customer;
			}
		}
		catch (Exception e)
		{
			logger.error("Error while getting cuetomer", e);
		}
		finally
		{
			DBConnection.closeConnection(dbCon);
		}
		throw new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND);
	}

	private Customer praseCutomer(ResultSet rs, Connection connection) throws SQLException
	{
		Customer customer = new Customer();
		customer.setId(rs.getLong("ID"));
		customer.setAccountNum1(rs.getString("Account_Num1"));
		customer.setAccountNum2(rs.getString("Account_Num2"));
		customer.setAccountNum3(rs.getString("Account_Num3"));
		customer.setName(rs.getString("Name"));
		customer.setGender(rs.getInt("gender"));
		customer.setPassportNum(rs.getString("PASSPORT_NUM"));
		customer.setPhone(rs.getString("PHONE"));
		customer.setCreationDate(rs.getTimestamp("CREATION_DATE"));
		customer.setBirthDate(rs.getTimestamp("BIRTH_DATE"));
		customer.setJob(rs.getString("JOB"));
		customer.setGrade(ServiceLocator.lookupService.gradeDAO.getLookup(rs.getLong("GRADE"), connection));
		customer.setMartialStatus(ServiceLocator.lookupService.martialStatusDAO.getLookup(rs.getLong("MARTIAL_STATUS"), connection));
		customer.setMime(rs.getString("PHOTO_MIME"));
		customer.setDeleted(rs.getInt("DELETED"));
		customer.setDelDate(rs.getTimestamp("DEL_DATE"));
		customer.setRegNum(rs.getString("REG_NUM"));
		customer.setPhone2(rs.getString("PHONE2"));
		customer.setStockShare(rs.getLong("STOCK_SHARE"));
		customer.setPassImgMime(rs.getString("PASS_IMG_MIME"));
		return customer;
	}

	/**
	 * gets the ID of the next customer
	 * 
	 * @return
	 */
	public long getNewCustomerId()
	{
		Connection dbCon = null;
		try
		{
			dbCon = DBConnection.getConnection();
			String sql = "SELECT MAX(ID)+1 FROM CUSTOMER";
			logger.info(sql);
			Statement s = dbCon.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if (rs.next())
				return rs.getInt(1);

		}
		catch (Exception e)
		{
			logger.error("", e);
		}
		finally
		{
			DBConnection.closeConnection(dbCon);
		}
		return 1;
	}

	public boolean isCustomerIdUsed(long id)

	{
		Connection dbCon = null;
		try
		{
			dbCon = DBConnection.getConnection();
			String sql = "SELECT 1 FROM CUSTOMER WHERE ID = " + id;
			logger.info(sql);
			Statement s = dbCon.createStatement();
			ResultSet rs = s.executeQuery(sql);
			return rs.next();

		}
		catch (Exception e)
		{
			logger.error("", e);
		}
		finally
		{
			DBConnection.closeConnection(dbCon);
		}
		return false;
	}

	public void addNewCustomer(Customer customer)
	{
		logger.info("addNewCustomer(customer=" + customer + ") - start");

		String sql = "INSERT INTO jspx.customer (ID,PASSPORT_NUM,NAME,PHONE,GENDER,ACCOUNT_NUM1,ACCOUNT_NUM2,ACCOUNT_NUM3,BIRTH_DATE,JOB,GRADE,MARTIAL_STATUS,REG_NUM,PHONE2,STOCK_SHARE,CREATION_DATE)"
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		Connection dbConn = null;
		try
		{
			dbConn = DBConnection.getConnection();
			PreparedStatement ps = dbConn.prepareStatement(sql);
			int parameterIndex = 1;
			ps.setLong(parameterIndex++, customer.getId());
			if (customer.getPassportNum() == null || customer.getPassportNum().equals(""))
				ps.setNull(parameterIndex++, Types.VARCHAR);
			else
				ps.setString(parameterIndex++, customer.getPassportNum());

			ps.setString(parameterIndex++, customer.getName());
			ps.setString(parameterIndex++, customer.getPhone());
			ps.setInt(parameterIndex++, customer.getGender());
			ps.setString(parameterIndex++, customer.getAccountNum1());
			ps.setString(parameterIndex++, customer.getAccountNum2());
			ps.setString(parameterIndex++, customer.getAccountNum3());
			if (customer.getBirthDate() != null)
				ps.setDate(parameterIndex++, new Date(customer.getBirthDate().getTime()));
			else
				ps.setNull(parameterIndex++, Types.DATE);
			ps.setString(parameterIndex++, customer.getJob());
			ps.setLong(parameterIndex++, customer.getGrade().getId());
			ps.setLong(parameterIndex++, customer.getMartialStatus().getId());
			ps.setString(parameterIndex++, customer.getRegNum());
			ps.setString(parameterIndex++, customer.getPhone2());
			if (customer.getStockShare() == null)
				ps.setLong(parameterIndex++, 0);
			else
				ps.setLong(parameterIndex++, customer.getStockShare());
			if (customer.getCreationDate() != null)
				ps.setDate(parameterIndex++, new Date(customer.getCreationDate().getTime()));
			else
				ps.setNull(parameterIndex++, Types.DATE);

			int res = ps.executeUpdate();
			if (res == 0)
			{
				logger.warn("Customer is not saved");
				throw new Exception();
			}
			if (customer.getPhoto() != null && customer.getPhoto().length > 0)
			{
				res = updateCustomerImage(customer, dbConn);
				if (res == 0)
				{
					logger.warn("Customer image is not saved");
					throw new Exception();
				}
			}
			if (customer.getPassportImg() != null && customer.getPassportImg().length > 0)
			{
				res = updatePassportImage(customer, dbConn);
				if (res == 0)
				{
					logger.warn("passport image is not saved");
					throw new Exception();
				}
			}
			res = ServiceLocator.customerService.addAddress(customer, dbConn);
			if (res == 0)
			{
				logger.warn("Customer Address is not saved");
				throw new Exception();
			}
		}
		catch (Exception e)
		{
			logger.error("addNewCustomer(customer=" + customer + ") - exception ignored - Customer customer=" + customer + ", Exception e=" + e, e);
			if (e.getMessage().contains("PASSPORT_NUM"))
				throw new BusinessException(ErrorCode.DUPLICATE_PASSPORT);
			throw new BusinessException(ErrorCode.CUSTOMER_NOT_SAVED);
		}
		finally
		{
			DBConnection.closeConnection(dbConn);
		}

		logger.info("addNewCustomer() - end");
	}

	public void updateCustomer(Customer customer)
	{
		logger.info("update(customer=" + customer + ") - start");

		String sql = "UPDATE DCS.CUSTOMER SET PASSPORT_NUM=? ,NAME=? ,PHONE=? ,GENDER=? ,ACCOUNT_NUM1=? ,ACCOUNT_NUM2=? ,ACCOUNT_NUM3=? ,BIRTH_DATE=? ,JOB=? ,GRADE=? ,MARTIAL_STATUS=? ,REG_NUM=? ,PHONE2=? ,STOCK_SHARE=? WHERE ID=?";

		Connection dbConn = null;
		try
		{
			dbConn = DBConnection.getConnection();
			PreparedStatement ps = dbConn.prepareStatement(sql);
			int parameterIndex = 1;
			ps.setString(parameterIndex++, customer.getPassportNum());
			ps.setString(parameterIndex++, customer.getName());
			ps.setString(parameterIndex++, customer.getPhone());
			ps.setInt(parameterIndex++, customer.getGender());
			ps.setString(parameterIndex++, customer.getAccountNum1());
			ps.setString(parameterIndex++, customer.getAccountNum2());
			ps.setString(parameterIndex++, customer.getAccountNum3());
			if (customer.getBirthDate() != null)
				ps.setDate(parameterIndex++, new Date(customer.getBirthDate().getTime()));
			else
				ps.setNull(parameterIndex++, Types.DATE);
			ps.setString(parameterIndex++, customer.getJob());
			ps.setLong(parameterIndex++, customer.getGrade().getId());
			ps.setLong(parameterIndex++, customer.getMartialStatus().getId());
			ps.setString(parameterIndex++, customer.getRegNum());
			ps.setString(parameterIndex++, customer.getPhone2());
			ps.setLong(parameterIndex++, customer.getStockShare());

			ps.setLong(parameterIndex++, customer.getId());
			int res = ps.executeUpdate();
			if (res == 0)
			{
				logger.warn("Customer is not updated");
				throw new Exception();
			}
			if (customer.getPhoto() != null && customer.getPhoto().length > 0)
			{
				res = updateCustomerImage(customer, dbConn);
				if (res == 0)
				{
					logger.warn("Customer image is not saved");
					throw new Exception();
				}
			}
			if (customer.getPassportImg() != null && customer.getPassportImg().length > 0)
			{
				res = updatePassportImage(customer, dbConn);
				if (res == 0)
				{
					logger.warn("passport image is not saved");
					throw new Exception();
				}
			}
			res = ServiceLocator.customerService.updateAddress(customer, dbConn);
			if (res == 0)
			{
				logger.warn("Customer Address is not saved");
				throw new Exception();
			}
		}
		catch (Exception e)
		{
			logger.error("addNewCustomer(customer=" + customer + ") - exception ignored - Customer customer=" + customer + ", Exception e=" + e, e);

			throw new BusinessException(ErrorCode.CUSTOMER_NOT_SAVED);
		}
		finally
		{
			DBConnection.closeConnection(dbConn);
		}

		logger.info("addNewCustomer() - end");
	}

	private int updateCustomerImage(Customer customer, Connection dbConn)
	{
		PreparedStatement s = null;
		try
		{
			String sql = "UPDATE DCS.CUSTOMER SET PHOTO=? ,PHOTO_MIME=? WHERE ID = ?";
			logger.info(sql);
			s = dbConn.prepareStatement(sql);
			int parameterIndex = 1;
			s.setBinaryStream(parameterIndex++, new ByteArrayInputStream(customer.getPhoto()), customer.getPhoto().length);
			s.setString(parameterIndex++, customer.getMime());
			s.setLong(parameterIndex++, customer.getId());
			return s.executeUpdate();

		}
		catch (Exception e)
		{
			logger.error("error saving image", e);
		}
		finally
		{
			DBConnection.closeStatementOnly(s);
		}
		return 0;
	}

	private int updatePassportImage(Customer customer, Connection dbConn)
	{
		PreparedStatement s = null;
		try
		{
			String sql = "UPDATE DCS.CUSTOMER SET PASSPORT_IMG=? ,PASS_IMG_MIME=? WHERE ID = ?";
			logger.info(sql);
			s = dbConn.prepareStatement(sql);
			int parameterIndex = 1;
			s.setBinaryStream(parameterIndex++, new ByteArrayInputStream(customer.getPassportImg()), customer.getPassportImg().length);
			s.setString(parameterIndex++, customer.getPassImgMime());
			s.setLong(parameterIndex++, customer.getId());
			return s.executeUpdate();

		}
		catch (Exception e)
		{
			logger.error("error saving image", e);
		}
		finally
		{
			DBConnection.closeStatementOnly(s);
		}
		return 0;
	}

	public int updateCustomerBarcode(byte[] barcode, long id)
	{
		Connection dbCon = null;
		PreparedStatement s = null;
		try
		{
			dbCon = DBConnection.getConnection();
			String sql = "UPDATE DCS.CUSTOMER SET BAR_CODE=? WHERE ID = ?";
			logger.info(sql);
			s = dbCon.prepareStatement(sql);
			int parameterIndex = 1;
			s.setBinaryStream(parameterIndex++, new ByteArrayInputStream(barcode), barcode.length);
			s.setLong(parameterIndex++, id);
			return s.executeUpdate();

		}
		catch (Exception e)
		{
			logger.error("error saving barecode", e);
		}
		finally
		{
			DBConnection.closeConnection(dbCon);
		}
		return 0;
	}

	public CustomFile getCustomerImage(long id)
	{
		String sql = "SELECT PHOTO,PHOTO_MIME FROM CUSTOMER WHERE ID=" + id;
		return getImage(sql);
	}

	public CustomFile getCustomerBarcodeImage(long id)
	{
		String sql = "SELECT BAR_CODE,'image/jpg' FROM CUSTOMER WHERE ID=" + id;
		return getImage(sql);
	}

	public CustomFile getPassportImage(long id)
	{
		String sql = "SELECT PASSPORT_IMG,PASS_IMG_MIME FROM CUSTOMER WHERE ID=" + id;
		return getImage(sql);
	}

	private CustomFile getImage(String sql)
	{
		CustomFile file = null;

		Connection dbCon = null;
		try
		{
			dbCon = DBConnection.getConnection();
			logger.info(sql);
			Statement s = dbCon.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if (rs.next())
			{
				InputStream is = rs.getBinaryStream(1);
				file = new CustomFile();
				if (is == null)
					return file;
				file.setFileData(JspxUtil.getByteArrayFromInputStream(is));
				file.setFileType(rs.getString(2));
				return file;
			}
		}
		catch (Exception e)
		{
			logger.error("", e);
		}
		finally
		{
			DBConnection.closeConnection(dbCon);
		}
		return file;
	}

	public int deleteCustomer(Customer customer, User user)
	{
		String sql = "UPDATE DCS.CUSTOMER SET DELETED=1, DEL_USER=" + user.getId() + ",DEL_DATE=CURRENT_TIMESTAMP() WHERE ID =" + customer.getId();

		Connection dbCon = null;
		try
		{
			dbCon = DBConnection.getConnection();
			logger.info(sql);
			Statement s = dbCon.createStatement();
			int res = s.executeUpdate(sql);
			ActionLog log = new ActionLog();
			log.setUserId(user.getId());
			log.setAction("Delete Customer " + customer);
			ServiceLocator.logService.saveActionLog(log, dbCon);
			customer.setDeleted(1);
			customer.setDelDate(new java.util.Date());
			return res;
		}
		catch (Exception e)
		{
			logger.error("", e);
		}
		finally
		{
			DBConnection.closeConnection(dbCon);
		}
		return 0;
	}

	public boolean hasBarCode(long id)
	{
		Connection dbCon = null;
		try
		{
			dbCon = DBConnection.getConnection();
			String sql = "select id from jspx.customer where id=" + id;
			logger.debug(sql);
			Statement s = dbCon.createStatement();
			ResultSet rs = s.executeQuery(sql);
			return rs.next();

		}
		catch (Exception e)
		{
			logger.error("", e);
		}
		finally
		{
			DBConnection.closeConnection(dbCon);
		}
		return false;
	}
}
