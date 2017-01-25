package com.ciengine.sourcesrepository;


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
public class SourceRepositoryClientImpl implements SourceRepositoryClient
{// TODO 1. Used by Agents/Slaves to send events to Master.

	@Override
	public GetDiffResponse getDiff(String serverUrl, GetDiffRequest getDiffRequest) {
		//		final String uri = "http://10.69.36.221:8080/onevent";
		final String uri = serverUrl + "/getdiff";

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
		return doPost(uri, getDiffRequest, GetDiffResponse.class);
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
