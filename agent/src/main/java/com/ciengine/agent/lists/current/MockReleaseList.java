package com.ciengine.agent.lists.current;



import com.ciengine.common.*;
import com.ciengine.common.dto.AddBuildRequest;
import com.ciengine.common.dto.AddBuildResponse;
import com.ciengine.common.dto.Build;
import com.ciengine.common.events.OnNewArtifactEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
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
		String branchName = environmentVariables.getProperty(EnvironmentVariablesConstants.RELEASE_BRANCH_NAME);
		String commitId = environmentVariables.getProperty(EnvironmentVariablesConstants.COMMIT_ID);
		String buildId = environmentVariables.getProperty(EnvironmentVariablesConstants.BUILD_EXTERNAL_ID);
		String goingToRelease = environmentVariables.getProperty(EnvironmentVariablesConstants.GOING_TO_RELEASE);
		String moduleNameToRelease = environmentVariables.getProperty(EnvironmentVariablesConstants.MODULE_NAME);
		String url = environmentVariables.getProperty(EnvironmentVariablesConstants.CIENGINE_MASTER_URL);
		String dockerImageId = environmentVariables.getProperty(EnvironmentVariablesConstants.DOCKER_IMAGE_ID);

		//String moduleName = moduleNameToRelease.split(":")[0];

		AddBuildRequest addBuildRequest = new AddBuildRequest();
		addBuildRequest.setExecutionList("mockReleaseList");
		addBuildRequest.setDockerImageId(dockerImageId);
		addBuildRequest.setModuleName(moduleNameToRelease);
		addBuildRequest.setBranchName(branchName);
		AddBuildResponse addBuildResponse = ciEngineClient.findBuild(url, addBuildRequest);
        // TODO calch hash and gilter by hash as well.
        // TODO except current one build - it alwasy IN PROGRESS
		List<Build> buildLists = addBuildResponse.getBuildList();

        // If has at least one build IN PROGRESS/QUEUED/FAILED (check hash as well, it should be the same?) - skip current one - no need build the same.

        boolean allAreSkipped = true;
        if (buildLists != null) {
            for (Build build : buildLists) {
                if ( !build.getExternalId().equals(buildId) && !BuildStatus.SKIPED.equals(build.getStatus())) {
                    allAreSkipped = false;
                }
            }
        }

		// If build (with the latest startTimestamp?) is skipped - rebuild
		//String lastBuildStatus = buildLists != null && buildLists.size() > 0 ? buildLists.get(0).getStatus() : null;
		if (allAreSkipped) {
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
                // TODO never will be here?! but we are here
				System.out.print("ALREADY RELEASED");
				// TODO SKIPED, reason = already released.
				// delete Release from DB (need releaseId in in args)
				ciEngineClient.setBuildStatus(url, buildId, BuildStatus.SKIPED);
			}
		} else {
            ciEngineClient.setBuildStatus(url, buildId, BuildStatus.SKIPED);
        }
	}

	private boolean allDepsInPlace(String url, String module0) {
		String moduleName = module0.split(":")[0];
		String moduleVersion = module0.split(":")[1];
		if ("ModC".equals(moduleName)) {
			return true;
		}
		if ("ModB".equals(moduleName) &&
				ciEngineClient.isModuleReleased(url, "ModC:" + moduleVersion)) {
			return true;
		}
		if ("ModA".equals(moduleName) && ciEngineClient.isModuleReleased(url, "ModB:" + moduleVersion) && ciEngineClient.isModuleReleased(url, "ModC:" + moduleVersion)) {
			return true;
		}
		return false;
	}
}
