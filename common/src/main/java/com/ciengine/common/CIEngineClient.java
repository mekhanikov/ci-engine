package com.ciengine.common;

import com.ciengine.common.dto.*;

/**
 * Created by emekhanikov on 06.09.2016.
 */
public interface CIEngineClient
{
	void attachArtefacts(String serverUrl, String buildId, String files);

	void sendEvent(String serverUrl, DefaultCIEngineEvent ciEngineEvent);

    void setBuildStatus(String serverUrl, String externalBuildId, String status, String s);

    boolean isModuleReleased(String serverUrl, String moduleNameToRelease);

    FindBuildsResponse findBuild(String serverUrl, FindBuildsRequest findBuildsRequest);

    SubmitReleasesResponse submitReleases(String serverUrl, SubmitReleasesRequest submitReleasesRequest);

    FindModulesResponse findModules(String serverUrl, FindModulesRequest findModulesRequest);
    FindBuildsResponse findBuilds(String serverUrl, FindBuildsRequest findBuildsRequest);
}
