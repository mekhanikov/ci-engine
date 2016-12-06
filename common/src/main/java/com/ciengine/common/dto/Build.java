package com.ciengine.common.dto;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by emekhanikov on 26.10.2016.
 */
public class Build {

    private int id;

    @XmlAttribute(name = "start_timestamp")
    //@Temporal(TemporalType.DATE)
//	@Temporal(TemporalType.TIMESTAMP)
    private Long startTimestamp;

    @XmlAttribute(name = "end_timestamp")
    //@Temporal(TemporalType.DATE)
    //	@Temporal(TemporalType.TIMESTAMP)
    private Long endTimestamp;

    private String inputParams; // TODO should be TEXT or so.
    private String inputParamsHash;
    private String moduleName;
    private String branchName;// TODO (merge connected to 2 branches, pass both?)
    private String executionList;
    private String dockerImageId;
    private String Status;// TODO  (queued, canceled/postponed/not met pre-req, in progress, success, failed)
    private String nodeId;//Executed on node id (null if Status: queued) - doesn't makes sense
    private String reasonOfTrigger;
    private String summary;
    private String log;
    private String externalId;
    private String statusDescription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Long getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public String getInputParams() {
        return inputParams;
    }

    public void setInputParams(String inputParams) {
        this.inputParams = inputParams;
    }

    public String getInputParamsHash() {
        return inputParamsHash;
    }

    public void setInputParamsHash(String inputParamsHash) {
        this.inputParamsHash = inputParamsHash;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getExecutionList() {
        return executionList;
    }

    public void setExecutionList(String executionList) {
        this.executionList = executionList;
    }

    public String getDockerImageId() {
        return dockerImageId;
    }

    public void setDockerImageId(String dockerImageId) {
        this.dockerImageId = dockerImageId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getReasonOfTrigger() {
        return reasonOfTrigger;
    }

    public void setReasonOfTrigger(String reasonOfTrigger) {
        this.reasonOfTrigger = reasonOfTrigger;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
