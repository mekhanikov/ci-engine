package main.java.com.ciengine.events.impl;

import main.java.com.ciengine.EnvironmentVariables;
import main.java.com.ciengine.events.CIEngineEvent;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class OnQueueBuildEvent implements CIEngineEvent
{
	private String reasonOfTrigger;
	private EnvironmentVariables environmentVariables; // String? //	Input params (module, branch, commit), fully determined if Status: progress, success, failed. On start on node can be updated. (should have all info to retrigger)

	private String branchName; //	Branch (merge connected to 2 branches, pass both?)
	private String executionList;
	private String dockerImageId;

//	RunId
//	start Time - submit time (or runtime?)
//	End time (duration?)
//	Sha of input params - calculated on fully determined params on runtime
//		module
//	Status (queued, canceled/postponed/not met pre-req, in progress, success, failed)
//	Executed on node id (null if Status: queued) - doesn't makes sense
//	summary
//	log
//	artifacts

	public String getReasonOfTrigger()
	{
		return reasonOfTrigger;
	}

	public void setReasonOfTrigger(String reasonOfTrigger)
	{
		this.reasonOfTrigger = reasonOfTrigger;
	}

	public EnvironmentVariables getEnvironmentVariables()
	{
		return environmentVariables;
	}

	public void setEnvironmentVariables(EnvironmentVariables environmentVariables)
	{
		this.environmentVariables = environmentVariables;
	}

	public String getBranchName()
	{
		return branchName;
	}

	public void setBranchName(String branchName)
	{
		this.branchName = branchName;
	}

	public String getExecutionList()
	{
		return executionList;
	}

	public void setExecutionList(String executionList)
	{
		this.executionList = executionList;
	}

	public String getDockerImageId()
	{
		return dockerImageId;
	}

	public void setDockerImageId(String dockerImageId)
	{
		this.dockerImageId = dockerImageId;
	}
}
