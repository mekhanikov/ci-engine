package com.ciengine.agent.lists.current;



import com.ciengine.common.*;
import com.ciengine.common.dto.AddBuildRequest;
import com.ciengine.common.events.OnNewArtifactEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


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
		String dockerImageId = environmentVariables.getProperty(EnvironmentVariablesConstants.DOCKER_IMAGE_ID);

		String moduleName = moduleNameToRelease.split(":")[0];
/*
		AddBuildRequest addBuildRequest = new AddBuildRequest();
		addBuildRequest.setExecutionList("mockReleaseList");
		addBuildRequest.setDockerImageId(dockerImageId);
		addBuildRequest.setModuleName(moduleName);
		addBuildRequest.setBranchName(branchName);
		List<BuildModel> buildModels = ciEngineClient.findBuild(addBuildRequest);

		// If build (with the latest startTimestamp?) is skipped - rebuild
		String lastBuildStatus = buildModels != null && buildModels.size() > 0 ? buildModels.get(0).getStatus() : null;
		if (lastBuildStatus == null || BuildStatus.SKIPED.equals(lastBuildStatus)) {

		}
//*/
		if (!ciEngineClient.isModuleReleased(url, moduleNameToRelease)) {
			if (allDepsInPlace(url, moduleNameToRelease)) {
				System.out.print("d");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//throw new CIEngineStepException("");
				OnNewArtifactEvent onNewArtifactEvent = new OnNewArtifactEvent();
				onNewArtifactEvent.setComitId(commitId);
				onNewArtifactEvent.setGitUrl(gitUrl);
				onNewArtifactEvent.setBranchName(branchName);
				onNewArtifactEvent.setModuleName(moduleNameToRelease);
				ciEngineClient.sendEvent(url, onNewArtifactEvent);
				ciEngineClient.setBuildStatus(url, buildId, BuildStatus.SUCCESS);
			} else {
				System.out.print("DEPS ARE REQUIRED");
				ciEngineClient.setBuildStatus(url, buildId, BuildStatus.SKIPED);
			}

			// TODO read deps from pom.xml
			// Map each dep artefact to module (each module can be related to multiple artefacts).
			// Check if the module in list of going to release.
			// If in list, check if isModuleReleased
			// if not all required modules is released, SKIPPED, reason = ModA:1, ModB:2 are required, but has not been released yet
		} else {
			// TODO we will never be here?
			System.out.print("ALREADY RELEASED");
			// TODO SKIPED, reason = already released.
			// delete Release from DB (need releaseId in in args)
			ciEngineClient.setBuildStatus(url, buildId, BuildStatus.SKIPED);
		}
	}

	private boolean allDepsInPlace(String url, String module) {
		if ("ModC:2.0".equals(module)) {
			return true;
		}
		if ("ModB:2.0".equals(module) &&
				ciEngineClient.isModuleReleased(url, "ModC:2.0")) {
			return true;
		}
		if ("ModA:2.0".equals(module) && ciEngineClient.isModuleReleased(url, "ModB:2.0") && ciEngineClient.isModuleReleased(url, "ModC:2.0")) {
			return true;
		}
		return false;
	}
}
