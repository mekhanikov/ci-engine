package com.ciengine.agent.steps.impl;

import com.ciengine.common.CIEngineStep;
import com.ciengine.common.CIEngineStepException;
import com.ciengine.common.CIEngineClient;
import com.ciengine.common.EnvironmentVariables;
import com.ciengine.common.events.OnNewArtifactEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by emekhanikov on 06.09.2016.
 */
@Component
public class NewArtefactsReleasedStep implements CIEngineStep
{
	@Autowired
	private CIEngineClient ciEngineClient;

	@Override public void doStep(EnvironmentVariables environmentVariables) throws CIEngineStepException
	{
		OnNewArtifactEvent onNewArtifactEvent = new OnNewArtifactEvent();

		String gitUrl = environmentVariables.getProperty("GIT_URL");
		String branchName = environmentVariables.getProperty("BRANCH_NAME");
		String commitId = environmentVariables.getProperty("COMMIT_ID");
		onNewArtifactEvent.setComitId(commitId);
		onNewArtifactEvent.setGitUrl(gitUrl);
		onNewArtifactEvent.setBranchName(branchName);
		ciEngineClient.sendEvent(onNewArtifactEvent);
	}
}
