package main.java.com.ciengine.master.listeners;

import main.java.com.ciengine.common.CIEngineEvent;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public interface CIEngineListener
{
	void onEvent(CIEngineEvent ciEngineEvent) throws CIEngineListenerException;
}
