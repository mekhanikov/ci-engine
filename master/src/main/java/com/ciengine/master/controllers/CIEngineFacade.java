package com.ciengine.master.controllers;

import com.ciengine.common.DefaultCIEngineEvent;
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
}
