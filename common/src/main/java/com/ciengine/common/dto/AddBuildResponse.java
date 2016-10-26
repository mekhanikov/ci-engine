package com.ciengine.common.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


/**
 * Created by emekhanikov on 14.09.2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AddBuildResponse
{
	private List<Build> buildList;

	@XmlAttribute(name = "sss")
private String s = "sdd";

	public String getS()
	{
		return s;
	}

	public void setS(String s)
	{
		this.s = s;
	}

	public List<Build> getBuildList() {
		return buildList;
	}

	public void setBuildList(List<Build> buildList) {
		this.buildList = buildList;
	}
}
