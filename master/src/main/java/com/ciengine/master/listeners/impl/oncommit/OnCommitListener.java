package com.ciengine.master.listeners.impl.oncommit;

import com.ciengine.common.*;
import com.ciengine.master.events.impl.OnCommitEvent;
import com.ciengine.master.events.impl.OnQueueBuildEvent;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.CIEngineListenerException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class OnCommitListener implements CIEngineListener
{
	@Autowired
	private CIEngine ciEngine;
	private List<OnCommitRule> rules;

	@Override public void onEvent(CIEngineEvent ciEngineEvent) throws CIEngineListenerException
	{
		OnCommitEvent onCommitEvent = (OnCommitEvent) ciEngineEvent;
		Module module = ciEngine.findModuleByGitUrl(((OnCommitEvent) ciEngineEvent).getGitUrl());
		EnvironmentVariables environmentVariablesFromEvent = new EnvironmentVariables();
		environmentVariablesFromEvent.addProperty("GIT_URL", onCommitEvent.getGitUrl());
		environmentVariablesFromEvent.addProperty("BRANCH_NAME", onCommitEvent.getBranchName());
		environmentVariablesFromEvent.addProperty("COMMIT_ID", onCommitEvent.getComitId());

		environmentVariablesFromEvent.addProperty("MODULE_NAME", module.getName());

		// TODO set module specific values

		List<OnCommitRule> onCommitRuleList = getRules();
		for(OnCommitRule onCommitRule : onCommitRuleList) {
			if(isApplicable(onCommitRule, onCommitEvent)) {
				OnQueueBuildEvent onQueueBuildEvent = new OnQueueBuildEvent();
				onQueueBuildEvent.setBranchName(module.getName());
				onQueueBuildEvent.setDockerImageId(onCommitRule.getDockerImageId());
				onQueueBuildEvent.setEnvironmentVariables(merge(environmentVariablesFromEvent, onCommitRule.getEnvironmentVariables()));
				onQueueBuildEvent.setExecutionList(onCommitRule.getApplyList());
				try
				{
					ciEngine.submitEvent(onQueueBuildEvent);
				}
				catch (CIEngineException e)
				{
					throw new CIEngineListenerException(e);
				}
			}
		}
	}

	private EnvironmentVariables merge(EnvironmentVariables environmentVariablesFromEvent,
			EnvironmentVariables environmentVariables)
	{
		// TODO
		return null;
	}

	private boolean isApplicable(OnCommitRule onCommitRule, OnCommitEvent onCommitEvent)
	{// TODO
		return false;
	}

	public List<OnCommitRule> getRules()
	{
		// TODO load from OnCommitListener.csv on each event.
		// TODO or only on start? because resources inside jar will never be overriten in runtime?
		return rules;
	}
}
