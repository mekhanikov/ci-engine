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
public class OnReleaseSubmitedEvent extends DefaultCIEngineEvent
{
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
}
