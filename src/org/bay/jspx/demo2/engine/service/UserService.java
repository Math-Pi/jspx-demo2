package org.bay.jspx.demo2.engine.service;

import java.sql.Connection;

import org.apache.log4j.Logger;

import org.bay.jspx.demo2.engine.dao.UserDao;
import org.bay.jspx.demo2.engine.error.BusinessException;
import org.bay.jspx.demo2.engine.error.ErrorCode;
import org.bay.jspx.demo2.engine.model.User;
import org.bay.jspx.demo2.engine.util.JspxUtil;

public class UserService
{
	private static final Logger logger = Logger.getLogger(UserService.class);

	private UserDao userDao = new UserDao();

	public User getUserByName(String name)
	{
		return userDao.getUserByName(name);
	}

	public User getUserById(long id, Connection dbCon)
	{
		return userDao.getUser(id, dbCon);
	}

	public User getUserById(long id)
	{
		return userDao.getUser(id);
	}

	public void deleteUser(User user, User authenticatedUser)
	{
		userDao.deleteCustomer(user, authenticatedUser);

	}

	public void changePassword(User user, String oldPass, String newPass)
	{
		String oldPassHash = JspxUtil.hashPassword(oldPass);
		String newPassHash = JspxUtil.hashPassword(newPass);

		logger.info("changePassword(u, oldPass, newPass) - chaning user Password  - user=" + user.getId() + " | " + user.getName());
		int result = userDao.changePassword(user.getId(), oldPassHash, newPassHash);
		if (result == -1)
			throw new BusinessException(ErrorCode.DB_ERROR);
		else if (result == 0)
			throw new BusinessException(ErrorCode.INVALID_PASSWORD);

		logger.info("changePassword() - chaning user Password  Done !!- user=" + user.getId() + " | " + user.getName());
	}

	public void addUser(User user)
	{
		user.setPassword(JspxUtil.hashPassword(user.getPassword()));
		int res = userDao.addUser(user);
		if (res == 0)
			throw new BusinessException(ErrorCode.USER_NOT_SAVED);
	}
}
