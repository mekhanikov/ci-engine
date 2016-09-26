package com.ciengine.master;

import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.DefaultCIEngineEvent;
import com.ciengine.common.Module;
import com.ciengine.common.Repo;
import com.ciengine.common.events.OnCommitEvent;
import com.ciengine.common.events.OnNewArtifactEvent;
import com.ciengine.master.facades.CIAgentFacade;
import com.ciengine.master.facades.CIEngineFacade;
import com.ciengine.master.listeners.impl.oncommit.OnCommitListener;
import com.ciengine.master.listeners.impl.oncommit.OnCommitRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest
public class CityRepositoryIntegrationTests {

	@Autowired
	CIEngineFacade ciEngineFacade;

	@Autowired
	OnCommitListener onCommitListener;

	@Autowired
	BuildStatusCheckerImpl buildStatusChecker;


	@Autowired
	BuildRunnerImpl buildRunner;


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
		- OnCommitListener will find executionList and trigger OnQueueBuildEvent->OnQueueBuildListener -> addBuildToDB
		- async in BuildRunnerImpl the build wll be executed on the Node (need mock it, no real node is required)
		- async in BuildStatusCheckerImpl build status will be changed on success or failed -> BuildFinishedEvent or so.
		- wait for BuildFinishedEvent for some time (timeout)
		 */
	@Test
	public void triggerBuildForModADevelop() throws Exception {
		prepareMocks();
		prepareModules();
		prepareOnCommitListener();

		//ciEngineFacade.addListener();
		OnCommitEvent onCommitEvent = new OnCommitEvent();
//		AddBuildRequest addBuildRequest = new AddBuildRequest();
//		addBuildRequest.setNodeId("1");
		onCommitEvent.setBranchName("develop");
		onCommitEvent.setGitUrl("ssh://git@repo.ru/mod-a");
		onCommitEvent.setComitId("1234");

//		WaitForEventListener waitForEventListener = new WaitForEventListener(OnNewArtifactEvent.class);
		WaitForEventListener waitForEventListener = new WaitForEventListener(OnNewArtifactEvent.class);
		ciEngineFacade.addListener(waitForEventListener);
		ciEngineFacade.onEvent(onCommitEvent);
		CIEngineEvent ciEngineEvent = waitForEventListener.waitEvent(15);
		assertTrue(ciEngineEvent instanceof OnNewArtifactEvent);
		//System.out.println("Hello World!");
//		long timeout = 5000;
//		DefaultCIEngineEvent defaultCIEngineEvent = waitForEvent(DefaultCIEngineEvent.class, timeout);
//		System.out.println("Hello World!");
//		assertTrue(true);
	}

	@Test
	public void triggerBuildForModAFeature() throws Exception {
		prepareMocks();
		prepareModules();
		prepareOnCommitListener();
		OnCommitEvent onCommitEvent = new OnCommitEvent();
		onCommitEvent.setBranchName("feature/AA-1234");
		onCommitEvent.setGitUrl("ssh://git@repo.ru/mod-a");
		onCommitEvent.setComitId("1234");
		WaitForEventListener waitForEventListener = new WaitForEventListener(OnNewArtifactEvent.class);
		ciEngineFacade.addListener(waitForEventListener);
		ciEngineFacade.onEvent(onCommitEvent);
		CIEngineEvent ciEngineEvent = waitForEventListener.waitEvent(15);
		assertTrue(ciEngineEvent instanceof OnNewArtifactEvent);
	}

	@Test
	public void dontTriggerBuildForModBDevelop() throws Exception {
		prepareModules();
		prepareOnCommitListener();
		OnCommitEvent onCommitEvent = new OnCommitEvent();
		onCommitEvent.setBranchName("develop");
		onCommitEvent.setGitUrl("ssh://git@repo.ru/mod-b");
		onCommitEvent.setComitId("1234");
		WaitForEventListener waitForEventListener = new WaitForEventListener(OnNewArtifactEvent.class);
		ciEngineFacade.addListener(waitForEventListener);
		ciEngineFacade.onEvent(onCommitEvent);
		CIEngineEvent ciEngineEvent = waitForEventListener.waitEvent(15);
		assertTrue(ciEngineEvent == null);
	}

	private void prepareOnCommitListener()
	{
		List<OnCommitRule> onCommitRules = new ArrayList<>();
		onCommitRules.add(createOnCommitRule("modA", "develop, feature/.*"));
//		onCommitRules.add(createOnCommitRule("modB", "develop"));
//		onCommitRules.add(createOnCommitRule("modC", "develop"));
		onCommitListener.setRules(onCommitRules);
	}

	private OnCommitRule createOnCommitRule(String forModules, String forBranches)
	{
		OnCommitRule onCommitRule = new OnCommitRule();
//		onCommitRule.setDockerImageId();
//		onCommitRule.setApplyList();
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

	private DefaultCIEngineEvent waitForEvent(Class<DefaultCIEngineEvent> defaultCIEngineEventClass, long timeout)
	{

		// todo create customlistener
		return null;
	}

	private void prepareMocks()
	{
		CIAgentFacade ciAgentFacade = Mockito.mock(CIAgentFacade.class);
		buildStatusChecker.setCiAgentFacade(ciAgentFacade);
		buildRunner.setCiAgentFacade(ciAgentFacade);
	}
}
