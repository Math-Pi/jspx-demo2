package org.bay.jspx.demo2.engine.service;

import org.bay.jspx.demo2.engine.dao.EmirateDAO;
import org.bay.jspx.demo2.engine.dao.GradeDAO;
import org.bay.jspx.demo2.engine.dao.GroupDAO;
import org.bay.jspx.demo2.engine.dao.JobDAO;
import org.bay.jspx.demo2.engine.dao.MartialStatusDAO;
import org.bay.jspx.demo2.engine.dao.RefrenceDAO;

public class LookupService
{
	public EmirateDAO emirateDAO = new EmirateDAO();
	public GradeDAO gradeDAO = new GradeDAO();
	public GroupDAO groupDAO = new GroupDAO();
	public JobDAO jobDAO = new JobDAO();
	public RefrenceDAO refrenceDAO = new RefrenceDAO();
	public MartialStatusDAO martialStatusDAO = new MartialStatusDAO();
}
