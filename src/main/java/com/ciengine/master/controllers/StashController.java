package main.java.com.ciengine.master.controllers;

import main.java.com.ciengine.common.CIEngine;
import main.java.com.ciengine.common.CIEngineException;
import main.java.com.ciengine.master.events.impl.OnCommitEvent;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class StashController
{
	@Autowired
	private CIEngine ciEngine;

	public void onEvent() {
		// TODO set from inpit params
		OnCommitEvent onCommitEvent = new OnCommitEvent();
		try
		{
			ciEngine.submitEvent(onCommitEvent);
		}
		catch (CIEngineException e)
		{
			e.printStackTrace();
		}
	}
}
