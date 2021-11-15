package org.bay.jspx.demo2.engine.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import org.bay.jspx.demo2.engine.ServiceLocator;
import org.bay.jspx.demo2.engine.db.DBConnection;
import org.bay.jspx.demo2.engine.error.BusinessException;
import org.bay.jspx.demo2.engine.error.ErrorCode;
import org.bay.jspx.demo2.engine.model.ActionLog;
import org.bay.jspx.demo2.engine.model.Bill;
import org.bay.jspx.demo2.engine.model.User;

public class BillDAO
{
	private static final Logger logger = Logger.getLogger(BillDAO.class);

	public int addBill(Bill bill)
	{
		Connection dbCon = null;

		try
		{
			dbCon = DBConnection.getConnection();
			int res = addBill(bill, dbCon);
			if (res == 0)
			{
				logger.warn("Bill is not saved");
				throw new BusinessException(ErrorCode.BILL_NOT_SAVED);
			}
			return res;
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			logger.error("", e);
			throw new BusinessException(ErrorCode.DB_ERROR);
		}
		finally
		{
			DBConnection.closeConnection(dbCon);
		}
	}

	public Bill getBill(long id)
	{
		Connection dbCon = null;

		try
		{
			dbCon = DBConnection.getConnection();
			return getBill(id, dbCon);
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			logger.error("", e);
			throw new BusinessException(ErrorCode.DB_ERROR);
		}
		finally
		{
			DBConnection.closeConnection(dbCon);
		}
	}

	public int addBill(Bill bill, Connection dbCon)
	{
		PreparedStatement s = null;
		try
		{
			String sql = "INSERT INTO DCS.BILL (CUSTOMER_ID,ISSUE_DATE,AMOUNT_DEPIT,REF_ID,USER_ID,SERIAL_NUM,COMMENT) VALUES (?,?,?,?,?,?,?)";
			logger.info(sql);
			s = dbCon.prepareStatement(sql);
			int parameterIndex = 1;
			s.setLong(parameterIndex++, bill.getCustomerId());
			s.setDate(parameterIndex++, new Date(bill.getIssueDate().getTime()));
			s.setDouble(parameterIndex++, bill.getAmountDepit());
			s.setLong(parameterIndex++, bill.getRefrence().getId());
			s.setLong(parameterIndex++, bill.getUser().getId());
			s.setString(parameterIndex++, bill.getSerialNum());
			s.setString(parameterIndex++, bill.getComment());

			int res = s.executeUpdate();
			if (res == 0)
			{
				logger.warn("Bill is not saved");
				throw new BusinessException(ErrorCode.BILL_NOT_SAVED);
			}
			return res;
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			logger.error("", e);
			throw new BusinessException(ErrorCode.DB_ERROR);
		}
		finally
		{
			DBConnection.closeStatementOnly(s);
		}
	}

	public Bill getBill(long id, Connection dbCon)
	{
		Statement s = null;
		try
		{
			String sql = "SELECT * FROM DCS.BILL WHERE ID =" + id;
			logger.info(sql);
			s = dbCon.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if (rs.next())
				return parseBill(rs, dbCon);
			throw new BusinessException(ErrorCode.BILL_NOT_FOUND);

		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			logger.error("", e);
			throw new BusinessException(ErrorCode.DB_ERROR);
		}
		finally
		{
			DBConnection.closeStatementOnly(s);
		}
	}

	private Bill parseBill(ResultSet rs, Connection dbCon) throws Exception
	{
		Bill bill = new Bill();
		bill.setId(rs.getLong("ID"));
		bill.setAmountDepit(rs.getDouble("AMOUNT_DEPIT"));
		bill.setCustomerId(rs.getLong("CUSTOMER_ID"));
		bill.setDelDate(rs.getTimestamp("DEL_DATE"));
		bill.setDeleted(rs.getInt("DELETED"));
		bill.setDelUSer(ServiceLocator.userService.getUserById(rs.getLong("DEL_USER"), dbCon));
		bill.setIssueDate(rs.getTime("ISSUE_DATE"));
		bill.setRefrence(ServiceLocator.lookupService.refrenceDAO.getLookup(rs.getLong("REF_ID"), dbCon));
		bill.setUser(ServiceLocator.userService.getUserById(rs.getLong("USER_ID"), dbCon));
		bill.setSerialNum(rs.getString("SERIAL_NUM"));
		bill.setComment(rs.getString("COMMENT"));
		bill.setCreationDate(rs.getDate("creation_date"));
		return bill;
	}

	public int deleteBill(Bill bill, User user)
	{
		String sql = "UPDATE DCS.BILL SET DELETED=1, DEL_USER=" + user.getId() + ",DEL_DATE=CURRENT_TIMESTAMP() WHERE ID =" + bill.getId();

		Connection dbCon = null;
		try
		{
			dbCon = DBConnection.getConnection();
			logger.info(sql);
			Statement s = dbCon.createStatement();
			int res = s.executeUpdate(sql);
			ActionLog log = new ActionLog();
			log.setUserId(user.getId());
			log.setAction("Delete Bill " + bill);
			ServiceLocator.logService.saveActionLog(log, dbCon);
			bill.setDeleted(1);
			bill.setDelDate(new java.util.Date());
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

	/**
	 * checks the 
	 * @param bill
	 * @return
	 */
	public boolean checkPrevBill(Bill bill)
	{
		Connection dbCon = null;
		try
		{
			dbCon = DBConnection.getConnection();
			String sql = "SELECT * FROM DCS.BILL WHERE REF_ID =? AND SERIAL_NUM=?";
			logger.debug(sql);
			PreparedStatement s = dbCon.prepareStatement(sql);
			int parameterIndex = 1;
			s.setLong(parameterIndex++, bill.getRefrence().getId());
			s.setString(parameterIndex++, bill.getSerialNum());
			ResultSet rs = s.executeQuery();
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
