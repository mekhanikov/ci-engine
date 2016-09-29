package com.ciengine.master;

import com.ciengine.common.CIEngineList;
import com.ciengine.common.CIEngineStepException;
import com.ciengine.common.Node;
import com.ciengine.master.facades.CIAgentFacade;
import com.ciengine.master.model.BuildModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
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

	private Map<Integer, CIEngineList> map = new HashMap<>();

	@Override
	public String getStatus(Node node, int id)
	{
		String result = "";
		CIEngineList ciEngineList = map.get(id);

		return result;
	}

	@Override
	public void run(BuildModel buildModel, Node node)
	{
		// todo find list bean by name.
		CIEngineList ciEngineList = (CIEngineList) applicationContext.getBean(buildModel.getExecutionList());
		map.put(buildModel.getId(), ciEngineList);
		try {
			Future<String> page = gitHubLookupServiceImpl.executeList(ciEngineList);
			// Wait until they are all done
			while (!(page.isDone())) {
				Thread.sleep(10); //10-millisecond pause between each check
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (CIEngineStepException e) {
			e.printStackTrace();
		}
	}

}
