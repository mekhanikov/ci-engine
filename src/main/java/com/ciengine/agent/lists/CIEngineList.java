package main.java.com.ciengine.agent.lists;

import main.java.com.ciengine.common.EnvironmentVariables;
import main.java.com.ciengine.agent.steps.CIEngineStepException;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public interface CIEngineList
{
	void doList(EnvironmentVariables environmentVariables) throws CIEngineStepException;
}
