package com.ciengine.agent.lists.current;


import com.ciengine.common.*;
import com.ciengine.common.dto.*;
import com.ciengine.common.events.OnNewArtifactEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


/**
 * Created by emekhanikov on 05.09.2016.
 */
@Component
abstract class AbstractReleaseList implements CIEngineList
{
	@Autowired
	private CIEngineClient ciEngineClient;

	@Autowired
	@Qualifier("mavenStep")
	private CIEngineStep ciEngineStep;

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

		FindBuildsRequest findBuildsRequest = new FindBuildsRequest();
		findBuildsRequest.setExecutionList(getExecutionListName());
		findBuildsRequest.setDockerImageId(dockerImageId);
		findBuildsRequest.setModuleName(moduleNameToRelease);
		findBuildsRequest.setBranchName(branchName);
		FindBuildsResponse findBuildsResponse = ciEngineClient.findBuild(url, findBuildsRequest);
        // TODO calch hash and gilter by hash as well.
        // TODO except current one build - it alwasy IN PROGRESS
		List<Build> buildLists = findBuildsResponse.getBuildList();

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
			if (!ciEngineClient.isModuleReleased(url, moduleNameToRelease)) {// TODO how it was released if all builds are skipped?
				Set<String> waitingModules = allDepsInPlace(environmentVariables);
				if (waitingModules.isEmpty()) {
					build(environmentVariables);
					ciEngineStep.doStep(environmentVariables);
					OnNewArtifactEvent onNewArtifactEvent = new OnNewArtifactEvent();
					onNewArtifactEvent.setComitId(commitId);
					onNewArtifactEvent.setGitUrl(gitUrl);
					onNewArtifactEvent.setBranchName(branchName);
					onNewArtifactEvent.setModuleName(moduleNameToRelease);
					ciEngineClient.setBuildStatus(url, buildId, BuildStatus.SUCCESS, "RELEASED");
					ciEngineClient.sendEvent(url, onNewArtifactEvent);
				} else {
					System.out.print("DEPS ARE REQUIRED");
					ciEngineClient.setBuildStatus(url, buildId, BuildStatus.SKIPED, "DEPS ARE REQUIRED: " + String.join(", ", waitingModules));
				}

				// Map each dep artefact to module (each module can be related to multiple artefacts).
				// Check if the module in list of going to release.
				// If in list, check if isModuleReleased
				// if not all required modules is released, SKIPPED, reason = ModA:1, ModB:2 are required, but has not been released yet
			} else {
                // TODO never will be here (how it was released if all builds are skipped?)?! but we are here
				System.out.print("ALREADY RELEASED");
				// TODO SKIPED, reason = already released.
				// delete Release from DB (need releaseId in in args)
				ciEngineClient.setBuildStatus(url, buildId, BuildStatus.SKIPED, "ALREADY RELEASED");
			}
		} else {
			System.out.println("NOT ALLARESKIPPED");
			if (ciEngineClient.isModuleReleased(url, moduleNameToRelease)) {
				ciEngineClient.setBuildStatus(url, buildId, BuildStatus.SKIPED, "ALREADY RELEASED");
			} else {
				ciEngineClient.setBuildStatus(url, buildId, BuildStatus.SKIPED, "NOT ALLARESKIPPED");
			}
        }
	}

	protected abstract void build(EnvironmentVariables environmentVariables);

	protected abstract String getExecutionListName();

	protected abstract Set<String> allDepsInPlace(EnvironmentVariables environmentVariables);


	public CIEngineClient getCiEngineClient() {
		return ciEngineClient;
	}

	public void setCiEngineClient(CIEngineClient ciEngineClient) {
		this.ciEngineClient = ciEngineClient;
	}

	public CIEngineStep getCiEngineStep() {
		return ciEngineStep;
	}

	public void setCiEngineStep(CIEngineStep ciEngineStep) {
		this.ciEngineStep = ciEngineStep;
	}
}
