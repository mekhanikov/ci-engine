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
	private String moduleName;

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

	public String getDeployVersion() {
		return deployVersion;
	}

	public void setDeployVersion(String deployVersion) {
		this.deployVersion = deployVersion;
	}

	public String getGitUrl() {
		return gitUrl;
	}

	public void setGitUrl(String gitUrl) {
		this.gitUrl = gitUrl;
	}

	public String getComitId() {
		return comitId;
	}

	public void setComitId(String comitId) {
		this.comitId = comitId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBuildId() {
		return buildId;
	}

	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		OnNewArtifactEvent that = (OnNewArtifactEvent) o;

		if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
		if (artifactId != null ? !artifactId.equals(that.artifactId) : that.artifactId != null) return false;
		if (deployVersion != null ? !deployVersion.equals(that.deployVersion) : that.deployVersion != null)
			return false;
		if (gitUrl != null ? !gitUrl.equals(that.gitUrl) : that.gitUrl != null) return false;
		if (comitId != null ? !comitId.equals(that.comitId) : that.comitId != null) return false;
		if (branchName != null ? !branchName.equals(that.branchName) : that.branchName != null) return false;
		if (buildId != null ? !buildId.equals(that.buildId) : that.buildId != null) return false;
		return moduleName != null ? moduleName.equals(that.moduleName) : that.moduleName == null;

	}

	@Override
	public int hashCode() {
		int result = groupId != null ? groupId.hashCode() : 0;
		result = 31 * result + (artifactId != null ? artifactId.hashCode() : 0);
		result = 31 * result + (deployVersion != null ? deployVersion.hashCode() : 0);
		result = 31 * result + (gitUrl != null ? gitUrl.hashCode() : 0);
		result = 31 * result + (comitId != null ? comitId.hashCode() : 0);
		result = 31 * result + (branchName != null ? branchName.hashCode() : 0);
		result = 31 * result + (buildId != null ? buildId.hashCode() : 0);
		result = 31 * result + (moduleName != null ? moduleName.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "OnNewArtifactEvent{" +
				"groupId='" + groupId + '\'' +
				", artifactId='" + artifactId + '\'' +
				", deployVersion='" + deployVersion + '\'' +
				", gitUrl='" + gitUrl + '\'' +
				", comitId='" + comitId + '\'' +
				", branchName='" + branchName + '\'' +
				", buildId='" + buildId + '\'' +
				", moduleName='" + moduleName + '\'' +
				'}';
	}
}
