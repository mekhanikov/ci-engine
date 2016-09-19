package com.ciengine.common;

/**
 * Created by emekhanikov on 05.09.2016.
 */
public class Node
{
	private String user;
	private String password;
	private String host;
	private int port;
	private String rootWorkspace;
	private String id;

	public String getUser()
	{
		return user;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getHost()
	{
		return host;
	}

	public void setHost(String host)
	{
		this.host = host;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public String getRootWorkspace()
	{
		return rootWorkspace;
	}

	public void setRootWorkspace(String rootWorkspace)
	{
		this.rootWorkspace = rootWorkspace;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}
}
