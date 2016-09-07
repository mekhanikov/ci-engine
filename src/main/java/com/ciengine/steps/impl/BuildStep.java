package main.java.com.ciengine.steps.impl;

import main.java.com.ciengine.EnvironmentVariables;
import main.java.com.ciengine.steps.CIEngineStep;
import main.java.com.ciengine.steps.CIEngineStepException;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class BuildStep implements CIEngineStep
{
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
		//
		return this;
	}

	private String calcHash(String... args)
	{
		String result = "";
		for(String s : args) {
			// TODO
		}
		return result;
	}

	private String getTheLattestCommitForBranch(String gitUrl, String branchName)
	{// TODO
		return null;
	}
}
