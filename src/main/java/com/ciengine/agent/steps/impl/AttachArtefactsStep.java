package main.java.com.ciengine.agent.steps.impl;

import main.java.com.ciengine.common.EnvironmentVariables;
import main.java.com.ciengine.common.CIEngineClient;
import main.java.com.ciengine.agent.steps.CIEngineStep;
import main.java.com.ciengine.agent.steps.CIEngineStepException;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by emekhanikov on 06.09.2016.
 */
public class AttachArtefactsStep implements CIEngineStep
{
	@Autowired
	private CIEngineClient ciEngineClient;

	@Override public void doStep(EnvironmentVariables environmentVariables) throws CIEngineStepException
	{
		//Don't need to change HASH because dont change state of the build.
		String buildId = environmentVariables.getProperty("BUILD_ID");
		String files = environmentVariables.getProperty("ATTACH_FILES"); // E.g. "target/*.html, target/*.java"
		ciEngineClient.attachArtefacts(buildId, files);
	}
}
