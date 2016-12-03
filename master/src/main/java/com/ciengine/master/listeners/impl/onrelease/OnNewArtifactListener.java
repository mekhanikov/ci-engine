package com.ciengine.master.listeners.impl.onrelease;

import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.DefaultCIEngineEvent;
import com.ciengine.common.EnvironmentVariables;
import com.ciengine.common.EnvironmentVariablesConstants;
import com.ciengine.common.dto.AddBuildRequest;
import com.ciengine.common.dto.IsModuleReleasedRequest;
import com.ciengine.common.dto.IsModuleReleasedResponse;
import com.ciengine.common.events.OnNewArtifactEvent;
import com.ciengine.master.facades.CIEngineFacade;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.CIEngineListenerException;
import com.ciengine.master.listeners.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by emekhanikov on 05.09.2016.
 */
//@Component(value = "OnNewArtifactListener")
public class OnNewArtifactListener implements CIEngineListener
{
	@Autowired
	private CIEngineFacade ciEngineFacade;
	private List<OnReleaseRule> rules = new ArrayList<>();

	@Override public void onEvent(CIEngineEvent ciEngineEvent) throws CIEngineListenerException
	{// TODO Triggered on OnNewArtifactEvent event. And on add OnReleaseRule to DB/mem.
//		OnNewArtifactEvent onNewArtifactEvent = (OnNewArtifactEvent) ciEngineEvent;
		//Module module = ciEngineFacade.findModuleByGitUrl(((OnNewArtifactEvent) ciEngineEvent).getGitUrl());
//		if (module == null) {
//			// TODO add warning?
//			return;
//		}
		String reasonOfTrigger = "";

			OnNewArtifactEvent ciEngineEvent1 = (OnNewArtifactEvent) ciEngineEvent;
			reasonOfTrigger = "Released module: " + ciEngineEvent1.getModuleName();
			// TODO run all?
			EnvironmentVariables environmentVariablesFromEvent = new EnvironmentVariables();
//		environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.GIT_URL, onNewArtifactEvent.getGitUrl());
//		environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.BRANCH_NAME, onNewArtifactEvent.getBranchName());
//		environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.COMMIT_ID, onNewArtifactEvent.getComitId());

//		environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.MODULE_NAME, module.getName());

			// TODO set module specific values

			List<OnReleaseRule> onReleaseRuleList = getRules();
			for(OnReleaseRule onReleaseRule : onReleaseRuleList) {
				IsModuleReleasedRequest isModuleReleasedRequest = new IsModuleReleasedRequest();
				isModuleReleasedRequest.setModule(onReleaseRule.getModuleNameToRelease());
				IsModuleReleasedResponse isModuleReleasedResponse = ciEngineFacade.isModuleReleased(isModuleReleasedRequest);
				if (!isModuleReleasedResponse.isReleased()) {
					// TODO check for dups right here?
					AddBuildRequest addBuildRequest = new AddBuildRequest();
					addBuildRequest.setExecutionList(onReleaseRule.getApplyList());
					addBuildRequest.setNodeId(null);
					addBuildRequest.setDockerImageId(onReleaseRule.getDockerImageId());
					addBuildRequest.setModuleName(onReleaseRule.getModuleNameToRelease());
					//addBuildRequest.setReasonOfTrigger("commit");
					addBuildRequest.setBranchName(onReleaseRule.getReleaseBranchName());
					addBuildRequest.setReasonOfTrigger(reasonOfTrigger);
//			List<BuildModel> buildModels = ciEngineFacade.findBuild(addBuildRequest);

					// If build (with the latest startTimestamp?) is skipped - rebuild
//			String lastBuildStatus = buildModels != null && buildModels.size() > 0 ? buildModels.get(0).getStatus() : null;
//			if (lastBuildStatus == null || BuildStatus.SKIPED.equals(lastBuildStatus)) {
					String buildExternalId = UUID.randomUUID().toString();
					addBuildRequest.setExternalId(buildExternalId);
					environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.BUILD_EXTERNAL_ID, buildExternalId);
					environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.GOING_TO_RELEASE, onReleaseRule.getGoingToRelease());
					//onReleaseRule.getApplyList();
					//onReleaseRule.getDockerImageId();
					onReleaseRule.getModuleNameToRelease();
					onReleaseRule.getReleaseBranchName();
					environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.MERGE_FROM_COMMIT_ID, onReleaseRule.getMergeFromCommitId());
					environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.MODULE_NAME, onReleaseRule.getModuleNameToRelease());
					environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.RELEASE_BRANCH_NAME, onReleaseRule.getReleaseBranchName());
					environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.DOCKER_IMAGE_ID, onReleaseRule.getDockerImageId());
					environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.CIENGINE_MASTER_URL, "http://127.0.0.1:8080"); // TODO to conf?
					addBuildRequest.setInputParams(Utils.makeString(Utils.merge(environmentVariablesFromEvent, onReleaseRule.getEnvironmentVariables())));
					ciEngineFacade.addBuild(addBuildRequest);
//			}


//				ciEngineFacade.onEvent(onQueueBuildEvent);

//				try
//				{
//
//				}
//				catch (CIEngineException e)
//				{
//					throw new CIEngineListenerException(e);
//				}
				}



			}


	}


	@Override public boolean isEventApplicable(DefaultCIEngineEvent defaultCIEngineEvent)
	{
		return defaultCIEngineEvent != null &&
				(defaultCIEngineEvent instanceof OnNewArtifactEvent);
	}



	public List<OnReleaseRule> getRules()
	{
		return ciEngineFacade.findAllReleases();
	}

	public void setRules(List<OnReleaseRule> rules)
	{
		this.rules = rules;
	}


}
