package com.ciengine.common;

import com.ciengine.common.dto.AddBuildRequest;
import com.ciengine.common.dto.AddBuildResponse;
import com.ciengine.common.dto.SubmitReleasesRequest;
import com.ciengine.common.dto.SubmitReleasesResponse;

/**
 * Created by emekhanikov on 06.09.2016.
 */
public interface CIEngineClient
{
	void attachArtefacts(String serverUrl, String buildId, String files);

	void sendEvent(String serverUrl, DefaultCIEngineEvent ciEngineEvent);

    void setBuildStatus(String serverUrl, String externalBuildId, String status, String s);

    boolean isModuleReleased(String serverUrl, String moduleNameToRelease);

    AddBuildResponse findBuild(String serverUrl, AddBuildRequest addBuildRequest);

    SubmitReleasesResponse submitReleases(String serverUrl, SubmitReleasesRequest submitReleasesRequest);
}
