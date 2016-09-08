package com.ciengine.common;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public interface CIEngine
{
	void submitEvent(CIEngineEvent ciEngineEvent) throws CIEngineException;

	Module findModuleByGitUrl(String gitUrl);

	Build runOnNode(Node node);
}
