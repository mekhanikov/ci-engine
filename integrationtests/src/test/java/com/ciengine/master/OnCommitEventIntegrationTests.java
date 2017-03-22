package com.ciengine.master;

import com.ciengine.IsMach;
import com.ciengine.TestConfiguration;
import com.ciengine.WaitForEventListener;
import com.ciengine.common.Module;
import com.ciengine.common.Repo;
import com.ciengine.common.events.OnCommitEvent;
import com.ciengine.common.events.OnNewArtifactEvent;
import com.ciengine.master.facades.CIEngineFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Mocked master-agent communication.
 */
//@ActiveProfiles("test")
//@Configuration
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {TestConfiguration.class}, properties = "server.port=8081")
//@SpringBootTest(classes = {TestConfiguration.class})
//@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
//@PropertySource(value = { "classpath:application.properties" })
public class OnCommitEventIntegrationTests {

	@Autowired
	CIEngineFacade ciEngineFacade;

	//@Autowired
	//OnCommitListener onCommitListener;

	/*
	Test data:
	Modules:
	ModA -> ModB, ModC
	ModB -> ModC
	Each has develop branch
	On commit to develop of ModC -> execute exection list
	 */

		/*
		Triger on commitEvent to develop of module-a ->
		- OnCommitListener will find executionList and enqueue build
		- async in BuildRunner the build wll be executed on the Node (need mock it, no real node is required)
		- async in BuildStatusChecker build status will be changed on success or failed -> BuildFinishedEvent or so.
		- wait for BuildFinishedEvent for some time (timeout)
		 */
	@Test
	public void triggerBuildForModADevelop() throws Exception {
		OnCommitEvent onCommitEvent = new OnCommitEvent();
		onCommitEvent.setBranchName("develop");
		onCommitEvent.setGitUrl("ssh://git@repo.ru/mod-a");
		onCommitEvent.setComitId("1234");

//		IsModuleReleasedRequest isModuleReleasedRequest = new IsModuleReleasedRequest();
//		isModuleReleasedRequest.setModule("");
//		IsMach isMach = ciEngineEvent -> {
////				IsModuleReleasedResponse isModuleReleasedResponse = ciEngineFacade.isModuleReleased(isModuleReleasedRequest);
////				return isModuleReleasedResponse.isReleased();
//            return ciEngineEvent instanceof OnNewArtifactEvent;
//        };
		WaitForEventListener waitForEventListener = waitForCondition(ciEngineEvent -> {
//				IsModuleReleasedResponse isModuleReleasedResponse = ciEngineFacade.isModuleReleased(isModuleReleasedRequest);
//				return isModuleReleasedResponse.isReleased();
			return ciEngineEvent instanceof OnNewArtifactEvent;
		});
		ciEngineFacade.onEvent(onCommitEvent);
		waitForEventListener.waitEvent(5);
		assertTrue(waitForEventListener.isMach());
	}


	@Test
	public void triggerBuildForModAFeature() throws Exception {
		OnCommitEvent onCommitEvent = new OnCommitEvent();
		onCommitEvent.setBranchName("feature/AA-1234");
		onCommitEvent.setGitUrl("ssh://git@repo.ru/mod-a");
		onCommitEvent.setComitId("1234");
//		WaitForEventListener waitForEventListener = new WaitForEventListener(OnNewArtifactEvent.class);
//		ciEngineFacade.addListener(waitForEventListener);

//        IsMach isMach = ciEngineEvent -> {
////				IsModuleReleasedResponse isModuleReleasedResponse = ciEngineFacade.isModuleReleased(isModuleReleasedRequest);
////				return isModuleReleasedResponse.isReleased();
//            return ciEngineEvent instanceof OnNewArtifactEvent;
//        };
        WaitForEventListener waitForEventListener = waitForCondition(ciEngineEvent -> {
//				IsModuleReleasedResponse isModuleReleasedResponse = ciEngineFacade.isModuleReleased(isModuleReleasedRequest);
//				return isModuleReleasedResponse.isReleased();
			return ciEngineEvent instanceof OnNewArtifactEvent;
		});
		ciEngineFacade.onEvent(onCommitEvent);
        waitForEventListener.waitEvent(5);
        assertTrue(waitForEventListener.isMach());
	}

	@Test
	public void dontTriggerBuildForModARelease() throws Exception {
		OnCommitEvent onCommitEvent = new OnCommitEvent();
		onCommitEvent.setBranchName("release");
		onCommitEvent.setGitUrl("ssh://git@repo.ru/mod-a");
		onCommitEvent.setComitId("1234");
		WaitForEventListener waitForEventListener = waitForCondition(ciEngineEvent -> {
//				IsModuleReleasedResponse isModuleReleasedResponse = ciEngineFacade.isModuleReleased(isModuleReleasedRequest);
//				return isModuleReleasedResponse.isReleased();
			if (ciEngineEvent instanceof OnNewArtifactEvent) {
				OnNewArtifactEvent onNewArtifactEvent = (OnNewArtifactEvent)ciEngineEvent;
				return "release".equals(onNewArtifactEvent.getBranchName());
			}
			return false;
			//return ciEngineEvent instanceof OnNewArtifactEvent;
		});
		ciEngineFacade.onEvent(onCommitEvent);
		waitForEventListener.waitEvent(5);
		assertFalse(waitForEventListener.isMach());
	}

	@Test
	public void dontTriggerBuildForModBDevelop() throws Exception {
		OnCommitEvent onCommitEvent = new OnCommitEvent();
		onCommitEvent.setBranchName("develop");
		onCommitEvent.setGitUrl("ssh://git@repo.ru/mod-b");
		onCommitEvent.setComitId("1234");
		WaitForEventListener waitForEventListener = waitForCondition(ciEngineEvent -> {
//				IsModuleReleasedResponse isModuleReleasedResponse = ciEngineFacade.isModuleReleased(isModuleReleasedRequest);
//				return isModuleReleasedResponse.isReleased();
			return ciEngineEvent instanceof OnNewArtifactEvent;
		});
		ciEngineFacade.onEvent(onCommitEvent);
		waitForEventListener.waitEvent(5);
		assertFalse(waitForEventListener.isMach());
	}


//
//	private OnCommitRule createOnCommitRule(String forModules, String forBranches)
//	{
//		OnCommitRule onCommitRule = new OnCommitRule();
////		onCommitRule.setDockerImageId();
//		onCommitRule.setApplyList("onCommitList");
////		onCommitRule.setEnvironmentVariables();
//		onCommitRule.setForBranches(forBranches);
//		onCommitRule.setForModules(forModules);
//		return onCommitRule;
//	}


	private Module createModule(String modName, String gitUrl)
	{
		Module module = new Module();
		module.setName(modName);
		Repo repo = new Repo();
		repo.setName("origin");
		repo.setGitUrl(gitUrl);
		List<Repo> repos = new ArrayList<>();
		repos.add(repo);
		module.setRepoList(repos);
		return module;
	}


	private WaitForEventListener waitForCondition(IsMach isMach) {
		WaitForEventListener waitForEventListener = new WaitForEventListener(isMach);
		ciEngineFacade.addListener(waitForEventListener);
		return waitForEventListener;
	}

}
