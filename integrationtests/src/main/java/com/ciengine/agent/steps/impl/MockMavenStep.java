package com.ciengine.agent.steps.impl;


import com.ciengine.common.*;
import com.ciengine.master.MockBinaryRepositoryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by emekhanikov on 05.09.2016.
 */
//@Component("mavenStep")
public class MockMavenStep implements CIEngineStep
{

	@Autowired
	private MockBinaryRepositoryClient mockBinaryRepositoryClient;

	@Override
	public void doStep(EnvironmentVariables environmentVariables) throws CIEngineStepException
	{
		String moduleNameToRelease = environmentVariables.getProperty(EnvironmentVariablesConstants.MODULE_NAME);
		mockBinaryRepositoryClient.addModule(moduleNameToRelease);
	}
}
