package org.bay.jspx.demo2.engine.dao;

import java.sql.Connection;

import org.bay.jspx.demo2.engine.model.Grade;

public class GradeDAO extends LookupDAO
{

	public GradeDAO()
	{
		super("GRADE");
	}

	@Override
	public Grade getLookup(long id, Connection connection)
	{
		Grade grade = new Grade(id);
		loadLookup(grade, connection);
		return grade;
	}

}
