package com.ciengine.master.facades;

import java.util.List;


public interface EnvironmentFacade
{
	EnvironmentData findApplyList(String moduleName, String branchName);

    void createEnvironmentData(String modA, String develop, String onCommitList, String dockerid);
}
