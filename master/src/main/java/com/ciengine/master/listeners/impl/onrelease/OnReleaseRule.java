package com.ciengine.master.listeners.impl.onrelease;



import com.ciengine.common.EnvironmentVariables;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class OnReleaseRule
{
	private String moduleNameToRelease;
	private String mergeFromCommitId; // commit it (e.g. on develop branch) from it merge to release.
	private String releaseBranchName; // e.g. release/2.0

	private String goingToRelease; // list of modules. Used by list, if it see module depends on some modu;es from list
	private String applyList;
	private String dockerImageId;
	private EnvironmentVariables environmentVariables;

}
