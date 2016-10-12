package com.ciengine.common.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Created by emekhanikov on 14.09.2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IsModuleReleasedRequest
{
//	@XmlAttribute(name = "sss")

	private String externalBuildId;
	private String status;

	public String getExternalBuildId() {
		return externalBuildId;
	}

	public void setExternalBuildId(String externalBuildId) {
		this.externalBuildId = externalBuildId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
