package main.java.com.ciengine;

import main.java.com.ciengine.events.CIEngineEvent;
import main.java.com.ciengine.events.impl.OnQueueBuildEvent;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public interface CIEngine
{
	void submitEvent(CIEngineEvent ciEngineEvent) throws CIEngineException;

	Module findModuleByGitUrl(String gitUrl);

	Build runOnNode(Node node);
}
