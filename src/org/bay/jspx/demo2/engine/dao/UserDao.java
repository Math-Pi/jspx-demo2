package org.bay.jspx.demo2.engine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import org.bay.jspx.demo2.engine.ServiceLocator;
import org.bay.jspx.demo2.engine.db.DBConnection;
import org.bay.jspx.demo2.engine.db.DBHelper;
import org.bay.jspx.demo2.engine.model.ActionLog;
import org.bay.jspx.demo2.engine.model.Group;
import org.bay.jspx.demo2.engine.model.User;

public class UserDao
{
	private static final Logger logger = Logger.getLogger(UserDao.class);

	public User getUserByName(String name)
	{
		Connection dbConn = null;
		try
		{
			dbConn = DBConnection.getConnection();
			return getUserByName(name, dbConn);
		}
		catch (Exception e)
		{
		}
		finally
		{
			DBConnection.closeConnection(dbConn);
		}
		return null;
	}

	private User getUserByName(String name, Connection dbConn)
	{
		return getUser(dbConn, "USER_NAME", "'" + name + "'");
	}

	public User getUser(long id)
	{
		Connection dbConn = null;
		try
		{
			dbConn = DBConnection.getConnection();
			return getUser(id, dbConn);
		}
		catch (Exception e)
		{
		}
		finally
		{
			DBConnection.closeConnection(dbConn);
		}
		return null;
	}

	public User getUser(long id, Connection dbConn)
	{
		return getUser(dbConn, "ID", String.valueOf(id));
	}

	private User getUser(Connection dbConn, String colName, String val)
	{
		Statement s = null;
		try
		{
			String sql = "SELECT * FROM DCS.USERS WHERE " + colName + " =" + val;
			logger.info(sql);
			s = dbConn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if (rs.next())
				return parseUser(rs, dbConn);
		}
		catch (Exception e)
		{
			logger.error("", e);
		}
		finally
		{
			DBConnection.closeStatementOnly(s);
		}
		return null;
	}

	private User parseUser(ResultSet rs, Connection dbCon) throws SQLException
	{
		User user = new User();
		user.setId(rs.getLong("ID"));
		user.setName(rs.getString("USER_NAME"));
		user.setCreationDate(rs.getTimestamp("CREATION_DATE"));
		user.setDeleted(rs.getInt("DELETED"));
		user.setDelDate(rs.getTimestamp("DEL_DATE"));
		user.setDelUser(rs.getLong("DEL_USER"));
		user.setGroups(getGroups(user.getId(), dbCon));
		return user;
	}

	private List<Group> getGroups(long userId, Connection dbCon)
	{
		String sql = "select g.* from jspx.groups g, jspx.users u, jspx.user_groups ug where u.id=ug.user_id and g.id=ug.group_id and u.id=" + userId;
		List<Group> groups = new ArrayList<Group>();
		Statement s = null;
		try
		{
			logger.debug(sql);
			s = dbCon.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next())
				groups.add(new Group(rs.getLong("ID"), rs.getString("NAME")));
		}
		catch (Exception e)
		{
			logger.error("", e);
		}
		finally
		{
			DBConnection.closeStatementOnly(s);
		}
		return groups;
	}

	public int deleteCustomer(User userTobeDeleted, User user)
	{
		String sql = "UPDATE DCS.USERS SET DELETED=1, DEL_USER=" + user.getId() + ",DEL_DATE=CURRENT_TIMESTAMP() WHERE ID ="
				+ userTobeDeleted.getId();

		Connection dbCon = null;
		try
		{
			dbCon = DBConnection.getConnection();
			logger.info(sql);
			Statement s = dbCon.createStatement();
			int res = s.executeUpdate(sql);
			ActionLog log = new ActionLog();
			log.setUserId(user.getId());
			log.setAction("Delete user " + userTobeDeleted);
			ServiceLocator.logService.saveActionLog(log, dbCon);
			userTobeDeleted.setDeleted(1);
			userTobeDeleted.setDelDate(new java.util.Date());
			return res;
		}
		catch (Exception e)
		{
			logger.error("", e);
		}
		finally
		{
			DBConnection.closeConnection(dbCon);
		}
		return 0;
	}

	public int changePassword(long userId, String oldPassword, String newPassword)
	{
		logger.info("changePassword(userId=" + userId + ", oldPassword=" + oldPassword + ", newPassword=" + newPassword + ") - start");

		int result = DBHelper.executeUpdate(new StringBuilder("update users set password ='").append(newPassword).append("' where id=")
				.append(userId).append(" and password ='").append(oldPassword).append("'").toString(), "Unable to change user password user Id :"
				+ userId);
		logger.info("changePassword() updating user password result is " + result);
		return result;
	}

	public int addUser(User user)
	{
		Connection dbCon = null;
		try
		{
			dbCon = DBConnection.getConnection();
			String sql = "INSERT INTO USERS (USER_NAME,PASSWORD) VALUES(?,?)";
			logger.debug(sql);
			PreparedStatement s = dbCon.prepareStatement(sql);
			int parameterIndex = 1;
			s.setString(parameterIndex++, user.getName());
			s.setString(parameterIndex++, user.getPassword());

			int res = s.executeUpdate();
			user.setId(getUserByName(user.getName(), dbCon).getId());
			addUserGroups(user.getId(), user.getGroupsString(), dbCon);
			return res;

		}
		catch (Exception e)
		{
			logger.error("", e);
		}
		finally
		{
			DBConnection.closeConnection(dbCon);
		}
		return 0;
	}

	public void addUserGroups(long id, String groups, Connection dbCon)
	{
		String sql = "INSERT INTO USER_GROUPS (USER_ID,GROUP_ID) VALUES(" + id + ",?)";

		PreparedStatement s = null;
		try
		{
			logger.debug(sql);
			s = dbCon.prepareStatement(sql);
			String[] gs = groups.split(",");
			for (int i = 0; i < gs.length; i++)
			{
				s.clearParameters();
				s.setLong(1, Long.parseLong(gs[i]));
				s.executeUpdate();
			}

		}
		catch (Exception e)
		{
			logger.error("", e);
		}
		finally
		{
			DBConnection.closeStatementOnly(s);
		}
	}
}
