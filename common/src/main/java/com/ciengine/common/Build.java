package com.ciengine.common;

import java.util.List;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class Build
{
	private long id;
	private long startTimestamp;
	private long endTimestamp;
	private long duration;
	private String reasonOfTrigger;
	private EnvironmentVariables environmentVariables; // 	Input params (module, branch, commit), fully determined if Status: progress, success, failed. On start on node can be updated. (should have all info to retrigger)
	private String sha; // Sha of input params - calculated on fully determined params on runtime
	private String module;
	private String branch;
	private String executionList;
	private String dockerImageId;
	private BuildStatus status;// (queued, canceled/postponed/not met pre-req, in progress, success, failed)
	private Node executedOnNode;
	private String summary;
	private String log;
	private List<Artefact> artifacts;


	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getStartTimestamp()
	{
		return startTimestamp;
	}

	public void setStartTimestamp(long startTimestamp)
	{
		this.startTimestamp = startTimestamp;
	}

	public long getEndTimestamp()
	{
		return endTimestamp;
	}

	public void setEndTimestamp(long endTimestamp)
	{
		this.endTimestamp = endTimestamp;
	}

	public long getDuration()
	{
		return duration;
	}

	public void setDuration(long duration)
	{
		this.duration = duration;
	}

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

	public String getSha()
	{
		return sha;
	}

	public void setSha(String sha)
	{
		this.sha = sha;
	}

	public String getModule()
	{
		return module;
	}

	public void setModule(String module)
	{
		this.module = module;
	}

	public String getBranch()
	{
		return branch;
	}

	public void setBranch(String branch)
	{
		this.branch = branch;
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

	public BuildStatus getStatus()
	{
		return status;
	}

	public void setStatus(BuildStatus status)
	{
		this.status = status;
	}

	public Node getExecutedOnNode()
	{
		return executedOnNode;
	}

	public void setExecutedOnNode(Node executedOnNode)
	{
		this.executedOnNode = executedOnNode;
	}

	public String getSummary()
	{
		return summary;
	}

	public void setSummary(String summary)
	{
		this.summary = summary;
	}

	public String getLog()
	{
		return log;
	}

	public void setLog(String log)
	{
		this.log = log;
	}

	public List<Artefact> getArtifacts()
	{
		return artifacts;
	}

	public void setArtifacts(List<Artefact> artifacts)
	{
		this.artifacts = artifacts;
	}
}
