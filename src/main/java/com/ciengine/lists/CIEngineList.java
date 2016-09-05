package main.java.com.ciengine.lists;

import main.java.com.ciengine.EnvironmentVariables;
import main.java.com.ciengine.steps.CIEngineStepException;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public interface CIEngineList
{
	void doList(EnvironmentVariables environmentVariables) throws CIEngineStepException;
}
