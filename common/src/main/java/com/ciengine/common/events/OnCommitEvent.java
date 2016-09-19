package com.ciengine.common.events;




import com.ciengine.common.DefaultCIEngineEvent;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class OnCommitEvent  extends DefaultCIEngineEvent
{
	private String gitUrl;
	private String comitId;
	private String branchName;

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
}