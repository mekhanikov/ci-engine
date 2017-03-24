package com.ciengine.master;

import com.ciengine.TestConfiguration;
import com.ciengine.common.BuildStatus;
import com.ciengine.master.dao.BuildDao;
import com.ciengine.master.facades.CIEngineFacade;
import com.ciengine.master.facades.ModuleFacade;
import com.ciengine.master.task.BuildTask;
import com.ciengine.master.task.Task;
import com.ciengine.master.task.TaskEvaluator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
		Task createBinaries = new BuildTask("createBinaries");
		Task createSources = new BuildTask("createSources");

		List<Task> tests = new ArrayList<>();
		for(int i=0; i < 2; i++) {
			Task task = new BuildTask("test" +i);
			task.dependsOn(createBinaries, createSources);
			tests.add(task);
		}

		Task javadocTask = new BuildTask("javadocTask");
		javadocTask.dependsOn(createBinaries, createSources);

		Task deployTask = new BuildTask("deployTask");

		Task[] myArray = tests.toArray(new Task[0]);
		deployTask.dependsOn(createBinaries, createSources, javadocTask);
		deployTask.dependsOn(myArray);

		//
		TaskEvaluator taskEvaluator =	new TaskEvaluator();
		taskEvaluator.evaluate(deployTask);
		// Test
		assertEquals(BuildStatus.IN_PROGRESS, createBinaries.getStatus());
		assertEquals(BuildStatus.IN_PROGRESS, createSources.getStatus());
		assertEquals(BuildStatus.QUEUED, tests.get(0).getStatus());
		assertEquals(BuildStatus.QUEUED, tests.get(1).getStatus());
		assertEquals(BuildStatus.QUEUED, javadocTask.getStatus());
		assertEquals(BuildStatus.QUEUED, deployTask.getStatus());

		//
		createBinaries.setStatus(BuildStatus.SUCCESS);
		createSources.setStatus(BuildStatus.SUCCESS);
		taskEvaluator.evaluate(deployTask);

		assertEquals(BuildStatus.IN_PROGRESS, tests.get(0).getStatus());
		assertEquals(BuildStatus.IN_PROGRESS, tests.get(1).getStatus());
		assertEquals(BuildStatus.IN_PROGRESS, javadocTask.getStatus());
		assertEquals(BuildStatus.QUEUED, deployTask.getStatus());

		tests.get(0).setStatus(BuildStatus.SUCCESS);
		tests.get(1).setStatus(BuildStatus.SUCCESS);
		javadocTask.setStatus(BuildStatus.SUCCESS);


		taskEvaluator.evaluate(deployTask);
		assertEquals(BuildStatus.IN_PROGRESS, deployTask.getStatus());

		System.out.print(1);
	}

}
