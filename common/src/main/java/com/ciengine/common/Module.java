package com.ciengine.common;

import java.util.List;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class Module
{
	private String name;
	private List<String> branchesFrom;
	private List<String> branchesTo;
	private List<Repo> repoList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getBranchesFrom() {
		return branchesFrom;
	}

	public void setBranchesFrom(List<String> branchesFrom) {
		this.branchesFrom = branchesFrom;
	}

	public List<String> getBranchesTo() {
		return branchesTo;
	}

	public void setBranchesTo(List<String> branchesTo) {
		this.branchesTo = branchesTo;
	}

	public List<Repo> getRepoList() {
		return repoList;
	}

	public void setRepoList(List<Repo> repoList) {
		this.repoList = repoList;
	}
}
