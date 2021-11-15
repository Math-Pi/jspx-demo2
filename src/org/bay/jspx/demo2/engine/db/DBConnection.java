package org.bay.jspx.demo2.engine.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.bay.jspx.demo2.engine.error.BusinessException;
import org.bay.jspx.demo2.engine.error.ErrorCode;

/**
 * Responsible for:
 * <ol>
 * <li>
 * Initialize Data Source.</li>
 * <li>
 * Create Connection</li>
 * <li>
 * Closing Connection</li>
 * <li>
 * Tracking the number of currently opened connections.</li>
 * </ol>
 * 
 * @author Amr.ElAdawy
 * 
 */
public class DBConnection
{
	private static DataSource myDataSource;

	private static Logger logger = Logger.getLogger(DBConnection.class);

	private static long busyConn = 0;
	private static long totalConn = 0;

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	static
	{
		try
		{
			InitialContext ic = new InitialContext();
			myDataSource = (DataSource) ic.lookup("java:/jspx-demo-DS");
			logger.info("DCS Datasource was loaded : " + myDataSource);
		}
		catch (NamingException ne)
		{
			logger.fatal("NamingException raised in creating dataSource.", ne);
		}
	}

	private static ConcurrentHashMap<Connection, Throwable> connectionMap = new ConcurrentHashMap<Connection, Throwable>();

	public static Connection getConnection() throws SQLException
	{
		if (myDataSource == null)
			throw new BusinessException(ErrorCode.DB_ERROR);
		Connection c = myDataSource.getConnection();
		if (c != null)
			increamentConnections(c);
		else
			throw new BusinessException(ErrorCode.DB_ERROR);
		return c;
	}

	private static void increamentConnections(Connection conn)
	{
		busyConn++;
		// Thread.dumpStack();
		totalConn++;

		connectionMap.put(conn, new Throwable(sdf.format(new Date())));
		if (totalConn % 100 == 0)
			logger.info("Opening DB Connection:total (" + totalConn + ") ############# current:" + busyConn);

	}

	public static void decrementConnections()
	{
		busyConn--;
		// Thread.dumpStack();
		if (totalConn % 100 == 0)
			logger.info("Closing DB Connection:total (" + totalConn + ") ############# current :" + busyConn);

	}

	public static void closeStatementOnly(Statement s)
	{
		try
		{
			if (s != null)
			{
				closeResutlSet(s.getResultSet());
				s.close();
			}
		}
		catch (Exception e)
		{
			logger.warn("Cannot close Statement :" + e.toString());
		}
	}

	public static void close(Statement s, Connection c)
	{
		try
		{
			closeStatementOnly(s);
			closeConnection(c);
		}
		catch (Exception e)
		{
			logger.warn("Cannot close connection :" + e.toString());
		}
	}

	/**
	 * closes the Statement and the Connection.
	 * 
	 * @param s
	 */
	public static void closeStatement(Statement s)
	{
		try
		{
			if (s == null)
				return;
			Connection c = s.getConnection();
			s.close();
			closeConnection(c);
		}
		catch (Exception e)
		{
			logger.warn("closeStatment():Cannot close connection :" + e.toString());
		}
	}

	public static void closeConnection(Connection dbCon)
	{
		try
		{
			if (dbCon != null && !dbCon.isClosed())
			{
				connectionMap.remove(dbCon);
				if (!dbCon.isClosed())
					decrementConnections();
				dbCon.close();
			}
		}
		catch (SQLException e)
		{
			logger.error("closeConnection():Cannot close connection :" + e.toString());
		}
	}

	private static void closeResutlSet(ResultSet rs)
	{
		try
		{
			rs.close();
		}
		catch (Exception e)
		{
		}
	}

	public static void rollbackConnection(Connection connection)
	{
		try
		{
			if (connection != null)
				connection.rollback();
		}
		catch (SQLException e)
		{
			logger.error("rollbackConnection(connection=" + connection + ")", e);
		}
	}

	public static String printOpenConnections(int depth)
	{
		StringBuilder sb = new StringBuilder();
		int i = 1;
		logger.info("THE FOLLOWING CONNECTIONS ARE CURRENTLY OPEN: ");
		for (Iterator<Connection> conn = connectionMap.keySet().iterator(); conn.hasNext();)
		{
			sb.append("==>Connection [" + i + "] ");
			logger.info("Printing Connection [" + i + "]:");
			try
			{
				printCallers(connectionMap.get(conn.next()), sb, depth);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			logger.info("_________________________________________");
			sb.append("<br/><br/><hr/>");
			i++;
		}
		return sb.toString();
	}

	private static void printCallers(Throwable ex, StringBuilder sb, int depth)
	{
		if (null == ex)
		{
			logger.info("Null stack trace reference! Bailing...");
			return;
		}
		sb.append("Since : ").append(ex.getMessage()).append("<br/>");
		logger.info("Since :" + ex.getMessage());
		StackTraceElement[] stackElements = ex.getStackTrace();
		if (depth > stackElements.length)
			depth = stackElements.length;
		for (int i = 0; i < depth; i++)
		{
			logger.info(" \t" + stackElements[i].getLineNumber() + " in " + stackElements[i].getClassName() + "." + stackElements[i].getMethodName()
					+ "()");
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;").append(stackElements[i].getLineNumber()).append(" in ").append(stackElements[i].getClassName())
					.append(".").append(stackElements[i].getMethodName()).append("()<br/>");
		}
	}
}
