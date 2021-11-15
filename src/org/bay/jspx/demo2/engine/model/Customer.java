package org.bay.jspx.demo2.engine.model;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Customer
{
	public static int MALE = 1;
	public static int FEMALE = 2;

	Long id;
	String passportNum;
	String name;
	String phone;
	int gender = 1;
	String accountNum1 = "8";
	String accountNum2 = "80";
	String accountNum3;

	Date creationDate;
	Date birthDate;
	String job;
	Grade grade = new Grade();
	MartialStatus martialStatus = new MartialStatus();

	Address address = new Address();

	byte[] photo;

	String mime;

	int deleted = 0;
	Date delDate;
	String regNum;
	String phone2;

	Long stockShare;

	byte[] passportImg;
	String passImgMime;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getPassportNum()
	{
		return passportNum;
	}

	public void setPassportNum(String passportNum)
	{
		this.passportNum = passportNum;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public int getGender()
	{
		return gender;
	}

	public void setGender(int gender)
	{
		this.gender = gender;
	}

	public String getAccountNum1()
	{
		return accountNum1;
	}

	public void setAccountNum1(String accountNum1)
	{
		this.accountNum1 = accountNum1;
	}

	public String getAccountNum2()
	{
		return accountNum2;
	}

	public void setAccountNum2(String accountNum2)
	{
		this.accountNum2 = accountNum2;
	}

	public String getAccountNum3()
	{
		return new DecimalFormat("0000").format(id);
	}

	public void setAccountNum3(String accountNum3)
	{
		// [Oct 30, 2010 10:03:58 AM] [amr.eladawy] [removed as this value is only obtained from the ID]
	}

	public Date getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	public Date getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(Date birthDate)
	{
		this.birthDate = birthDate;
	}

	public String getJob()
	{
		return job;
	}

	public void setJob(String job)
	{
		this.job = job;
	}

	public Grade getGrade()
	{
		return grade;
	}

	public void setGrade(Grade grade)
	{
		this.grade = grade;
	}

	public MartialStatus getMartialStatus()
	{
		return martialStatus;
	}

	public void setMartialStatus(MartialStatus martialStatus)
	{
		this.martialStatus = martialStatus;
	}

	public Address getAddress()
	{
		return address;
	}

	public void setAddress(Address address)
	{
		this.address = address;
	}

	public byte[] getPhoto()
	{
		return photo;
	}

	public void setPhoto(byte[] photo)
	{
		this.photo = photo;
	}

	public String getMime()
	{
		return mime;
	}

	public void setMime(String mime)
	{
		this.mime = mime;
	}

	public int getDeleted()
	{
		return deleted;
	}

	public void setDeleted(int deleted)
	{
		this.deleted = deleted;
	}

	public Date getDelDate()
	{
		return delDate;
	}

	public void setDelDate(Date delDate)
	{
		this.delDate = delDate;
	}

	public String getRegNum()
	{
		return regNum;
	}

	public void setRegNum(String regNum)
	{
		this.regNum = regNum;
	}

	public String getPhone2()
	{
		return phone2;
	}

	public void setPhone2(String phone2)
	{
		this.phone2 = phone2;
	}

	public Long getStockShare()
	{
		return stockShare;
	}

	public void setStockShare(Long stockShare)
	{
		this.stockShare = stockShare;
	}

	public byte[] getPassportImg()
	{
		return passportImg;
	}

	public void setPassportImg(byte[] passportImg)
	{
		this.passportImg = passportImg;
	}

	public String getPassImgMime()
	{
		return passImgMime;
	}

	public void setPassImgMime(String passImgMime)
	{
		this.passImgMime = passImgMime;
	}

	public String getGenderString()
	{
		if (gender == MALE)
			return "–ﬂ—";
		else if (gender == FEMALE)
			return "√‰ÀÏ";
		return String.valueOf(gender);
	}

	public String getBirthDateString()
	{
		return new SimpleDateFormat("dd/MM/yyyy").format(birthDate);
	}

	public String getAge()
	{
		if (birthDate != null)
		{
			Date now = new Date();
			int age = now.getYear() - birthDate.getYear();
			if (now.getMonth() < birthDate.getMonth() || now.getDay() < now.getDay())
				age--;
			return String.valueOf(age);
		}
		return "";
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Customer [id=");
		builder.append(id);
		builder.append(", passportNum=");
		builder.append(passportNum);
		builder.append(", name=");
		builder.append(name);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", gender=");
		builder.append(gender);
		builder.append(", accountNum1=");
		builder.append(accountNum1);
		builder.append(", accountNum2=");
		builder.append(accountNum2);
		builder.append(", accountNum3=");
		builder.append(accountNum3);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append(", birthDate=");
		builder.append(birthDate);
		builder.append(", job=");
		builder.append(job);
		builder.append(", grade=");
		builder.append(grade);
		builder.append(", martialStatus=");
		builder.append(martialStatus);
		builder.append("]");
		return builder.toString();
	}

}
