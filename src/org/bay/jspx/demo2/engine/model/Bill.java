package org.bay.jspx.demo2.engine.model;

import java.util.Date;

public class Bill
{
	long id;
	long customerId;
	Date issueDate = new Date();
	double amountCredit;
	Double amountDepit;
	User user;
	Refrence refrence = new Refrence();

	int deleted = 0;
	User delUSer;
	Date delDate;

	String serialNum;

	Date creationDate;

	String comment;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(long customerId)
	{
		this.customerId = customerId;
	}

	public Date getIssueDate()
	{
		return issueDate;
	}

	public void setIssueDate(Date issueDate)
	{
		this.issueDate = issueDate;
	}

	public double getAmountCredit()
	{
		return amountCredit;
	}

	public void setAmountCredit(double amountCredit)
	{
		this.amountCredit = amountCredit;
	}

	public Double getAmountDepit()
	{
		return amountDepit;
	}

	public void setAmountDepit(Double amountDepit)
	{
		this.amountDepit = (double) Math.round(amountDepit);
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public Refrence getRefrence()
	{
		return refrence;
	}

	public void setRefrence(Refrence refrence)
	{
		this.refrence = refrence;
	}

	public int getDeleted()
	{
		return deleted;
	}

	public void setDeleted(int deleted)
	{
		this.deleted = deleted;
	}

	public User getDelUSer()
	{
		return delUSer;
	}

	public void setDelUSer(User delUSer)
	{
		this.delUSer = delUSer;
	}

	public Date getDelDate()
	{
		return delDate;
	}

	public void setDelDate(Date delDate)
	{
		this.delDate = delDate;
	}

	public String getSerialNum()
	{
		return serialNum;
	}

	public void setSerialNum(String serialNum)
	{
		this.serialNum = serialNum;
	}

	public Date getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Bill [id=");
		builder.append(id);
		builder.append(", customerId=");
		builder.append(customerId);
		builder.append(", issueDate=");
		builder.append(issueDate);
		builder.append(", amountCredit=");
		builder.append(amountCredit);
		builder.append(", amountDepit=");
		builder.append(amountDepit);
		builder.append(", user=");
		builder.append(user);
		builder.append(", refrence=");
		builder.append(refrence);
		builder.append(", deleted=");
		builder.append(deleted);
		builder.append(", delUSer=");
		builder.append(delUSer);
		builder.append(", delDate=");
		builder.append(delDate);
		builder.append(", serialNum=");
		builder.append(serialNum);
		builder.append("]");
		return builder.toString();
	}

}
