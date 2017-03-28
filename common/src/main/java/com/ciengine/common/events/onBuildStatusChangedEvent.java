package com.ciengine.common.events;




import com.ciengine.common.DefaultCIEngineEvent;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class onBuildStatusChangedEvent extends DefaultCIEngineEvent
{
	private String buildId;
	private String newStatus;

	public String getBuildId() {
		return buildId;
	}

	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}

	public String getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		onBuildStatusChangedEvent that = (onBuildStatusChangedEvent) o;

		if (buildId != null ? !buildId.equals(that.buildId) : that.buildId != null) return false;
		return newStatus != null ? newStatus.equals(that.newStatus) : that.newStatus == null;
	}

	@Override
	public int hashCode() {
		int result = buildId != null ? buildId.hashCode() : 0;
		result = 31 * result + (newStatus != null ? newStatus.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "onBuildStatusChangedEvent{" +
				"buildId='" + buildId + '\'' +
				", newStatus='" + newStatus + '\'' +
				'}';
	}
}
