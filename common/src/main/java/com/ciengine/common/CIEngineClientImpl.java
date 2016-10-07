package com.ciengine.common;



import com.ciengine.common.CIEngineClient;
import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.DefaultCIEngineEvent;
import com.ciengine.common.dto.OnEventRequest;
import com.ciengine.common.dto.OnEventResponse;
import com.ciengine.common.dto.SetBuildStatusRequest;
import com.ciengine.common.dto.SetBuildStatusResponse;
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
	@Override public void attachArtefacts(String buildId, String files)
	{


	}

	@Override public void sendEvent(DefaultCIEngineEvent ciEngineEvent)
	{
//		final String uri = "http://10.69.36.221:8080/onevent";// TODO to env_var.props
		final String uri = "http://127.0.0.1:8080/onevent";// TODO to env_var.props

		OnEventRequest onEventRequest = new OnEventRequest();
		RestTemplate restTemplate = new RestTemplate();
		//set interceptors/requestFactory
		ClientHttpRequestInterceptor ri = new LoggingRequestInterceptor();
		List<ClientHttpRequestInterceptor> ris = new ArrayList<ClientHttpRequestInterceptor>();
		ris.add(ri);
		restTemplate.setInterceptors(ris);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CIEngineEvent> entity = new HttpEntity<CIEngineEvent>(ciEngineEvent, headers);
		restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		OnEventResponse result = restTemplate.postForObject( uri, entity, OnEventResponse.class);
	}

	@Override
	public void setBuildStatus(String buildId, String skiped) {
		final String uri = "http://127.0.0.1:8080/setbuildstatus";// TODO to env_var.props

		SetBuildStatusRequest setBuildStatusRequest = new SetBuildStatusRequest();
		RestTemplate restTemplate = new RestTemplate();
		//set interceptors/requestFactory
		ClientHttpRequestInterceptor ri = new LoggingRequestInterceptor();
		List<ClientHttpRequestInterceptor> ris = new ArrayList<ClientHttpRequestInterceptor>();
		ris.add(ri);
		restTemplate.setInterceptors(ris);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<SetBuildStatusRequest> entity = new HttpEntity<>(setBuildStatusRequest, headers);
		restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		SetBuildStatusResponse result = restTemplate.postForObject( uri, entity, SetBuildStatusResponse.class);
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
