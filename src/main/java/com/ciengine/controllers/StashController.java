package main.java.com.ciengine.controllers;

import main.java.com.ciengine.CIEngine;
import main.java.com.ciengine.CIEngineException;
import main.java.com.ciengine.events.impl.OnCommitEvent;
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
