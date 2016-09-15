package com.ciengine.master.listeners;




import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.DefaultCIEngineEvent;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public interface CIEngineListener
{
	void onEvent(CIEngineEvent ciEngineEvent) throws CIEngineListenerException;

	boolean isEventApplicable(DefaultCIEngineEvent defaultCIEngineEvent);
}
