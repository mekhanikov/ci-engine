package com.ciengine.master.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by emekhanikov on 13.09.2016.
 */
@Entity(name = "build")
public class BuildModel
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@GeneratorType()
	private int id;

	@Column(name = "start_timestamp")
	//@Temporal(TemporalType.DATE)
//	@Temporal(TemporalType.TIMESTAMP)
	private Date startTimestamp;

	@Column(name = "end_timestamp")
	//@Temporal(TemporalType.DATE)
	//	@Temporal(TemporalType.TIMESTAMP)
	private Date endTimestamp;

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
//			artifacts


	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Date getStartTimestamp()
	{
		return startTimestamp;
	}

	public void setStartTimestamp(Date startTimestamp)
	{
		this.startTimestamp = startTimestamp;
	}

	public Date getEndTimestamp()
	{
		return endTimestamp;
	}

	public void setEndTimestamp(Date endTimestamp)
	{
		this.endTimestamp = endTimestamp;
	}

	public String getInputParams()
	{
		return inputParams;
	}

	public void setInputParams(String inputParams)
	{
		this.inputParams = inputParams;
	}

	public String getInputParamsHash()
	{
		return inputParamsHash;
	}

	public void setInputParamsHash(String inputParamsHash)
	{
		this.inputParamsHash = inputParamsHash;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public String getBranchName()
	{
		return branchName;
	}

	public void setBranchName(String branchName)
	{
		this.branchName = branchName;
	}

	public String getExecutionList()
	{
		return executionList;
	}

	public void setExecutionList(String executionList)
	{
		this.executionList = executionList;
	}

	public String getDockerImageId()
	{
		return dockerImageId;
	}

	public void setDockerImageId(String dockerImageId)
	{
		this.dockerImageId = dockerImageId;
	}

	public String getStatus()
	{
		return Status;
	}

	public void setStatus(String status)
	{
		Status = status;
	}

	public String getNodeId()
	{
		return nodeId;
	}

	public void setNodeId(String nodeId)
	{
		this.nodeId = nodeId;
	}

	public String getReasonOfTrigger()
	{
		return reasonOfTrigger;
	}

	public void setReasonOfTrigger(String reasonOfTrigger)
	{
		this.reasonOfTrigger = reasonOfTrigger;
	}

	public String getSummary()
	{
		return summary;
	}

	public void setSummary(String summary)
	{
		this.summary = summary;
	}

	public String getLog()
	{
		return log;
	}

	public void setLog(String log)
	{
		this.log = log;
	}


	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getExternalId() {
		return externalId;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BuildModel that = (BuildModel) o;

		if (id != that.id) return false;
		if (startTimestamp != null ? !startTimestamp.equals(that.startTimestamp) : that.startTimestamp != null)
			return false;
		if (endTimestamp != null ? !endTimestamp.equals(that.endTimestamp) : that.endTimestamp != null) return false;
		if (inputParams != null ? !inputParams.equals(that.inputParams) : that.inputParams != null) return false;
		if (inputParamsHash != null ? !inputParamsHash.equals(that.inputParamsHash) : that.inputParamsHash != null)
			return false;
		if (moduleName != null ? !moduleName.equals(that.moduleName) : that.moduleName != null) return false;
		if (branchName != null ? !branchName.equals(that.branchName) : that.branchName != null) return false;
		if (executionList != null ? !executionList.equals(that.executionList) : that.executionList != null)
			return false;
		if (dockerImageId != null ? !dockerImageId.equals(that.dockerImageId) : that.dockerImageId != null)
			return false;
		if (Status != null ? !Status.equals(that.Status) : that.Status != null) return false;
		if (nodeId != null ? !nodeId.equals(that.nodeId) : that.nodeId != null) return false;
		if (reasonOfTrigger != null ? !reasonOfTrigger.equals(that.reasonOfTrigger) : that.reasonOfTrigger != null)
			return false;
		if (summary != null ? !summary.equals(that.summary) : that.summary != null) return false;
		if (log != null ? !log.equals(that.log) : that.log != null) return false;
		if (externalId != null ? !externalId.equals(that.externalId) : that.externalId != null) return false;
		return statusDescription != null ? statusDescription.equals(that.statusDescription) : that.statusDescription == null;

	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (startTimestamp != null ? startTimestamp.hashCode() : 0);
		result = 31 * result + (endTimestamp != null ? endTimestamp.hashCode() : 0);
		result = 31 * result + (inputParams != null ? inputParams.hashCode() : 0);
		result = 31 * result + (inputParamsHash != null ? inputParamsHash.hashCode() : 0);
		result = 31 * result + (moduleName != null ? moduleName.hashCode() : 0);
		result = 31 * result + (branchName != null ? branchName.hashCode() : 0);
		result = 31 * result + (executionList != null ? executionList.hashCode() : 0);
		result = 31 * result + (dockerImageId != null ? dockerImageId.hashCode() : 0);
		result = 31 * result + (Status != null ? Status.hashCode() : 0);
		result = 31 * result + (nodeId != null ? nodeId.hashCode() : 0);
		result = 31 * result + (reasonOfTrigger != null ? reasonOfTrigger.hashCode() : 0);
		result = 31 * result + (summary != null ? summary.hashCode() : 0);
		result = 31 * result + (log != null ? log.hashCode() : 0);
		result = 31 * result + (externalId != null ? externalId.hashCode() : 0);
		result = 31 * result + (statusDescription != null ? statusDescription.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "BuildModel{" +
				"id=" + id +
				", startTimestamp=" + startTimestamp +
				", endTimestamp=" + endTimestamp +
				", inputParams='" + inputParams + '\'' +
				", inputParamsHash='" + inputParamsHash + '\'' +
				", moduleName='" + moduleName + '\'' +
				", branchName='" + branchName + '\'' +
				", executionList='" + executionList + '\'' +
				", dockerImageId='" + dockerImageId + '\'' +
				", Status='" + Status + '\'' +
				", nodeId='" + nodeId + '\'' +
				", reasonOfTrigger='" + reasonOfTrigger + '\'' +
				", summary='" + summary + '\'' +
				", log='" + log + '\'' +
				", externalId='" + externalId + '\'' +
				", statusDescription='" + statusDescription + '\'' +
				'}';
	}
}
