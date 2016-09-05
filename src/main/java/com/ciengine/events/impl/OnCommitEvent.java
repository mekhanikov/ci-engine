package main.java.com.ciengine.events.impl;

import main.java.com.ciengine.events.CIEngineEvent;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class OnCommitEvent implements CIEngineEvent
{
	private String gitUrl;
	private String comitId;
	private String branchName;
}
