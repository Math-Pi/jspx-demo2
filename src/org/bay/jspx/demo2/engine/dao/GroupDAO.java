package org.bay.jspx.demo2.engine.dao;

import java.sql.Connection;

import org.bay.jspx.demo2.engine.model.Group;

public class GroupDAO extends LookupDAO
{

	public GroupDAO()
	{
		super("COUNTRY");
	}

	@Override
	public Group getLookup(long id, Connection connection)
	{
		Group group = new Group(id);
		loadLookup(group, connection);
		return group;
	}

}
