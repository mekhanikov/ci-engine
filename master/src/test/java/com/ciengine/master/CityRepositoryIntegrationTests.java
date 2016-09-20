package com.ciengine.master;

import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.DefaultCIEngineEvent;
import com.ciengine.common.events.OnCommitEvent;
import com.ciengine.common.events.OnNewArtifactEvent;
import com.ciengine.master.controllers.addbuild.AddBuildRequest;
import com.ciengine.master.facades.CIEngineFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest
public class CityRepositoryIntegrationTests {

	@Autowired
	CIEngineFacade ciEngineFacade;


	/*
	Triger on commitEvent to develop of module-a ->
	- OnCommitListener will find executionList and trigger OnQueueBuildEvent->OnQueueBuildListener -> addBuildToDB
	- async in BuildRunnerImpl the build wll be executed on the Node (need mock it, no real node is required)
	- async in BuildStatusCheckerImpl build status will be changed on success or failed -> BuildFinishedEvent or so.
	- wait for BuildFinishedEvent for some time (timeout)
	 */
	@Test
	public void testName() throws Exception {
		//ciEngineFacade.addListener();
		OnCommitEvent onCommitEvent = new OnCommitEvent();
		AddBuildRequest addBuildRequest = new AddBuildRequest();
		addBuildRequest.setNodeId("1");
		onCommitEvent.setBranchName("develop");
		onCommitEvent.setGitUrl("setGitUrl");
		onCommitEvent.setComitId("setComitId");
//		WaitForEventListener waitForEventListener = new WaitForEventListener(OnNewArtifactEvent.class);
		WaitForEventListener waitForEventListener = new WaitForEventListener(OnCommitEvent.class);
		ciEngineFacade.addListener(waitForEventListener);
		ciEngineFacade.onEvent(onCommitEvent);
		CIEngineEvent ciEngineEvent = waitForEventListener.waitEvent();
		assertTrue(ciEngineEvent instanceof OnCommitEvent);
		System.out.println("Hello World!");
//		long timeout = 5000;
//		DefaultCIEngineEvent defaultCIEngineEvent = waitForEvent(DefaultCIEngineEvent.class, timeout);
//		System.out.println("Hello World!");
//		assertTrue(true);
	}

	private DefaultCIEngineEvent waitForEvent(Class<DefaultCIEngineEvent> defaultCIEngineEventClass, long timeout)
	{

		// todo create customlistener
		return null;
	}
}
