package com.ciengine.master.model;

import com.ciengine.common.EnvironmentVariables;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by emekhanikov on 13.09.2016.
 */
@Entity(name = "release")
public class ReleaseModel
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@GeneratorType()
	private int id;

	private String moduleNameToRelease;
	private String mergeFromCommitId; // commit it (e.g. on develop branch) from it merge to release.
	private String releaseBranchName; // e.g. release/2.0

	private String goingToRelease; // list of modules. Used by list, if it see module depends on some modu;es from list
	private String applyList;
	private String dockerImageId;
	private String inputParams; // strigified EnvironmentVariables

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModuleNameToRelease() {
		return moduleNameToRelease;
	}

	public void setModuleNameToRelease(String moduleNameToRelease) {
		this.moduleNameToRelease = moduleNameToRelease;
	}

	public String getMergeFromCommitId() {
		return mergeFromCommitId;
	}

	public void setMergeFromCommitId(String mergeFromCommitId) {
		this.mergeFromCommitId = mergeFromCommitId;
	}

	public String getReleaseBranchName() {
		return releaseBranchName;
	}

	public void setReleaseBranchName(String releaseBranchName) {
		this.releaseBranchName = releaseBranchName;
	}

	public String getGoingToRelease() {
		return goingToRelease;
	}

	public void setGoingToRelease(String goingToRelease) {
		this.goingToRelease = goingToRelease;
	}

	public String getApplyList() {
		return applyList;
	}

	public void setApplyList(String applyList) {
		this.applyList = applyList;
	}

	public String getDockerImageId() {
		return dockerImageId;
	}

	public void setDockerImageId(String dockerImageId) {
		this.dockerImageId = dockerImageId;
	}

	public String getInputParams() {
		return inputParams;
	}

	public void setInputParams(String inputParams) {
		this.inputParams = inputParams;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ReleaseModel that = (ReleaseModel) o;

		if (id != that.id) return false;
		if (moduleNameToRelease != null ? !moduleNameToRelease.equals(that.moduleNameToRelease) : that.moduleNameToRelease != null)
			return false;
		if (mergeFromCommitId != null ? !mergeFromCommitId.equals(that.mergeFromCommitId) : that.mergeFromCommitId != null)
			return false;
		if (releaseBranchName != null ? !releaseBranchName.equals(that.releaseBranchName) : that.releaseBranchName != null)
			return false;
		if (goingToRelease != null ? !goingToRelease.equals(that.goingToRelease) : that.goingToRelease != null)
			return false;
		if (applyList != null ? !applyList.equals(that.applyList) : that.applyList != null) return false;
		if (dockerImageId != null ? !dockerImageId.equals(that.dockerImageId) : that.dockerImageId != null)
			return false;
		return inputParams != null ? inputParams.equals(that.inputParams) : that.inputParams == null;

	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (moduleNameToRelease != null ? moduleNameToRelease.hashCode() : 0);
		result = 31 * result + (mergeFromCommitId != null ? mergeFromCommitId.hashCode() : 0);
		result = 31 * result + (releaseBranchName != null ? releaseBranchName.hashCode() : 0);
		result = 31 * result + (goingToRelease != null ? goingToRelease.hashCode() : 0);
		result = 31 * result + (applyList != null ? applyList.hashCode() : 0);
		result = 31 * result + (dockerImageId != null ? dockerImageId.hashCode() : 0);
		result = 31 * result + (inputParams != null ? inputParams.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ReleaseModel{" +
				"id=" + id +
				", moduleNameToRelease='" + moduleNameToRelease + '\'' +
				", mergeFromCommitId='" + mergeFromCommitId + '\'' +
				", releaseBranchName='" + releaseBranchName + '\'' +
				", goingToRelease='" + goingToRelease + '\'' +
				", applyList='" + applyList + '\'' +
				", dockerImageId='" + dockerImageId + '\'' +
				", inputParams='" + inputParams + '\'' +
				'}';
	}
}
