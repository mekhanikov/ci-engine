package main.java.com.ciengine.lists.current;

import main.java.com.ciengine.EnvironmentVariables;
import main.java.com.ciengine.client.CIEngineClient;
import main.java.com.ciengine.lists.CIEngineList;
import main.java.com.ciengine.steps.CIEngineStep;
import main.java.com.ciengine.steps.CIEngineStepException;
import main.java.com.ciengine.steps.impl.AttachArtefactsStep;
import main.java.com.ciengine.steps.impl.CheckoutStep;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class OnCommitList implements CIEngineList
{
	@Autowired
	private CheckoutStep checkoutStep;

	@Autowired
	private AttachArtefactsStep attachArtefactsStep;

	@Override public void doList(EnvironmentVariables environmentVariables) throws CIEngineStepException
	{

		executeStep(checkoutStep, environmentVariables);

		environmentVariables.addProperty("BUILD_STATUS", "OK"); // TODO or ciEngineClient.setBuildStatus(); ?
		// TODO upload logs (all at the end? partialy?)
		executeStep(attachArtefactsStep, environmentVariables);
	}

	private void executeStep(CIEngineStep checkoutStep, EnvironmentVariables environmentVariables) throws CIEngineStepException
	{
		checkoutStep.doStep(environmentVariables);
	}
}
