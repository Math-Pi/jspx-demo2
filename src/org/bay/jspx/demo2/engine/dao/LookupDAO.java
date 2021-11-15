package org.bay.jspx.demo2.engine.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import org.bay.jspx.demo2.engine.model.Lookup;

public abstract class LookupDAO
{
	private static final Logger logger = Logger.getLogger(LookupDAO.class);

	String tableName;

	public LookupDAO(String tableName)
	{
		this.tableName = tableName;
	}

	public abstract Lookup getLookup(long id, Connection connection);

	protected void loadLookup(Lookup lookup, Connection connection)
	{
		logger.info("loadLookup(long, Lookup, Connection) - start");

		String sql = "SELECT * FROM " + tableName + " WHERE ID =" + lookup.getId();

		Statement s = null;
		try
		{
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if (rs.next())
				lookup.setName(rs.getString("name"));
		}
		catch (Exception e)
		{
			logger.warn("loadLookup(long, Lookup, Connection) - exception ignored", e);

		}

		logger.info("loadLookup(long, Lookup, Connection) - end");
	}
}
