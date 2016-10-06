package com.ciengine.master;

import com.ciengine.common.*;
import com.ciengine.master.facades.CIAgentFacade;
import com.ciengine.master.model.BuildModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
	private AsyncListExecutor asyncListExecutorImpl;

	private Map<Integer, Future<String>> map = new HashMap<>();

	@Override
	public String getStatus(Node node, int id)
	{
		String result = "";
		Future<String> ciEngineList = map.get(id);
		if (ciEngineList != null) {
			try {
				result = ciEngineList.isDone() ? ciEngineList.get() : BuildStatus.IN_PROGRESS;
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	@Override
	public void run(BuildModel buildModel, Node node)
	{
		CIEngineList ciEngineList = (CIEngineList) applicationContext.getBean(buildModel.getExecutionList());
		EnvironmentVariables environmentVariables = new EnvironmentVariables();
		if (!StringUtils.isEmpty(buildModel.getInputParams())) {
			String[] lines = buildModel.getInputParams().split("\n");
			for (String line : lines) {
				if (!StringUtils.isEmpty(line)) {
					String[] keyValue = line.split("=");
					String key = keyValue.length > 0 ? keyValue[0] : "";
					String value = keyValue.length > 1 ? keyValue[1] : "";
					environmentVariables.addProperty(key, value);
				}
			}
		}


		Future<String> page = null;
		try {
			page = asyncListExecutorImpl.executeList(ciEngineList, environmentVariables);
		} catch (InterruptedException | CIEngineStepException e) {
			e.printStackTrace();
		}
		map.put(buildModel.getId(), page);

	}

}
