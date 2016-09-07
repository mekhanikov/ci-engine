package main.java.com.ciengine.steps.impl;

import main.java.com.ciengine.EnvironmentVariables;
import main.java.com.ciengine.steps.CIEngineStep;
import main.java.com.ciengine.steps.CIEngineStepException;
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
		CIEngineStep buildStepForModule = retrieveBuildStepForModule(environmentVariables.getProperty("BUILD_STEP_FOR_MODULE"));
		buildStepForModule.doStep(environmentVariables);
		String hash = environmentVariables.getProperty("HASH");
//		hash = calcHash(hash, environmentVariables.getProperty("BUILD_STEP_FOR_MODULE"));
//		environmentVariables.addProperty("HASH", hash);
	}

	private CIEngineStep retrieveBuildStepForModule(String moduleName)
	{
		//TODO if cant find specific, use default (this?). DEFAULT, DEFAULT_MODULE_A, DEFAULT_MODULE_A_6.2v
		return this;
	}
}
