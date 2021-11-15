package org.bay.jspx.demo2.engine.model;

public class Lookup
{

	long id;
	String name;
	public Lookup()
	{
		super();
		
	}
	public Lookup(long id, String name)
	{
		super();
		this.id = id;
		this.name = name;
	}
	public Lookup(long id)
	{
		super();
		this.id = id;
	}
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	
}
