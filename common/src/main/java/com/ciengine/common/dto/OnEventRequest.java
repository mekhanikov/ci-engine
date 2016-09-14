package com.ciengine.common.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Created by emekhanikov on 14.09.2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OnEventRequest
{
//	@XmlAttribute(name = "sss")

	private String inputParams;
	private String moduleName;
	private String branchName;
	private String executionList;
	private String dockerImageId;
	private String nodeId;//Executed on node id (null if Status: queued) - doesn't makes sense
	private String reasonOfTrigger;


	public String getInputParams()
	{
		return inputParams;
	}

	public void setInputParams(String inputParams)
	{
		this.inputParams = inputParams;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
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

	public String getNodeId()
	{
		return nodeId;
	}

	public void setNodeId(String nodeId)
	{
		this.nodeId = nodeId;
	}

	public String getReasonOfTrigger()
	{
		return reasonOfTrigger;
	}

	public void setReasonOfTrigger(String reasonOfTrigger)
	{
		this.reasonOfTrigger = reasonOfTrigger;
	}
}
