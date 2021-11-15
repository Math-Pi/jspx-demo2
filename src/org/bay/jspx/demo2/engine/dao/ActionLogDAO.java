package org.bay.jspx.demo2.engine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import org.bay.jspx.demo2.engine.db.DBConnection;
import org.bay.jspx.demo2.engine.model.ActionLog;

public class ActionLogDAO
{
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(ActionLogDAO.class);

	public void saveActionLog(ActionLog log, Connection dbCon)
	{
		PreparedStatement s = null;
		try
		{
			dbCon = DBConnection.getConnection();
			String sql = "INSERT INTO ACTION_LOG (USER_ID,ACTION) VALUES (?,?)";
			logger.info(sql);
			s = dbCon.prepareStatement(sql);
			s.setLong(1, log.getUserId());
			s.setString(2, log.getAction());
			int rs = s.executeUpdate();
			logger.info("Logged Action count =" + rs);
		}
		catch (Exception e)
		{
			logger.error("", e);
		}
		finally
		{
			DBConnection.closeStatementOnly(s);
		}
	}
}
