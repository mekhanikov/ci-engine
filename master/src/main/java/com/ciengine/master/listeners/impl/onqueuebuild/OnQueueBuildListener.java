package com.ciengine.master.listeners.impl.onqueuebuild;




import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.DefaultCIEngineEvent;
import com.ciengine.common.events.OnQueueBuildEvent;
import com.ciengine.master.controllers.addbuild.AddBuildRequest;
import com.ciengine.master.facades.CIEngineFacade;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.CIEngineListenerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by emekhanikov on 05.09.2016.
 */
@Component(value = "OnQueueBuildListener")
public class OnQueueBuildListener implements CIEngineListener
{
	@Autowired
	private CIEngineFacade ciEngineFacade;

	@Override public void onEvent(CIEngineEvent ciEngineEvent) throws CIEngineListenerException
	{
		OnQueueBuildEvent onQueueBuildEvent = (OnQueueBuildEvent) ciEngineEvent;
		AddBuildRequest addBuildRequest = new AddBuildRequest();
		addBuildRequest.setExecutionList(onQueueBuildEvent.getExecutionList());
		addBuildRequest.setNodeId(null);
		addBuildRequest.setDockerImageId(onQueueBuildEvent.getDockerImageId());
//		addBuildRequest.setInputParams(onQueueBuildEvent.getEnvironmentVariables().toString());// TODO
		addBuildRequest.setModuleName(onQueueBuildEvent.getModuleName());
		addBuildRequest.setReasonOfTrigger(onQueueBuildEvent.getReasonOfTrigger());
		addBuildRequest.setBranchName(onQueueBuildEvent.getBranchName());
		ciEngineFacade.addBuild(addBuildRequest);
	}

	@Override public boolean isEventApplicable(DefaultCIEngineEvent defaultCIEngineEvent)
	{
		return defaultCIEngineEvent != null && defaultCIEngineEvent instanceof OnQueueBuildEvent;
	}
}
