package org.bay.jspx.demo2.engine.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Helper class for the common database operations.
 * 
 * @author Amr.ElAdawy
 * 
 */
public abstract class DBHelper
{

	public static Logger logger = Logger.getLogger(DBHelper.class);

	public static int executeUpdate(String sql, String errorMsg)
	{
		Connection dbConn = null;
		int result = -1;
		try
		{
			dbConn = DBConnection.getConnection();
			return executeUpdate(sql, errorMsg, dbConn);
		}
		catch (Exception e)
		{
			logger.error(errorMsg + e.toString());
		}
		finally
		{
			DBConnection.closeConnection(dbConn);
		}
		return result;
	}

	public static int executeUpdate(String sql, String errorMsg, Connection dbConn)
	{
		Statement stmt = null;
		int result = -1;
		try
		{
			stmt = dbConn.createStatement();
			logger.info("ExecutingUpdate: " + sql);
			result = stmt.executeUpdate(sql);
			logger.info("database update executed successfully, updated records = " + result);
		}
		catch (Exception e)
		{
			logger.error(errorMsg + e.toString(), e);
		}
		finally
		{
			try
			{
				DBConnection.closeStatementOnly(stmt);
			}
			catch (Exception e)
			{
				logger.error("Could not close statment", e);
			}
		}
		return result;
	}

	public static String formatDate(Date date)
	{
		return new SimpleDateFormat("yyyy-MM-Dd HH:mm:ss").format(date);
	}

	public static double getSum(String sql, String fieldName)
	{
		String finalSQL = "SELECT SUM(" + fieldName + ") FROM (" + sql + ")b";
		Connection dbCon = null;
		try
		{
			dbCon = DBConnection.getConnection();
			logger.info(finalSQL);
			Statement s = dbCon.createStatement();
			ResultSet rs = s.executeQuery(finalSQL);
			if (rs.next())
				return rs.getDouble(1);

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

}
