package com.ciengine.common;

import com.ciengine.common.events.OnNewArtifactEvent;


/**
 * Created by emekhanikov on 06.09.2016.
 */
public interface CIEngineClient
{
	void attachArtefacts(String buildId, String files);

	void sendEvent(CIEngineEvent ciEngineEvent);
}
