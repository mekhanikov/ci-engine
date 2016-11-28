package com.ciengine.master.controllers.getbuilds;

import com.ciengine.common.dto.Build;

import javax.xml.bind.annotation.*;
import java.util.List;


/**
 * Created by emekhanikov on 14.09.2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GetBuildsResponse
{
	@XmlAttribute(name = "sss")
private String s = "sdd";

	private List<Build> buildList;

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
