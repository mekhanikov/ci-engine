package main.java.com.ciengine;

import main.java.com.ciengine.events.impl.OnQueueBuildEvent;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class CIEngineImpl implements CIEngine
{
	@Override
	public void submitEvent(OnQueueBuildEvent onQueueBuildEvent) throws CIEngineException
	{

	}

	@Override public Module findModuleByGitUrl(String gitUrl)
	{
		return null;
	}
}
