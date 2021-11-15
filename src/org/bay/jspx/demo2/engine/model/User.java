package org.bay.jspx.demo2.engine.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User
{

	long id;
	String name;
	String password;
	int deleted;

	Date creationDate;
	Date delDate;
	long delUser;
	List<Group> groups = new ArrayList<Group>();

	String groupsString;

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

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public int getDeleted()
	{
		return deleted;
	}

	public void setDeleted(int deleted)
	{
		this.deleted = deleted;
	}

	public Date getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	public Date getDelDate()
	{
		return delDate;
	}

	public void setDelDate(Date delDate)
	{
		this.delDate = delDate;
	}

	public long getDelUser()
	{
		return delUser;
	}

	public void setDelUser(long delUser)
	{
		this.delUser = delUser;
	}

	public List<Group> getGroups()
	{
		return groups;
	}

	public void setGroups(List<Group> groups)
	{
		this.groups = groups;
	}

	public void setGroupsString(String groupsString)
	{
		this.groupsString = groupsString;
	}

	public String getGroupsNameString()
	{
		StringBuilder sb = new StringBuilder("");
		for (Group g : groups)
		{
			sb.append(g.getName()).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public String getGroupsString()
	{
		return groupsString;
		//		StringBuilder sb = new StringBuilder("");
		//		for (Group g : groups)
		//		{
		//			sb.append(g.getId()).append(",");
		//		}
		//		sb.deleteCharAt(sb.length() - 1);
		//		return sb.toString();
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", password=");
		builder.append(password);
		builder.append(", deleted=");
		builder.append(deleted);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append("]");
		return builder.toString();
	}

}
