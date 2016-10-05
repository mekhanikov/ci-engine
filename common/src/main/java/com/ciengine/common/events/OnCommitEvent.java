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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		OnCommitEvent that = (OnCommitEvent) o;

		if (gitUrl != null ? !gitUrl.equals(that.gitUrl) : that.gitUrl != null) return false;
		if (comitId != null ? !comitId.equals(that.comitId) : that.comitId != null) return false;
		return branchName != null ? branchName.equals(that.branchName) : that.branchName == null;

	}

	@Override
	public int hashCode() {
		int result = gitUrl != null ? gitUrl.hashCode() : 0;
		result = 31 * result + (comitId != null ? comitId.hashCode() : 0);
		result = 31 * result + (branchName != null ? branchName.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "OnCommitEvent{" +
				"gitUrl='" + gitUrl + '\'' +
				", comitId='" + comitId + '\'' +
				", branchName='" + branchName + '\'' +
				'}';
	}
}
