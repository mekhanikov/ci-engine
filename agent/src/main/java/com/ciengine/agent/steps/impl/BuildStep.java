package com.ciengine.agent.steps.impl;


import com.ciengine.agent.steps.CIEngineStep;
import com.ciengine.agent.steps.CIEngineStepException;
import com.ciengine.common.EnvironmentVariables;
import org.springframework.stereotype.Component;


/**
 * Created by emekhanikov on 05.09.2016.
 */
@Component
public class BuildStep implements CIEngineStep
{// TODO or rename to BuildModAStep
	@Override
	public void doStep(EnvironmentVariables environmentVariables) throws CIEngineStepException
	{
		// TODO mvn clean install
		// TODO set build status, WHY IF MASTER SET IT BY ITSELF BASED ON build.sh exit code? somebody overwrite other!
	}
}
