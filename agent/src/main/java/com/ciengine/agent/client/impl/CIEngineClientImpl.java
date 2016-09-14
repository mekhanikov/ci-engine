package com.ciengine.agent.client.impl;



import com.ciengine.common.CIEngineClient;
import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.dto.OnEventRequest;
import com.ciengine.common.dto.OnEventResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/**
 * Created by emekhanikov on 06.09.2016.
 */
@Component
public class CIEngineClientImpl implements CIEngineClient
{// TODO 1. Used by Agents/Slaves to send events to Master.
	@Override public void attachArtefacts(String buildId, String files)
	{


	}

	@Override public void sendEvent(CIEngineEvent ciEngineEvent)
	{
		final String uri = "http://localhost:8080/onevent";

		OnEventRequest onEventRequest = new OnEventRequest();
// TODO sends XML swirch to JSON
		RestTemplate restTemplate = new RestTemplate();
		OnEventResponse result = restTemplate.postForObject( uri, onEventRequest, OnEventResponse.class);
	}
}
