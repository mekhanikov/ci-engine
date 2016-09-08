package com.ciengine.agent.lists;


import com.ciengine.agent.steps.CIEngineStepException;
import com.ciengine.common.EnvironmentVariables;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public interface CIEngineList
{
	void doList(EnvironmentVariables environmentVariables) throws CIEngineStepException;
}
