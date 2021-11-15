package org.bay.jspx.demo2.engine.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.apache.log4j.Logger;
import org.bay.jspx.demo2.engine.dao.CustomerDAO;

import sun.misc.BASE64Encoder;

public class JspxUtil
{
	private static final Logger logger = Logger.getLogger(JspxUtil.class);

	private JspxUtil()
	{
	}

	public static final String REPORT_SQL = "REPORT_SQL";
	public static final String CUSTOMER_BEAN = "CUSTOMER_BEAN";
	public static final String RATIO = "RATIO";

	public static byte[] getByteArrayFromInputStream(InputStream inputStream) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[4000];
		while (true)
		{
			int dataSize = inputStream.read(buf);
			if (dataSize == -1)
				break;
			baos.write(buf, 0, dataSize);
		}
		inputStream.close();
		CustomerDAO.logger.info("got file blob sucessfully");
		return baos.toByteArray();
	}

	public static String hashPassword(String password)
	{
		final String ENCODING = "UTF8";
		String result = password;
		String algorithm = "SHA";
		MessageDigest md = null;
		if (password != null)
		{
			try
			{
				md = MessageDigest.getInstance(algorithm, "SUN");
			}
			catch (NoSuchAlgorithmException e)
			{
				logger.error("couldn't hash password: " + e);
			}
			catch (NoSuchProviderException e)
			{
				logger.error("couldn't hash password: " + e);
			}
			byte[] bytes = null;
			try
			{
				bytes = password.getBytes(ENCODING);
			}
			catch (UnsupportedEncodingException e1)
			{
				logger.error("Exception while hashing the password... " + e1);
			}
			md.update(bytes);
			byte[] raw = md.digest();
			result = (new BASE64Encoder()).encode(raw);
		}
		return result;
	}

}
