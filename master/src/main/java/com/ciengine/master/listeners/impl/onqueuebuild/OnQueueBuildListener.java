package com.ciengine.master.listeners.impl.onqueuebuild;




import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.DefaultCIEngineEvent;
import com.ciengine.common.events.OnCommitEvent;
import com.ciengine.common.events.OnQueueBuildEvent;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.CIEngineListenerException;
import org.springframework.stereotype.Component;


/**
 * Created by emekhanikov on 05.09.2016.
 */
@Component(value = "OnQueueBuildListener")
public class OnQueueBuildListener implements CIEngineListener
{
	@Override public void onEvent(CIEngineEvent ciEngineEvent) throws CIEngineListenerException
	{
		OnQueueBuildEvent onQueueBuildEvent = (OnQueueBuildEvent) ciEngineEvent;
		// TODO Just add record to Build queue/history table (or even tun? why if no free nodes? wait? Controller will hang and then timeout?)
		// TODO
	}

	@Override public boolean isEventApplicable(DefaultCIEngineEvent defaultCIEngineEvent)
	{
		return defaultCIEngineEvent != null && defaultCIEngineEvent instanceof OnQueueBuildEvent;
	}
}
