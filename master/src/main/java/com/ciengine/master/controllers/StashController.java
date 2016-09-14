package com.ciengine.master.controllers;


import com.ciengine.common.CIEngine;
import com.ciengine.common.CIEngineException;
import com.ciengine.common.events.OnCommitEvent;
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
