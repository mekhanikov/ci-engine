package main.java.com.ciengine.listeners.impl.oncommit;

import main.java.com.ciengine.CIEngine;
import main.java.com.ciengine.CIEngineException;
import main.java.com.ciengine.EnvironmentVariables;
import main.java.com.ciengine.Module;
import main.java.com.ciengine.events.CIEngineEvent;
import main.java.com.ciengine.events.impl.OnCommitEvent;
import main.java.com.ciengine.events.impl.OnQueueBuildEvent;
import main.java.com.ciengine.listeners.CIEngineListener;
import main.java.com.ciengine.listeners.CIEngineListenerException;
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
	{// TODO load?
		return rules;
	}
}
