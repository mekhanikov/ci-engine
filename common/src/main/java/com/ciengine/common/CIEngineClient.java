package com.ciengine.common;

/**
 * Created by emekhanikov on 06.09.2016.
 */
public interface CIEngineClient
{
	void attachArtefacts(String buildId, String files);

	void sendEvent(DefaultCIEngineEvent ciEngineEvent);

    void setBuildStatus(String buildId, String skiped);
}
