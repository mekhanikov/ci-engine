package com.ciengine.agent.lists.current;



import com.ciengine.agent.lists.CIEngineList;
import com.ciengine.agent.steps.CIEngineStep;
import com.ciengine.agent.steps.CIEngineStepException;
import com.ciengine.agent.steps.impl.AttachArtefactsStep;
import com.ciengine.agent.steps.impl.BuildStep;
import com.ciengine.agent.steps.impl.CheckoutStep;
import com.ciengine.common.EnvironmentVariables;
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
