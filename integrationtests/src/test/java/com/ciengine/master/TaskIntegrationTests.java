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

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {TestConfiguration.class}, properties = "server.port=8081")
public class TaskIntegrationTests extends AbstractIntegrationTests {

	@Autowired
	CIEngineFacade ciEngineFacade;

	@Autowired
	private ModuleFacade moduleFacade;

	@Autowired
	BuildDao buildDao;

	@Test
	public void test() throws Exception {
		Task createBinaries = new Task("createBinaries");
		Task createSources = new Task("createSources");

		List<Task> tests = new ArrayList<>();
		for(int i=0; i < 2; i++) {
			Task task = new Task("test" +i);
			task.dependsOn(createBinaries, createSources);
			tests.add(task);
		}

		Task javadocTask = new Task("javadocTask");
		javadocTask.dependsOn(createBinaries, createSources);

		Task deployTask = new Task("deployTask");

		Task[] myArray = tests.toArray(new Task[0]);
		deployTask.dependsOn(createBinaries, createSources, javadocTask);
		deployTask.dependsOn(myArray);

		//
		TaskEvaluator taskEvaluator =	new TaskEvaluator();
		taskEvaluator.evaluate(deployTask);
		// Test
		assertTrue(createBinaries.isInProgress());
		assertTrue(createSources.isInProgress());

		System.out.print(1);
	}

}
