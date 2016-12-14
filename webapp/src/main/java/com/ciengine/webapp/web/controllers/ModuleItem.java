package com.ciengine.webapp.web.controllers;

import java.util.List;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class ModuleItem
{
	private boolean enabled;
	private String name;
	private String brancheFrom;
	private String numericversion = "2.0";
	private String codeChanged="yes";
	private String milestonetype="M";
	private String version="2.0-M1";
	private List<String> branchesFrom;
	private String brancheTo;
	private List<String> branchesTo;


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

	public String getBrancheTo() {
		return brancheTo;
	}

	public void setBrancheTo(String brancheTo) {
		this.brancheTo = brancheTo;
	}

	public List<String> getBranchesTo() {
		return branchesTo;
	}

	public void setBranchesTo(List<String> branchesTo) {
		this.branchesTo = branchesTo;
	}

    public String getNumericversion() {
        return numericversion;
    }

    public void setNumericversion(String numericversion) {
        this.numericversion = numericversion;
    }

    public String getMilestonetype() {
        return milestonetype;
    }

    public void setMilestonetype(String milestonetype) {
        this.milestonetype = milestonetype;
    }

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
