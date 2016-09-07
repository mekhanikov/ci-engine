package main.java.com.ciengine.agent.lists.current;

import main.java.com.ciengine.common.EnvironmentVariables;
import main.java.com.ciengine.agent.lists.CIEngineList;
import main.java.com.ciengine.agent.steps.CIEngineStep;
import main.java.com.ciengine.agent.steps.CIEngineStepException;
import main.java.com.ciengine.agent.steps.impl.AttachArtefactsStep;
import main.java.com.ciengine.agent.steps.impl.BuildStep;
import main.java.com.ciengine.agent.steps.impl.CheckoutStep;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class OnCommitList implements CIEngineList
{
	@Autowired
	private CheckoutStep checkoutStep;

	@Autowired
	private BuildStep buildStep;

	@Autowired
	private AttachArtefactsStep attachArtefactsStep;

	@Override public void doList(EnvironmentVariables environmentVariables) throws CIEngineStepException
	{
		executeStep(checkoutStep, environmentVariables);// TODO or by name executeSteps(environmentVariables, "CHECKOUT", "BUILD", ...)
		executeStep(buildStep, environmentVariables);   // TODO or by name executeSteps(environmentVariables, "CHECKOUT", "BUILD", ...)

		environmentVariables.addProperty("BUILD_STATUS", "OK"); // TODO or ciEngineClient.setBuildStatus(); ?
		// TODO upload logs (all at the end? partialy?)
		executeStep(attachArtefactsStep, environmentVariables);
	}

	private void executeStep(CIEngineStep checkoutStep, EnvironmentVariables environmentVariables) throws CIEngineStepException
	{
		checkoutStep.doStep(environmentVariables);
	}
}
