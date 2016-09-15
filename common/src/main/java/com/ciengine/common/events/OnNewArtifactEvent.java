package com.ciengine.common.events;




import com.ciengine.common.DefaultCIEngineEvent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Created by emekhanikov on 05.09.2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OnNewArtifactEvent  extends DefaultCIEngineEvent
{
	private String groupId;
	private String artifactId;
	private String deployVersion;

	// FROM OnComitEvent
	private String gitUrl;
	private String comitId;
	private String branchName;

	private String buildId;

	//private String time;// ????

	public String getGroupId()
	{
		return groupId;
	}

	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}

	public String getArtifactId()
	{
		return artifactId;
	}

	public void setArtifactId(String artifactId)
	{
		this.artifactId = artifactId;
	}

	public String getDeployVersion()
	{
		return deployVersion;
	}

	public void setDeployVersion(String deployVersion)
	{
		this.deployVersion = deployVersion;
	}

	public String getGitUrl()
	{
		return gitUrl;
	}

	public void setGitUrl(String gitUrl)
	{
		this.gitUrl = gitUrl;
	}

	public String getComitId()
	{
		return comitId;
	}

	public void setComitId(String comitId)
	{
		this.comitId = comitId;
	}

	public String getBranchName()
	{
		return branchName;
	}

	public void setBranchName(String branchName)
	{
		this.branchName = branchName;
	}

	public String getBuildId()
	{
		return buildId;
	}

	public void setBuildId(String buildId)
	{
		this.buildId = buildId;
	}
}
