package com.ciengine.master.facades;

import com.ciengine.common.*;
import com.ciengine.master.controllers.addbuild.AddBuildRequest;
import com.ciengine.master.controllers.getbuilds.GetBuildsResponse;


/**
 * Created by emekhanikov on 14.09.2016.
 */
public interface CIEngineFacade
{
	GetBuildsResponse getBuildsResponse();

	GetBuildsResponse addBuild(AddBuildRequest addBuildRequest);

	void onEvent(DefaultCIEngineEvent defaultCIEngineEvent);

	void submitEvent(CIEngineEvent ciEngineEvent) throws CIEngineException;

	Module findModuleByGitUrl(String gitUrl);

	Build runOnNode(Node node);
}
