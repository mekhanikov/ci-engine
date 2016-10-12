//package com.ciengine.master;
//
//
//
//import com.ciengine.common.CIEngineClient;
//import com.ciengine.common.CIEngineList;
//import com.ciengine.common.CIEngineStepException;
//import com.ciengine.common.EnvironmentVariables;
//import com.ciengine.common.events.OnNewArtifactEvent;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//
///**
// * Created by emekhanikov on 05.09.2016.
// */
//@Component
//public class MockList implements CIEngineList
//{
//	@Autowired
//	private CIEngineClient ciEngineClient;
//
//	@Override public void doList(EnvironmentVariables environmentVariables) throws CIEngineStepException
//	{
//System.out.print("d");
//		try {
//			Thread.sleep(5);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		//throw new CIEngineStepException("");
//		OnNewArtifactEvent onNewArtifactEvent = new OnNewArtifactEvent();
//
////		String gitUrl = environmentVariables.getProperty(EnvironmentVariablesConstants.GIT_URL);
////		String branchName = environmentVariables.getProperty(EnvironmentVariablesConstants.BRANCH_NAME);
////		String commitId = environmentVariables.getProperty(EnvironmentVariablesConstants.COMMIT_ID);
////		onNewArtifactEvent.setComitId(commitId);
////		onNewArtifactEvent.setGitUrl(gitUrl);
////		onNewArtifactEvent.setBranchName(branchName);
//		ciEngineClient.sendEvent(onNewArtifactEvent);
//	}
//}
