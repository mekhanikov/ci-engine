package com.ciengine.master.facades;

/**
 * Created by emekhanikov on 03.10.2016.
 */
public class Release {

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
