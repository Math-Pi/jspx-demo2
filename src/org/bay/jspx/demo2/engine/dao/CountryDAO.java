package org.bay.jspx.demo2.engine.dao;

import java.sql.Connection;

import org.bay.jspx.demo2.engine.model.Country;

public class CountryDAO extends LookupDAO
{

	public CountryDAO()
	{
		super("COUNTRY");
	}

	@Override
	public Country getLookup(long id, Connection connection)
	{
		Country country = new Country(id);
		loadLookup(country, connection);
		return country;
	}
}
