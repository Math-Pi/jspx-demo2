/**
 * 
 */
package org.bay.jspx.demo2.engine.dao;

import java.sql.Connection;

import org.apache.log4j.Logger;

import org.bay.jspx.demo2.engine.db.DBConnection;
import org.bay.jspx.demo2.engine.model.Refrence;

/**
 * @author amr.eladawy
 * 
 */
public class RefrenceDAO extends LookupDAO
{
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(RefrenceDAO.class);

	/**
	 * @param tableName
	 */
	public RefrenceDAO()
	{
		super("REFRENCE");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bay.jspx.demo2.engine.dao.LookupDAO#getLookup(long, java.sql.Connection)
	 */
	@Override
	public Refrence getLookup(long id, Connection connection)
	{
		Refrence refrence = new Refrence(id);
		loadLookup(refrence, connection);
		return refrence;
	}

	public Refrence getLookup(long id)
	{
		Refrence refrence = new Refrence(id);
		Connection connection = null;
		try
		{
			connection = DBConnection.getConnection();
			loadLookup(refrence, connection);
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			DBConnection.closeConnection(connection);
		}
		return refrence;
	}

}
