package main.java.com.ciengine;

import main.java.com.ciengine.events.impl.OnQueueBuildEvent;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public interface CIEngine
{
	void submitEvent(OnQueueBuildEvent onQueueBuildEvent) throws CIEngineException;

	Module findModuleByGitUrl(String gitUrl);
}
