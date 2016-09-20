package com.ciengine.master.controllers.stash;


import com.ciengine.common.CIEngineException;
import com.ciengine.common.events.OnCommitEvent;
import com.ciengine.master.facades.CIEngineFacade;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class StashController
{
	@Autowired
	private CIEngineFacade ciEngineFacade;

	public void onEvent() {
		// TODO set from inpit params
		OnCommitEvent onCommitEvent = new OnCommitEvent();
		try
		{
			ciEngineFacade.submitEvent(onCommitEvent);
		}
		catch (CIEngineException e)
		{
			e.printStackTrace();
		}
	}
}
