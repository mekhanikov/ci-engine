package main.java.com.ciengine.listeners;

import main.java.com.ciengine.events.CIEngineEvent;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public interface CIEngineListener
{
	void onEvent(CIEngineEvent ciEngineEvent) throws CIEngineListenerException;
}
