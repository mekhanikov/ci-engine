package com.ciengine.agent.client.impl;



import com.ciengine.common.CIEngineClient;
import org.springframework.stereotype.Component;


/**
 * Created by emekhanikov on 06.09.2016.
 */
@Component
public class CIEngineClientImpl implements CIEngineClient
{// TODO 1. Used by Agents/Slaves to send events to Master.
	@Override public void attachArtefacts(String buildId, String files)
	{


	}
}
