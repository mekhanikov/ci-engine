package main.java.com.ciengine.listeners.impl.oncommit;

import main.java.com.ciengine.CIEngine;
import main.java.com.ciengine.CIEngineException;
import main.java.com.ciengine.EnvironmentVariables;
import main.java.com.ciengine.Module;
import main.java.com.ciengine.events.CIEngineEvent;
import main.java.com.ciengine.events.impl.OnCommitEvent;
import main.java.com.ciengine.listeners.CIEngineListener;
import main.java.com.ciengine.listeners.CIEngineListenerException;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class OnCommitListener implements CIEngineListener
{
	@Autowired
	private CIEngine ciEngine;

	@Override public void onEvent(CIEngineEvent ciEngineEvent) throws CIEngineListenerException
	{
		OnCommitEvent onCommitEvent = (OnCommitEvent)ciEngineEvent;
		Module  module = ciEngine.findModuleByGitUrl(((OnCommitEvent) ciEngineEvent).getGitUrl());
		onCommitEvent.getGitUrl();
		EnvironmentVariables environmentVariables = new EnvironmentVariables();
		// TODO copy all info from event
		OnCommitRule onCommitRule = new OnCommitRule();
		try
		{
			ciEngine.submitBuild(module.getName(), onCommitEvent.getBranchName(),
					onCommitRule.getApplyList(), onCommitRule.getDockerImageId(), onCommitRule.getEnvironmentVariables());
		}
		catch (CIEngineException e)
		{
			throw new CIEngineListenerException(e);
		}
	}
}
