package org.bay.jspx.demo2.engine.dao;

import java.sql.Connection;

import org.bay.jspx.demo2.engine.model.Job;

public class JobDAO extends LookupDAO
{

	public JobDAO()
	{
		super("JOB");
	}

	@Override
	public Job getLookup(long id, Connection connection)
	{
		Job job = new Job(id);
		loadLookup(job, connection);
		return job;
	}

}
