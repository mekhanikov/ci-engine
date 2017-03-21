package com.ciengine.master;

import com.ciengine.TestConfiguration;
import com.ciengine.WaitForEventListener;
import com.ciengine.common.Module;
import com.ciengine.common.Repo;
import com.ciengine.common.dto.IsModuleReleasedRequest;
import com.ciengine.common.dto.IsModuleReleasedResponse;
import com.ciengine.master.dao.BuildDao;
import com.ciengine.master.facades.CIEngineFacade;
import com.ciengine.master.facades.ModuleFacade;
import com.ciengine.master.listeners.OnReleaseRule;
import com.ciengine.master.model.BuildModel;
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
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {TestConfiguration.class}, properties = "server.port=8081")
//@SpringBootTest(TestConfiguration.class)
//@SpringBootTest(classes = {TestConfiguration.class})
//@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
public class ReleaseIntegrationTests extends AbstractIntegrationTests {

	@Autowired
	CIEngineFacade ciEngineFacade;

	@Autowired
	private ModuleFacade moduleFacade;

//	@Autowired
//	OnCommitListener onCommitListener;
//
//	@Autowired
//	MockBinaryRepositoryClient mockBinaryRepositoryClient;

	@Autowired
	BuildDao buildDao;

//	@Autowired
//	BinaryRepositoryClient binaryRepositoryClient;

