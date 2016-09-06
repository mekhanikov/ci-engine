package main.java.com.ciengine.lists;

import main.java.com.ciengine.EnvironmentVariables;
import main.java.com.ciengine.lists.current.OnCommitList;
import main.java.com.ciengine.steps.CIEngineStepException;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by emekhanikov on 06.09.2016.
 */
public class ListExecutor
{// TODO entry point for Agent.
	@Autowired
	private OnCommitList onCommitList;

	public void run() {
		try
		{
			EnvironmentVariables environmentVariables = new EnvironmentVariables(); // TODO load from file
			onCommitList.doList(environmentVariables);
		}
		catch (CIEngineStepException e)
		{
			e.printStackTrace();
		} finally
		{
			// TODO save environmentVariables in build queue.
			// TODO extract BUILS_STATUS and save it in build queue "status" field.
		}
	}
}
