package com.ciengine.common;



import com.ciengine.common.CIEngineClient;
import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.DefaultCIEngineEvent;
import com.ciengine.common.dto.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by emekhanikov on 06.09.2016.
 */
@Component
public class CIEngineClientImpl implements CIEngineClient
{// TODO 1. Used by Agents/Slaves to send events to Master.
	@Override public void attachArtefacts(String serverUrl, String buildId, String files)
	{


	}

	@Override public void sendEvent(String serverUrl, DefaultCIEngineEvent ciEngineEvent)
	{
//		final String uri = "http://10.69.36.221:8080/onevent";
		final String uri = serverUrl + "/onevent";

//		OnEventRequest onEventRequest = new OnEventRequest();
//		RestTemplate restTemplate = new RestTemplate();
//		//set interceptors/requestFactory
//		ClientHttpRequestInterceptor ri = new LoggingRequestInterceptor();
//		List<ClientHttpRequestInterceptor> ris = new ArrayList<ClientHttpRequestInterceptor>();
//		ris.add(ri);
//		restTemplate.setInterceptors(ris);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//
//		HttpEntity<CIEngineEvent> entity = new HttpEntity<CIEngineEvent>(ciEngineEvent, headers);
//		restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
//		OnEventResponse result = restTemplate.postForObject( uri, entity, OnEventResponse.class);
		doPost(uri, ciEngineEvent, SetBuildStatusResponse.class);
	}

	@Override
	public void setBuildStatus(String serverUrl, String externalBuildId, String status) {
		final String uri =  serverUrl + "/setbuildstatus";

		SetBuildStatusRequest setBuildStatusRequest = new SetBuildStatusRequest();
		setBuildStatusRequest.setExternalBuildId(externalBuildId);
		setBuildStatusRequest.setStatus(status);
//		RestTemplate restTemplate = new RestTemplate();
//		//set interceptors/requestFactory
//		ClientHttpRequestInterceptor ri = new LoggingRequestInterceptor();
//		List<ClientHttpRequestInterceptor> ris = new ArrayList<ClientHttpRequestInterceptor>();
//		ris.add(ri);
//		restTemplate.setInterceptors(ris);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//
//		HttpEntity<SetBuildStatusRequest> entity = new HttpEntity<>(setBuildStatusRequest, headers);
//		restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
//		SetBuildStatusResponse result = restTemplate.postForObject( uri, entity, SetBuildStatusResponse.class);

		doPost(uri, setBuildStatusRequest, SetBuildStatusResponse.class);
	}

	@Override
	public boolean isModuleReleased(String serverUrl, String moduleNameToRelease) {
        final String uri =  serverUrl + "/ismodulereleased";
        IsModuleReleasedRequest isModuleReleasedRequest = new IsModuleReleasedRequest();
        IsModuleReleasedResponse isModuleReleasedResponse = doPost(uri, isModuleReleasedRequest, IsModuleReleasedResponse.class);
		return isModuleReleasedResponse.isReleased();
	}

	private <T> T doPost(String url, Object setBuildStatusRequest, Class<T> responseType) {
		RestTemplate restTemplate = new RestTemplate();
		//set interceptors/requestFactory
		ClientHttpRequestInterceptor ri = new LoggingRequestInterceptor();
		List<ClientHttpRequestInterceptor> ris = new ArrayList<ClientHttpRequestInterceptor>();
		ris.add(ri);
		restTemplate.setInterceptors(ris);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Object> entity = new HttpEntity<>(setBuildStatusRequest, headers);
		restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		return restTemplate.postForObject( url, entity, responseType);
	}

	public static class LoggingRequestInterceptor implements ClientHttpRequestInterceptor
	{


		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws
				IOException
		{

			ClientHttpResponse response = execution.execute(request, body);


			log(request,body,response);

			return response;
		}

		private void log(HttpRequest request, byte[] body, ClientHttpResponse response) throws IOException {
			System.out.println("*************************************");
			String str = new String(body, StandardCharsets.UTF_8);
			System.out.println(str);
			System.out.println("*************************************");
		}
	}
}
