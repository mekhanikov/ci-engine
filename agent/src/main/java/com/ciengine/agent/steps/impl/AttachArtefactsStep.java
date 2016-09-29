package com.ciengine.agent.steps.impl;

import com.ciengine.common.CIEngineStep;
import com.ciengine.common.CIEngineStepException;
import com.ciengine.common.CIEngineClient;
import com.ciengine.common.EnvironmentVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by emekhanikov on 06.09.2016.
 */
@Component
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
