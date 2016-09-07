package main.java.com.ciengine.master.listeners.impl.onqueuebuild;

import main.java.com.ciengine.common.CIEngineEvent;
import main.java.com.ciengine.master.events.impl.OnQueueBuildEvent;
import main.java.com.ciengine.master.listeners.CIEngineListener;
import main.java.com.ciengine.master.listeners.CIEngineListenerException;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class OnQueueBuildListener implements CIEngineListener
{
	@Override public void onEvent(CIEngineEvent ciEngineEvent) throws CIEngineListenerException
	{
		OnQueueBuildEvent onQueueBuildEvent = (OnQueueBuildEvent) ciEngineEvent;
		// TODO Just add record to Build queue/history table (or even tun? why if no free nodes? wait? Controller will hang and then timeout?)
		// TODO
	}
}
