package com.ciengine.agent.lists.current;



import com.ciengine.common.*;
import com.ciengine.agent.steps.impl.AttachArtefactsStep;
import com.ciengine.agent.steps.impl.BuildStep;
import com.ciengine.agent.steps.impl.CheckoutStep;
import com.ciengine.agent.steps.impl.NewArtefactsReleasedStep;
import com.ciengine.common.events.OnNewArtifactEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by emekhanikov on 05.09.2016.
 */
@Component
public class OnCommitList implements CIEngineList
{
	@Autowired
	private CheckoutStep checkoutStep;

	@Autowired
	private BuildStep buildStep;

	@Autowired
	private AttachArtefactsStep attachArtefactsStep;

	@Autowired
	private NewArtefactsReleasedStep newArtefactsReleasedStep;

	@Autowired
	private CIEngineClient ciEngineClient;

	@Override public void doList(EnvironmentVariables environmentVariables) throws CIEngineStepException
	{
		/*
# TODO it is steps should be here
#RUN git clone ssh://git@stash.hybris.com:7999/platform/atdd.git source
#RUN cp source/pom.xml ./
#RUN mvn clean install > logs.txt
		 */
		executeStep(checkoutStep, environmentVariables);// TODO or by name executeSteps(environmentVariables, "CHECKOUT", "BUILD", ...)
		executeStep(buildStep, environmentVariables);   // TODO or by name executeSteps(environmentVariables, "CHECKOUT", "BUILD", ...)

		environmentVariables.addProperty("BUILD_STATUS", "OK"); // TODO or ciEngineClient.setBuildStatus(); ?
		executeStep(attachArtefactsStep, environmentVariables);
		executeStep(newArtefactsReleasedStep, environmentVariables);

		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//throw new CIEngineStepException("");
		OnNewArtifactEvent onNewArtifactEvent = new OnNewArtifactEvent();

//		String gitUrl = environmentVariables.getProperty("GIT_URL");
//		String branchName = environmentVariables.getProperty("BRANCH_NAME");
//		String commitId = environmentVariables.getProperty("COMMIT_ID");
//		onNewArtifactEvent.setComitId(commitId);
//		onNewArtifactEvent.setGitUrl(gitUrl);
//		onNewArtifactEvent.setBranchName(branchName);
		ciEngineClient.sendEvent(onNewArtifactEvent);
	}

	private void executeStep(CIEngineStep checkoutStep, EnvironmentVariables environmentVariables) throws CIEngineStepException
	{
		checkoutStep.doStep(environmentVariables);
	}
}
