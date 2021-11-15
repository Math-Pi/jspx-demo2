package org.bay.jspx.demo2.engine.model;

public class Address
{

	long customerId;
	Country country = new Country(0);
	Emirate emirate = new Emirate();
	String location;
	String postOfficeNumber;
	String home;

	public long getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(long customerId)
	{
		this.customerId = customerId;
	}

	public Country getCountry()
	{
		return country;
	}

	public void setCountry(Country country)
	{
		this.country = country;
	}

	public Emirate getEmirate()
	{
		return emirate;
	}

	public void setEmirate(Emirate emirate)
	{
		this.emirate = emirate;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getPostOfficeNumber()
	{
		return postOfficeNumber;
	}

	public void setPostOfficeNumber(String postOfficeNumber)
	{
		this.postOfficeNumber = postOfficeNumber;
	}

	public String getHome()
	{
		return home;
	}

	public void setHome(String home)
	{
		this.home = home;
	}
}
