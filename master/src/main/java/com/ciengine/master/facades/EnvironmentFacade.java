package com.ciengine.master.facades;

import com.ciengine.common.Node;
import com.ciengine.master.model.BuildModel;

import java.util.List;


public interface EnvironmentFacade
{
	List<EnvironmentData> findApplyLists(String moduleName, String branchName);
	EnvironmentData findApplyList(String moduleName, String branchName);
}
