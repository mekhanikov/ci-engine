package com.ciengine.common;

import com.ciengine.common.dto.AddBuildRequest;
import com.ciengine.common.dto.AddBuildResponse;

/**
 * Created by emekhanikov on 06.09.2016.
 */
public interface CIEngineClient
{
	void attachArtefacts(String serverUrl, String buildId, String files);

	void sendEvent(String serverUrl, DefaultCIEngineEvent ciEngineEvent);

    void setBuildStatus(String serverUrl, String externalBuildId, String status);

    boolean isModuleReleased(String serverUrl, String moduleNameToRelease);

    AddBuildResponse findBuild(AddBuildRequest addBuildRequest);
}
