/**
 * 
 */
package org.bay.jspx.demo2.engine.dao;

import java.sql.Connection;

import org.bay.jspx.demo2.engine.model.MartialStatus;

/**
 * @author amr.eladawy
 * 
 */
public class MartialStatusDAO extends LookupDAO
{

	/**
	 * @param tableName
	 */
	public MartialStatusDAO()
	{
		super("MARTIAL_STATUS");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bay.jspx.demo2.engine.dao.LookupDAO#getLookup(long, java.sql.Connection)
	 */
	@Override
	public MartialStatus getLookup(long id, Connection connection)
	{
		MartialStatus martialStatus = new MartialStatus(id);
		loadLookup(martialStatus, connection);
		return martialStatus;
	}

}
