package com.ciengine.agent.lists;


import com.ciengine.agent.lists.current.OnCommitList;
import com.ciengine.agent.steps.CIEngineStepException;
import com.ciengine.agent.steps.impl.NewArtefactsReleasedStep;
import com.ciengine.common.EnvironmentVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by emekhanikov on 06.09.2016.
 */
@Component
public class ListExecutor
{// TODO entry point for Agent.
	@Autowired
	private OnCommitList onCommitList;

	@Autowired
	Environment environment;

	public void run() {
		try
		{
			// TODO find Bean of CIEngineList based on bean name in EnvironmentVariables.EXECUTION_LIST
			System.out.println("*********************");

			System.out.println("*********************");
			EnvironmentVariables environmentVariables = getEnvironmentVariables(); // TODO load from file
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

	private EnvironmentVariables getEnvironmentVariables()
	{
		EnvironmentVariables environmentVariables =  new EnvironmentVariables();
		Map<String, Object> map = new HashMap();
		for(Iterator it = ((AbstractEnvironment) environment).getPropertySources().iterator(); it.hasNext(); ) {
			PropertySource propertySource = (PropertySource) it.next();
			if (propertySource instanceof MapPropertySource) {
				environmentVariables.addProperties(((MapPropertySource) propertySource).getSource());
			}
		}
		System.out.println(map);
		//environment.getRequiredProperty("a");
		return environmentVariables;
	}
}
