package com.ciengine.webapp.web.controllers;

import com.ciengine.common.Repo;

import java.util.List;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class ModuleItem
{
	private boolean enabled;
	private String name;


	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
