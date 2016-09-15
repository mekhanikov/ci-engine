package com.ciengine.master;



import com.ciengine.common.CIEngineClient;
import com.ciengine.common.DefaultCIEngineEvent;


/**
 * Created by emekhanikov on 06.09.2016.
 */
public class CIEngineClientImpl implements CIEngineClient
{// TODO 1. Used by Agents/Slaves to send events to Master.
	@Override public void attachArtefacts(String buildId, String files)
	{

	}

	@Override public void sendEvent(DefaultCIEngineEvent ciEngineEvent)
	{

	}
}
