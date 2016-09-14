package com.ciengine.master.controllers.getbuilds;

import javax.xml.bind.annotation.*;


/**
 * Created by emekhanikov on 14.09.2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GetBuildsResponse
{
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
}
