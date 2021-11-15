package org.bay.jspx.demo2.engine.service;

import java.sql.Connection;

import org.bay.jspx.demo2.engine.dao.ActionLogDAO;
import org.bay.jspx.demo2.engine.model.ActionLog;

public class LogService
{
	ActionLogDAO actionLogDAO = new ActionLogDAO();

	public void saveActionLog(ActionLog log, Connection dbCon)
	{
		actionLogDAO.saveActionLog(log, dbCon);
	}
}
