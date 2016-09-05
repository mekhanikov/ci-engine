package main.java.com.ciengine.lists.current;

import main.java.com.ciengine.EnvironmentVariables;
import main.java.com.ciengine.lists.CIEngineList;
import main.java.com.ciengine.steps.CIEngineStepException;
import main.java.com.ciengine.steps.impl.CheckoutStep;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class OnCommitList implements CIEngineList
{
	@Override public void doList(EnvironmentVariables environmentVariables) throws CIEngineStepException
	{
		executeStep(new CheckoutStep(), environmentVariables);
		executeStep(new CheckoutStep(), environmentVariables);
	}

	private void executeStep(CheckoutStep checkoutStep, EnvironmentVariables environmentVariables)
	{
		try
		{
			checkoutStep.doStep(environmentVariables);
		}
		catch (CIEngineStepException e)
		{
			e.printStackTrace();
		} finally
		{
			// TODO save environmentVariables in build queue.
		}
	}
}
