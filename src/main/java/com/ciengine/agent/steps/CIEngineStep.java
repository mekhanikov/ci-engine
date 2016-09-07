package main.java.com.ciengine.agent.steps;

import main.java.com.ciengine.common.EnvironmentVariables;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public interface CIEngineStep
{
	void doStep(EnvironmentVariables environmentVariables) throws CIEngineStepException;
}
