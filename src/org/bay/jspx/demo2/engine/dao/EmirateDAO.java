package org.bay.jspx.demo2.engine.dao;

import java.sql.Connection;

import org.bay.jspx.demo2.engine.model.Emirate;

public class EmirateDAO extends LookupDAO
{

	public EmirateDAO()
	{
		super("EMIRATE");
	}

	@Override
	public Emirate getLookup(long id, Connection dbCon)
	{
		Emirate emirate = new Emirate(id);
		loadLookup(emirate, dbCon);
		return emirate;
	}

}