	/*
	Test data:
	Modules:
	ModA -> ModB, ModC
	ModB -> ModC
	Each has release branch

	Task (lives in memory or in DB):
	Release ModA 2.0
	Release ModB 2.0
	Release ModC 2.0


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
	public void test() throws Exception {
//		prepareMocks();
		prepareModules();
//		prepareOnCommitListener();
//		WaitForEventListener waitForEventListener = new WaitForEventListener(OnNewArtifactEvent.class);
//		ciEngineFacade.addListener(waitForEventListener);

		WaitForEventListener waitForEventListener = waitForCondition(ciEngineEvent -> {
//				IsModuleReleasedResponse isModuleReleasedResponse = ciEngineFacade.isModuleReleased(isModuleReleasedRequest);
//				return isModuleReleasedResponse.isReleased();
			return isModuleReleased("ModA:1.0") && isModuleReleased("ModB:1.0") && isModuleReleased("ModC:1.0");
		});

		submitRelease("ModA:1.0", "ModA:1.0,ModB:1.0,ModC:1.0");
		//Thread.sleep(6000);
		submitRelease("ModB:1.0", "ModA:1.0,ModB:1.0,ModC:1.0");
//		//Thread.sleep(6000);
		submitRelease("ModC:1.0", "ModA:1.0,ModB:1.0,ModC:1.0");
		//Thread.sleep(6000);
		//mockBinaryRepositoryClient.addModule("ModC:2.0");
//		submitRelease("ModB:2.0", "ModA:2.0,ModB:2.0,ModC:2.0");
//		submitRelease("ModA:2.0", "ModA:2.0,ModB:2.0,ModC:2.0");



		//		IsModuleReleasedRequest isModuleReleasedRequest = new IsModuleReleasedRequest();
//		isModuleReleasedRequest.setModule("");
//		IsMach isMach = ciEngineEvent -> {
////				IsModuleReleasedResponse isModuleReleasedResponse = ciEngineFacade.isModuleReleased(isModuleReleasedRequest);
////				return isModuleReleasedResponse.isReleased();
//            return ciEngineEvent instanceof OnNewArtifactEvent;
//        };


		waitForEventListener.waitEvent(45);
		assertTrue(waitForEventListener.isMach());
		List<BuildModel> buildModels = buildDao.getAll();
		System.out.println("********");
		for (BuildModel buildModel : buildModels) {
			System.out.println(buildModel);
			System.out.println("--------");
		}
		System.out.println("********");
	}

    @Test
	public void test3() throws Exception {
//		prepareMocks();
		prepareModules();
//		prepareOnCommitListener();
//		WaitForEventListener waitForEventListener = new WaitForEventListener(OnNewArtifactEvent.class);
//		ciEngineFacade.addListener(waitForEventListener);
		WaitForEventListener waitForEventListener = waitForCondition(ciEngineEvent -> {
//				IsModuleReleasedResponse isModuleReleasedResponse = ciEngineFacade.isModuleReleased(isModuleReleasedRequest);
//				return isModuleReleasedResponse.isReleased();
			return isModuleReleased("ModA:1.0") && isModuleReleased("ModB:1.0") && isModuleReleased("ModC:1.0");
		});

		submitRelease("ModA:3.0", "ModA:3.0,ModB:3.0,ModC:3.0");
		Thread.sleep(6000);
		submitRelease("ModB:3.0", "ModA:3.0,ModB:3.0,ModC:3.0");
		Thread.sleep(6000);
		submitRelease("ModC:3.0", "ModA:3.0,ModB:3.0,ModC:3.0");
		Thread.sleep(6000);
		//mockBinaryRepositoryClient.addModule("ModC:2.0");
//		submitRelease("ModB:2.0", "ModA:2.0,ModB:2.0,ModC:2.0");
//		submitRelease("ModA:2.0", "ModA:2.0,ModB:2.0,ModC:2.0");

		waitForEventListener.waitEvent(45);
		assertTrue(waitForEventListener.isMach());
	}

	@Test
	public void test2() throws Exception {
		prepareModules();

		WaitForEventListener waitForEventListener = waitForCondition(ciEngineEvent -> {
//				IsModuleReleasedResponse isModuleReleasedResponse = ciEngineFacade.isModuleReleased(isModuleReleasedRequest);
//				return isModuleReleasedResponse.isReleased();
			return isModuleReleased("ModA:1.0") && isModuleReleased("ModB:1.0") && isModuleReleased("ModC:1.0");
		});

		submitRelease("ModC:2.0", "ModA:2.0,ModB:2.0,ModC:2.0");
		Thread.sleep(6000);
		submitRelease("ModB:2.0", "ModA:2.0,ModB:2.0,ModC:2.0");
		Thread.sleep(6000);
		submitRelease("ModA:2.0", "ModA:2.0,ModB:2.0,ModC:2.0");
		waitForEventListener.waitEvent(45);
		assertTrue(waitForEventListener.isMach());
	}


//	private void prepareOnCommitListener()
//	{
//		List<OnCommitRule> onCommitRules = new ArrayList<>();
//		onCommitRules.add(createOnCommitRule("modA", "develop, feature/.*"));
////		onCommitRules.add(createOnCommitRule("modB", "develop"));
////		onCommitRules.add(createOnCommitRule("modC", "develop"));
//		onCommitListener.setRules(onCommitRules);
//	}
//
//	private OnCommitRule createOnCommitRule(String forModules, String forBranches)
//	{
//		OnCommitRule onCommitRule = new OnCommitRule();
////		onCommitRule.setDockerImageId();
////		onCommitRule.setApplyList();
////		onCommitRule.setEnvironmentVariables();
//		onCommitRule.setForBranches(forBranches);
//		onCommitRule.setForModules(forModules);
//		return onCommitRule;
//	}

	private void prepareModules() {
		moduleFacade.addModule(createModule("ModA", "ssh://git@repo.ru/mod-a"));
		moduleFacade.addModule(createModule("ModB", "ssh://git@repo.ru/mod-b"));
		moduleFacade.addModule(createModule("ModC", "ssh://git@repo.ru/mod-c"));

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

	private void submitRelease(String moduleNameToRelease,
							   String goingToRelease) {
		OnReleaseRule release = new OnReleaseRule();
		release.setModuleNameToRelease(moduleNameToRelease);
		release.setGoingToRelease(goingToRelease);
		release.setApplyList("mockReleaseList");
		release.setMergeFromCommitId("123");
		release.setReleaseBranchName("release/2.0");
		release.setDockerImageId("someDockerImageId");
		ciEngineFacade.submitRelease(release);
	}

	private boolean isModuleReleased(String s) {
		IsModuleReleasedRequest isModuleReleasedRequest = new IsModuleReleasedRequest();
		isModuleReleasedRequest.setModule(s);
		IsModuleReleasedResponse isModuleReleasedResponse = ciEngineFacade.isModuleReleased(isModuleReleasedRequest);
		return isModuleReleasedResponse.isReleased();
	}
}
