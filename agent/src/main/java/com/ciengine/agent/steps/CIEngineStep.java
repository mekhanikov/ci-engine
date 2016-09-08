package com.ciengine.agent.steps;



import com.ciengine.common.EnvironmentVariables;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public interface CIEngineStep
{
	void doStep(EnvironmentVariables environmentVariables) throws CIEngineStepException;
}
