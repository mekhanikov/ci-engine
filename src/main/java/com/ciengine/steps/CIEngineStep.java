package main.java.com.ciengine.steps;

import main.java.com.ciengine.EnvironmentVariables;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public interface CIEngineStep
{
	void doStep(EnvironmentVariables environmentVariables) throws CIEngineStepException;
}
