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
	private String brancheFrom;
	private String codeChanged="yes";
	private List<String> branchesFrom;


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

	public String getBrancheFrom() {
		return brancheFrom;
	}

	public void setBrancheFrom(String brancheFrom) {
		this.brancheFrom = brancheFrom;
	}

	public List<String> getBranchesFrom() {
		return branchesFrom;
	}

	public void setBranchesFrom(List<String> branchesFrom) {
		this.branchesFrom = branchesFrom;
	}

	public String getCodeChanged() {
		return codeChanged;
	}

	public void setCodeChanged(String codeChanged) {
		this.codeChanged = codeChanged;
	}
}
