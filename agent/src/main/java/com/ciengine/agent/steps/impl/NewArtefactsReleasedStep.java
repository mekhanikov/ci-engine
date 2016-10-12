package com.ciengine.agent.steps.impl;

import com.ciengine.common.*;
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

		String gitUrl = environmentVariables.getProperty(EnvironmentVariablesConstants.GIT_URL);
		String branchName = environmentVariables.getProperty(EnvironmentVariablesConstants.BRANCH_NAME);
		String commitId = environmentVariables.getProperty(EnvironmentVariablesConstants.COMMIT_ID);
		String url = environmentVariables.getProperty(EnvironmentVariablesConstants.CIENGINE_MASTER_URL);
		onNewArtifactEvent.setComitId(commitId);
		onNewArtifactEvent.setGitUrl(gitUrl);
		onNewArtifactEvent.setBranchName(branchName);
		ciEngineClient.sendEvent(url, onNewArtifactEvent);
	}
}
