package main.java.com.ciengine.master.listeners.impl.oncommit;

import main.java.com.ciengine.common.EnvironmentVariables;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class OnCommitRule
{
	private String forModules;
	private String forBranches;
	private String applyList;
	private String dockerImageId;
	private EnvironmentVariables environmentVariables;

	public String getForModules()
	{
		return forModules;
	}

	public void setForModules(String forModules)
	{
		this.forModules = forModules;
	}

	public String getForBranches()
	{
		return forBranches;
	}

	public void setForBranches(String forBranches)
	{
		this.forBranches = forBranches;
	}

	public String getApplyList()
	{
		return applyList;
	}

	public void setApplyList(String applyList)
	{
		this.applyList = applyList;
	}

	public String getDockerImageId()
	{
		return dockerImageId;
	}

	public void setDockerImageId(String dockerImageId)
	{
		this.dockerImageId = dockerImageId;
	}

	public EnvironmentVariables getEnvironmentVariables()
	{
		return environmentVariables;
	}

	public void setEnvironmentVariables(EnvironmentVariables environmentVariables)
	{
		this.environmentVariables = environmentVariables;
	}
}
