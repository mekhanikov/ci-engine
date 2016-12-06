package com.ciengine.common;


import com.ciengine.common.dto.*;
import com.ciengine.master.facades.CIEngineFacade;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by emekhanikov on 06.09.2016.
 */
//@Component
public class MockCIEngineClientImpl implements CIEngineClient
{// TODO 1. Used by Agents/Slaves to send events to Master.

	@Autowired
	CIEngineFacade ciEngineFacade;

	@Override public void attachArtefacts(String serverUrl, String buildId, String files)
	{


	}

	@Override public void sendEvent(String serverUrl, DefaultCIEngineEvent ciEngineEvent)
	{
		ciEngineFacade.onEvent(ciEngineEvent);
	}

	@Override
	public void setBuildStatus(String serverUrl, String externalBuildId, String status, String s) {
		SetBuildStatusRequest setBuildStatusRequest = new SetBuildStatusRequest();
		setBuildStatusRequest.setExternalBuildId(externalBuildId);
		setBuildStatusRequest.setStatus(status);
		setBuildStatusRequest.setStatusDescription(s);
		ciEngineFacade.setBuildStatus(setBuildStatusRequest);
	}

	@Override
	public boolean isModuleReleased(String serverUrl, String moduleNameToRelease) {
        IsModuleReleasedRequest isModuleReleasedRequest = new IsModuleReleasedRequest();
        isModuleReleasedRequest.setModule(moduleNameToRelease);
        IsModuleReleasedResponse isModuleReleasedResponse = ciEngineFacade.isModuleReleased(isModuleReleasedRequest);
		return isModuleReleasedResponse.isReleased();
	}

	@Override
	public AddBuildResponse findBuild(String serverUrl, AddBuildRequest addBuildRequest) {
		//AddBuildRequest addBuildRequest1 = new AddBuildRequest();
		//isModuleReleasedRequest.setModule(moduleNameToRelease);
		AddBuildResponse addBuildResponse = ciEngineFacade.findBuild(addBuildRequest);
		return addBuildResponse;
	}
}
