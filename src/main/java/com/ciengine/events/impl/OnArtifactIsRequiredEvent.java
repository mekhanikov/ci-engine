package main.java.com.ciengine.events.impl;

import main.java.com.ciengine.events.CIEngineEvent;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class OnArtifactIsRequiredEvent implements CIEngineEvent
{
	private String groupId;
	private String artifactId;
	private String version; // wildcarts? more then one?

	// FROM OnComitEvent
	private String gitUrl;
	//private String comitId; // required?
	private String branchName;
}
