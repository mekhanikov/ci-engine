package com.ciengine.master.facades;

/**
 * Created by emekhanikov on 15.12.2016.
 */
public class EnvironmentData {
    private String forModules;
    private String forBranches;
    private String applyList;
    private String dockerImageId;
    private String environmentVariables;

    public EnvironmentData(String forModules, String forBranches, String applyList, String dockerImageId, String environmentVariables) {
        this.forModules = forModules;
        this.forBranches = forBranches;
        this.applyList = applyList;
        this.dockerImageId = dockerImageId;
        this.environmentVariables = environmentVariables;
    }

    public String getForModules() {
        return forModules;
    }

    public void setForModules(String forModules) {
        this.forModules = forModules;
    }

    public String getForBranches() {
        return forBranches;
    }

    public void setForBranches(String forBranches) {
        this.forBranches = forBranches;
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

    public String getEnvironmentVariables() {
        return environmentVariables;
    }

    public void setEnvironmentVariables(String environmentVariables) {
        this.environmentVariables = environmentVariables;
    }
}
