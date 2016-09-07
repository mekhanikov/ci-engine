package main.java.com.ciengine.master.events.impl;

import main.java.com.ciengine.common.CIEngineEvent;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class OnNewArtifactEvent implements CIEngineEvent
{
	private String groupId;
	private String artifactId;
	private String deployVersion;

	// FROM OnComitEvent
	private String gitUrl;
	private String comitId;
	private String branchName;

	private String buildId;

	//private String time;// ????

}
