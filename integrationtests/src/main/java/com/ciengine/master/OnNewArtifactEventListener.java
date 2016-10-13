package com.ciengine.master;

import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.DefaultCIEngineEvent;
import com.ciengine.common.events.OnNewArtifactEvent;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.CIEngineListenerException;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class OnNewArtifactEventListener implements CIEngineListener
{
	@Autowired
	MockBinaryRepositoryClient mockBinaryRepositoryClient;

	@Override public void onEvent(CIEngineEvent ciEngineEvent) throws CIEngineListenerException
	{
		OnNewArtifactEvent onNewArtifactEvent = (OnNewArtifactEvent)ciEngineEvent;
		mockBinaryRepositoryClient.addModule(onNewArtifactEvent.getModuleName());// TODO artefsact
	}

	@Override public boolean isEventApplicable(DefaultCIEngineEvent defaultCIEngineEvent)
	{
		return defaultCIEngineEvent instanceof OnNewArtifactEvent;
	}
}
