package org.bay.jspx.demo2.engine.model;

/**
 *  this is a model for a file.
 *  it includes the file binary data along with meta info.
 * @author Amr.ElAdawy
 *
 */
public class CustomFile
{
	String fileName;
	String mime;
	byte[] fileData;

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getFileType()
	{
		return mime;
	}

	public void setFileType(String mime)
	{
		this.mime = mime;
	}

	public byte[] getFileData()
	{
		return fileData;
	}

	public void setFileData(byte[] fileData)
	{
		this.fileData = fileData;
	}

}
