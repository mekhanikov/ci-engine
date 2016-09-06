package main.java.com.ciengine.steps.impl;

import main.java.com.ciengine.EnvironmentVariables;
import main.java.com.ciengine.client.CIEngineClient;
import main.java.com.ciengine.steps.CIEngineStep;
import main.java.com.ciengine.steps.CIEngineStepException;
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
		String buildId = environmentVariables.getProperty("BUILD_ID");
		String files = environmentVariables.getProperty("ATTACH_FILES"); // E.g. "target/*.html, target/*.java"
		ciEngineClient.attachArtefacts(buildId, files);
	}
}
