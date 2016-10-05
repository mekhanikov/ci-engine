package com.ciengine.common.events;




import com.ciengine.common.DefaultCIEngineEvent;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class OnArtifactIsRequiredEvent extends DefaultCIEngineEvent
{
	private String groupId;
	private String artifactId;
	private String version; // wildcarts? more then one?

	// FROM OnComitEvent
	private String gitUrl;
	//private String comitId; // required?
	private String branchName;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getGitUrl() {
		return gitUrl;
	}

	public void setGitUrl(String gitUrl) {
		this.gitUrl = gitUrl;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		OnArtifactIsRequiredEvent that = (OnArtifactIsRequiredEvent) o;

		if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
		if (artifactId != null ? !artifactId.equals(that.artifactId) : that.artifactId != null) return false;
		if (version != null ? !version.equals(that.version) : that.version != null) return false;
		if (gitUrl != null ? !gitUrl.equals(that.gitUrl) : that.gitUrl != null) return false;
		return branchName != null ? branchName.equals(that.branchName) : that.branchName == null;

	}

	@Override
	public int hashCode() {
		int result = groupId != null ? groupId.hashCode() : 0;
		result = 31 * result + (artifactId != null ? artifactId.hashCode() : 0);
		result = 31 * result + (version != null ? version.hashCode() : 0);
		result = 31 * result + (gitUrl != null ? gitUrl.hashCode() : 0);
		result = 31 * result + (branchName != null ? branchName.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "OnArtifactIsRequiredEvent{" +
				"groupId='" + groupId + '\'' +
				", artifactId='" + artifactId + '\'' +
				", version='" + version + '\'' +
				", gitUrl='" + gitUrl + '\'' +
				", branchName='" + branchName + '\'' +
				'}';
	}
}
