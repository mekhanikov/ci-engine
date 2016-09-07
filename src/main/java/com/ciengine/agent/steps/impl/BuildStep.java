package main.java.com.ciengine.agent.steps.impl;

import main.java.com.ciengine.common.EnvironmentVariables;
import main.java.com.ciengine.agent.steps.CIEngineStep;
import main.java.com.ciengine.agent.steps.CIEngineStepException;
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
		// TODO send logs
		// TODO set build status
	}
}
