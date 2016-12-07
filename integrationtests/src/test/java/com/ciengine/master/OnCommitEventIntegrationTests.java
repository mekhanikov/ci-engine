package com.ciengine.master;

import com.ciengine.IsMach;
import com.ciengine.TestConfiguration;
import com.ciengine.WaitForEventListener;
import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.Module;
import com.ciengine.common.Repo;
import com.ciengine.common.dto.IsModuleReleasedRequest;
import com.ciengine.common.dto.IsModuleReleasedResponse;
import com.ciengine.common.events.OnCommitEvent;
import com.ciengine.common.events.OnNewArtifactEvent;
import com.ciengine.common.events.OnReleaseSubmitedEvent;
import com.ciengine.master.facades.CIEngineFacade;
import com.ciengine.master.listeners.impl.oncommit.OnCommitRule;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


/**
 * Mocked master-agent communication.
 */
//@ActiveProfiles("test")
//@Configuration
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {TestConfiguration.class}, properties = "server.port=8080")
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
		prepareMocks();
		prepareModules();

		OnCommitEvent onCommitEvent = new OnCommitEvent();
		onCommitEvent.setBranchName("develop");
		onCommitEvent.setGitUrl("ssh://git@repo.ru/mod-a");
		onCommitEvent.setComitId("1234");

		IsModuleReleasedRequest isModuleReleasedRequest = new IsModuleReleasedRequest();
		isModuleReleasedRequest.setModule("");
		IsMach isMach = ciEngineEvent -> {
//				IsModuleReleasedResponse isModuleReleasedResponse = ciEngineFacade.isModuleReleased(isModuleReleasedRequest);
//				return isModuleReleasedResponse.isReleased();
            return ciEngineEvent instanceof OnNewArtifactEvent;
        };
		WaitForEventListener waitForEventListener0 = waitForCondition(isMach);
		ciEngineFacade.onEvent(onCommitEvent);
		waitForEventListener0.waitEvent(5);
		assertTrue(waitForEventListener0.isMach());
	}


	@Test
	public void triggerBuildForModAFeature() throws Exception {
		prepareMocks();
		prepareModules();
		OnCommitEvent onCommitEvent = new OnCommitEvent();
		onCommitEvent.setBranchName("feature/AA-1234");
		onCommitEvent.setGitUrl("ssh://git@repo.ru/mod-a");
		onCommitEvent.setComitId("1234");
//		WaitForEventListener waitForEventListener = new WaitForEventListener(OnNewArtifactEvent.class);
//		ciEngineFacade.addListener(waitForEventListener);
		ciEngineFacade.onEvent(onCommitEvent);
//		CIEngineEvent ciEngineEvent = waitForEventListener.waitEvent(15);
//		Assert.assertTrue(ciEngineEvent instanceof OnNewArtifactEvent);
		// TODO Added pause because it is posible situation when even OnNewArtifactEvent is sitll sending to other listeners and in next test will recive event created in current.
		// TODO better to wait for some event but which one? no active builds? All events had been sent to all listeners?
		Thread.sleep(1000);
	}

	@Test
	public void dontTriggerBuildForModARelease() throws Exception {
		prepareModules();
		OnCommitEvent onCommitEvent = new OnCommitEvent();
		onCommitEvent.setBranchName("release");
		onCommitEvent.setGitUrl("ssh://git@repo.ru/mod-a");
		onCommitEvent.setComitId("1234");
//		WaitForEventListener waitForEventListener = new WaitForEventListener(OnNewArtifactEvent.class);
//		ciEngineFacade.addListener(waitForEventListener);
		ciEngineFacade.onEvent(onCommitEvent);
//		CIEngineEvent ciEngineEvent = waitForEventListener.waitEvent(15);
//		Assert.assertTrue(ciEngineEvent == null);
	}

	@Test
	public void dontTriggerBuildForModBDevelop() throws Exception {
		prepareModules();
		OnCommitEvent onCommitEvent = new OnCommitEvent();
		onCommitEvent.setBranchName("develop");
		onCommitEvent.setGitUrl("ssh://git@repo.ru/mod-b");
		onCommitEvent.setComitId("1234");
//		WaitForEventListener waitForEventListener = new WaitForEventListener(OnNewArtifactEvent.class);
//		ciEngineFacade.addListener(waitForEventListener);
		ciEngineFacade.onEvent(onCommitEvent);
//		CIEngineEvent ciEngineEvent = waitForEventListener.waitEvent(15);
//		Assert.assertTrue(ciEngineEvent == null);
	}



	private OnCommitRule createOnCommitRule(String forModules, String forBranches)
	{
		OnCommitRule onCommitRule = new OnCommitRule();
//		onCommitRule.setDockerImageId();
		onCommitRule.setApplyList("onCommitList");
//		onCommitRule.setEnvironmentVariables();
		onCommitRule.setForBranches(forBranches);
		onCommitRule.setForModules(forModules);
		return onCommitRule;
	}

	private void prepareModules() {
		List<Module> moduleList = new ArrayList<>();
		moduleList.add(createModule("modA", "ssh://git@repo.ru/mod-a"));
		moduleList.add(createModule("modB", "ssh://git@repo.ru/mod-b"));
//		moduleList.add(createModule("modC", "ssh://git@repo.ru/mod-c"));
		ciEngineFacade.setModules(moduleList);

	}

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

	private void prepareMocks()
	{
//		CIAgentFacade ciAgentFacade = Mockito.mock(CIAgentFacade.class);
//		buildStatusChecker.setCiAgentFacade(ciAgentFacade);
//		buildRunner.setCiAgentFacade(ciAgentFacade);
	}

	private WaitForEventListener waitForCondition(IsMach isMach) {
		WaitForEventListener waitForEventListener = new WaitForEventListener(isMach);
		ciEngineFacade.addListener(waitForEventListener);
		return waitForEventListener;
	}
}
