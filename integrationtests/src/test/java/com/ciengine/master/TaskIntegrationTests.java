package com.ciengine.master;

import com.ciengine.TestConfiguration;
import com.ciengine.master.dao.BuildDao;
import com.ciengine.master.facades.CIEngineFacade;
import com.ciengine.master.facades.ModuleFacade;
import com.ciengine.master.task.Task;
import com.ciengine.master.task.TaskEvaluator;
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
		assertFalse(tests.get(0).isInProgress());
		assertFalse(tests.get(1).isInProgress());
		assertFalse(javadocTask.isInProgress());
		assertFalse(deployTask.isInProgress());

		//
		createBinaries.setSuccess(true);
		createBinaries.setFinished(true);
		createBinaries.setInProgress(false);
		createSources.setSuccess(true);
		createSources.setFinished(true);
		createSources.setInProgress(false);
		taskEvaluator.evaluate(deployTask);

		assertTrue(tests.get(0).isInProgress());
		assertTrue(tests.get(1).isInProgress());
		assertTrue(javadocTask.isInProgress());
		assertFalse(deployTask.isInProgress());

		tests.get(0).setSuccess(true);
		tests.get(0).setFinished(true);
		tests.get(0).setInProgress(false);
		tests.get(1).setSuccess(true);
		tests.get(1).setFinished(true);
		tests.get(1).setInProgress(false);
		javadocTask.setSuccess(true);
		javadocTask.setFinished(true);
		javadocTask.setInProgress(false);

		taskEvaluator.evaluate(deployTask);
		assertTrue(deployTask.isInProgress());

		System.out.print(1);
	}

}
