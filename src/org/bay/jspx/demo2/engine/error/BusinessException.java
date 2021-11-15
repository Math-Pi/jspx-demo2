package org.bay.jspx.demo2.engine.error;

public class BusinessException extends RuntimeException
{

	public BusinessException()
	{
	}

	public BusinessException(String msg)
	{
		super(msg);
	}

	public BusinessException(String msg, Throwable cause)
	{
		super(msg, cause);
	}

	public BusinessException(Throwable cause)
	{
		super(cause);
	}

}
