package main.java.com.ciengine.listeners.impl;

import main.java.com.ciengine.CIEngine;
import main.java.com.ciengine.EnvironmentVariables;
import main.java.com.ciengine.events.CIEngineEvent;
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
		EnvironmentVariables environmentVariables = new EnvironmentVariables();
		// TODO copy all info from event
		ciEngine.submitBuild();
	}
}
