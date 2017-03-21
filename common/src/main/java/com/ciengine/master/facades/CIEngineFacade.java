package com.ciengine.master.facades;

import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.CIEngineException;
import com.ciengine.common.DefaultCIEngineEvent;
import com.ciengine.common.Module;
import com.ciengine.common.dto.*;
import com.ciengine.master.GetModulesResponse;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.OnReleaseRule;

import java.util.List;


/**
 * Created by emekhanikov on 14.09.2016.
 */
public interface CIEngineFacade
{
	FindBuildsResponse findBuilds();

	AddBuildResponse addBuild(AddBuildRequest addBuildRequest);

	void onEvent(DefaultCIEngineEvent defaultCIEngineEvent);

	void submitEvent(CIEngineEvent ciEngineEvent) throws CIEngineException;

	Module findModuleByGitUrl(String gitUrl);

	void addListener(CIEngineListener ciEngineListener);


	// TODO extract to ReleseFacade
	void submitRelease(OnReleaseRule release);

	// TODO extract to ReleseFacade
    List<OnReleaseRule> findAllReleases();

	AddBuildResponse findBuild(AddBuildRequest addBuildRequest);

    void setBuildStatus(SetBuildStatusRequest setBuildStatusRequest);

	IsModuleReleasedResponse isModuleReleased(IsModuleReleasedRequest isModuleReleasedRequest);

	GetModulesResponse getModulesResponse();

	SubmitReleasesResponse submitReleases(SubmitReleasesRequest submitReleasesRequest);

	FindModulesResponse findModules(FindModulesRequest findModulesRequest);

	String findGitUrlByModuleName(String moduleNameWithoutVersion);
}
