package com.ciengine.master.facades;

import com.ciengine.common.*;
import com.ciengine.common.dto.SetBuildStatusRequest;
import com.ciengine.master.controllers.addbuild.AddBuildRequest;
import com.ciengine.master.controllers.getbuilds.GetBuildsResponse;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.impl.onrelease.OnReleaseRule;
import com.ciengine.master.model.BuildModel;

import java.util.List;


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

	void addListener(CIEngineListener ciEngineListener);

	void setModules(List<Module> moduleList);

	// TODO extract to ReleseFacade
	void submitRelease(Release release);

	// TODO extract to ReleseFacade
    List<OnReleaseRule> findAllReleases();

	List<BuildModel> findBuild(AddBuildRequest addBuildRequest);

    void setBuildStatus(SetBuildStatusRequest setBuildStatusRequest);
}
