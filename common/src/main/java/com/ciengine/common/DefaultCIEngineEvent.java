package com.ciengine.common;

import com.ciengine.common.events.OnNewArtifactEvent;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * Created by emekhanikov on 15.09.2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(OnNewArtifactEvent.class)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "type")
public class DefaultCIEngineEvent implements CIEngineEvent
{
			private String s;


	public String getS()
	{
		return s;
	}

	public void setS(String s)
	{
		this.s = s;
	}
}
