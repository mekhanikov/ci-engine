package com.ciengine.master;

import com.ciengine.common.*;
import com.ciengine.master.facades.CIAgentFacade;
import com.ciengine.master.model.BuildModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by emekhanikov on 14.09.2016.
 */
//@Transactional
@Component
public class MockCIAgentFacadeImpl implements CIAgentFacade
{
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private GitHubLookupService gitHubLookupServiceImpl;

	private Map<Integer, Future<String>> map = new HashMap<>();

	@Override
	public String getStatus(Node node, int id)
	{
		String result = "";
		Future<String> ciEngineList = map.get(id);
		try {
			result = ciEngineList.isDone() ? ciEngineList.get() : BuildStatus.IN_PROGRESS;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void run(BuildModel buildModel, Node node)
	{
		CIEngineList ciEngineList = (CIEngineList) applicationContext.getBean(buildModel.getExecutionList());
		EnvironmentVariables environmentVariables = new EnvironmentVariables();
		String[] lines = buildModel.getInputParams().split("\n");
		for (String line : lines) {
			String[] keyValue = line.split("=");
			environmentVariables.addProperty(keyValue[0], keyValue[1]);
		}

		Future<String> page = null;
		try {
			page = gitHubLookupServiceImpl.executeList(ciEngineList, environmentVariables);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (CIEngineStepException e) {
			e.printStackTrace();
		}
		map.put(buildModel.getId(), page);

	}

}
