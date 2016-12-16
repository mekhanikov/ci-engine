package com.ciengine.master.facades;

import java.util.List;


public interface EnvironmentFacade
{
	List<EnvironmentData> findApplyLists(String moduleName, String branchName);
	EnvironmentData findApplyList(String moduleName, String branchName);
}
