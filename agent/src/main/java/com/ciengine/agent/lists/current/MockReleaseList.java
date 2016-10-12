package com.ciengine.agent.lists.current;



import com.ciengine.common.*;
import com.ciengine.common.events.OnNewArtifactEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by emekhanikov on 05.09.2016.
 */
@Component
public class MockReleaseList implements CIEngineList
{// TODO rename to ReleaseList
	@Autowired
	private CIEngineClient ciEngineClient;

	@Override public void doList(EnvironmentVariables environmentVariables) throws CIEngineStepException
	{
		String gitUrl = environmentVariables.getProperty(EnvironmentVariablesConstants.GIT_URL);
		String branchName = environmentVariables.getProperty(EnvironmentVariablesConstants.BRANCH_NAME);
		String commitId = environmentVariables.getProperty(EnvironmentVariablesConstants.COMMIT_ID);
		String buildId = environmentVariables.getProperty(EnvironmentVariablesConstants.BUILD_EXTERNAL_ID);
		String goingToRelease = environmentVariables.getProperty(EnvironmentVariablesConstants.GOING_TO_RELEASE);
		String moduleNameToRelease = environmentVariables.getProperty(EnvironmentVariablesConstants.MODULE_NAME);
		String url = environmentVariables.getProperty(EnvironmentVariablesConstants.CIENGINE_MASTER_URL);

		if (!ciEngineClient.isModuleReleased(url, moduleNameToRelease)) {
			// TODO read deps from pom.xml
			// Map each dep artefact to module (each module can be related to multiple artefacts).
			// Check if the module in list of going to release.
			// If in list, check if isModuleReleased
			// if not all required modules is released, SKIPPED, reason = ModA:1, ModB:2 are required, but has not been released yet
		} else {
			// TODO SKIPED, reason = already released.
			// delete Release from DB (need releaseId in in args)
		}

System.out.print("d");
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//throw new CIEngineStepException("");
		OnNewArtifactEvent onNewArtifactEvent = new OnNewArtifactEvent();





		onNewArtifactEvent.setComitId(commitId);
		onNewArtifactEvent.setGitUrl(gitUrl);
		onNewArtifactEvent.setBranchName(branchName);
		ciEngineClient.sendEvent(url, onNewArtifactEvent);
		ciEngineClient.setBuildStatus(url, buildId, BuildStatus.SKIPED);
	}
}
