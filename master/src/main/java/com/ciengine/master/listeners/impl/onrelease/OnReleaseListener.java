package com.ciengine.master.listeners.impl.onrelease;

import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.DefaultCIEngineEvent;
import com.ciengine.common.EnvironmentVariables;
import com.ciengine.common.events.OnNewArtifactEvent;
import com.ciengine.common.events.OnReleaseSubmitedEvent;
import com.ciengine.master.controllers.addbuild.AddBuildRequest;
import com.ciengine.master.facades.CIEngineFacade;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.CIEngineListenerException;
import com.ciengine.master.model.BuildModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by emekhanikov on 05.09.2016.
 */
@Component(value = "OnReleaseListener")
public class OnReleaseListener implements CIEngineListener
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
		EnvironmentVariables environmentVariablesFromEvent = new EnvironmentVariables();
//		environmentVariablesFromEvent.addProperty("GIT_URL", onNewArtifactEvent.getGitUrl());
//		environmentVariablesFromEvent.addProperty("BRANCH_NAME", onNewArtifactEvent.getBranchName());
//		environmentVariablesFromEvent.addProperty("COMMIT_ID", onNewArtifactEvent.getComitId());

//		environmentVariablesFromEvent.addProperty("MODULE_NAME", module.getName());

		// TODO set module specific values

		List<OnReleaseRule> onReleaseRuleList = getRules();
		for(OnReleaseRule onReleaseRule : onReleaseRuleList) {
// TODO check for dups right here?
				AddBuildRequest addBuildRequest = new AddBuildRequest();
				addBuildRequest.setExecutionList(onReleaseRule.getApplyList());
				addBuildRequest.setNodeId(null);
				addBuildRequest.setDockerImageId(onReleaseRule.getDockerImageId());
				addBuildRequest.setInputParams(makeString(merge(environmentVariablesFromEvent, onReleaseRule.getEnvironmentVariables())));
				addBuildRequest.setModuleName(onReleaseRule.getModuleNameToRelease());
				addBuildRequest.setReasonOfTrigger("commit");
				addBuildRequest.setBranchName(onReleaseRule.getModuleNameToRelease());// todo or what?
			List<BuildModel> buildModels = ciEngineFacade.findBuild(addBuildRequest);
			// TODO If build (with the latest startTimestamp?) is skipped - rebuild
			if (buildModels == null || buildModels.size() == 0) {
				ciEngineFacade.addBuild(addBuildRequest);
			}


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

	private String makeString(EnvironmentVariables merge)
	{
		StringBuilder stringBuilder = new StringBuilder();
		if (merge != null && merge.getProperties() != null) {
			for (Map.Entry<String, Object> kvEntry : merge.getProperties().entrySet()) {
				stringBuilder.append(kvEntry.getKey());
				stringBuilder.append("=");
				stringBuilder.append(kvEntry.getValue());
				stringBuilder.append("\n");
			}
		}
		return stringBuilder.toString();
	}

	@Override public boolean isEventApplicable(DefaultCIEngineEvent defaultCIEngineEvent)
	{
		return defaultCIEngineEvent != null &&
				(defaultCIEngineEvent instanceof OnNewArtifactEvent  || defaultCIEngineEvent instanceof OnReleaseSubmitedEvent);
	}

	private EnvironmentVariables merge(EnvironmentVariables environmentVariablesFromEvent,
			EnvironmentVariables environmentVariables)
	{
		EnvironmentVariables environmentVariablesMerged = new EnvironmentVariables();
		if (environmentVariablesFromEvent != null && environmentVariablesFromEvent.getProperties() != null) {
			environmentVariablesMerged.addProperties(environmentVariablesFromEvent.getProperties());
		}
		if (environmentVariables != null && environmentVariables.getProperties() != null) {
			environmentVariablesMerged.addProperties(environmentVariables.getProperties());
		}

		return environmentVariablesMerged;
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
