package com.ciengine.master.facades;

import com.ciengine.common.*;
import com.ciengine.master.controllers.addbuild.AddBuildRequest;
import com.ciengine.master.controllers.getbuilds.GetBuildsResponse;
import com.ciengine.master.dao.BuildDao;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.CIEngineListenerException;
import com.ciengine.master.model.BuildModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * Created by emekhanikov on 14.09.2016.
 */
@Transactional
@Component
public class CIEngineFacadeImpl implements CIEngineFacade
{
	@Autowired
	private BuildDao buildDao;

	@Autowired
	private ApplicationContext applicationContext;

	private List<CIEngineListener> ciEngineListeners = new ArrayList<>();

	public GetBuildsResponse getBuildsResponse() {
		GetBuildsResponse getBuildsResponse = new GetBuildsResponse();
		//		buildDao.getAll();
//		BuildModel buildModel = new BuildModel();
//		buildModel.setStartTimestamp(new Date());
		buildDao.getAll();
		return getBuildsResponse;
	}

	@Override public GetBuildsResponse addBuild(AddBuildRequest addBuildRequest)
	{
		GetBuildsResponse getBuildsResponse = new GetBuildsResponse();
		BuildModel buildModel = new BuildModel();
		buildModel.setDockerImageId(addBuildRequest.getDockerImageId());
		buildModel.setBranchName(addBuildRequest.getBranchName());
		buildModel.setExecutionList(addBuildRequest.getExecutionList());
		buildModel.setInputParams(addBuildRequest.getInputParams());
		buildModel.setModuleName(addBuildRequest.getModuleName());
		buildModel.setNodeId(addBuildRequest.getNodeId());
		buildModel.setReasonOfTrigger(addBuildRequest.getReasonOfTrigger());
		buildModel.setStatus("QUEUED");
		buildModel.setStartTimestamp(new Date());
		buildDao.save(buildModel);
		return getBuildsResponse;
	}

	@Override public void onEvent(DefaultCIEngineEvent defaultCIEngineEvent)
	{
		Map<String, CIEngineListener> stringCIEngineListenerMap = applicationContext.getBeansOfType(CIEngineListener.class);
		if (stringCIEngineListenerMap != null) {
			Collection<CIEngineListener> ciEngineListenerCollection = stringCIEngineListenerMap.values();
			processEventForListeners(defaultCIEngineEvent, ciEngineListenerCollection);
		}
		processEventForListeners(defaultCIEngineEvent, ciEngineListeners);
	}

	private void processEventForListeners(DefaultCIEngineEvent defaultCIEngineEvent, Collection<CIEngineListener> ciEngineListenerCollection)
	{
		for (CIEngineListener ciEngineListener : ciEngineListenerCollection) {
			if (ciEngineListener.isEventApplicable(defaultCIEngineEvent)) {
				try
				{
					ciEngineListener.onEvent(defaultCIEngineEvent);
				}
				catch (CIEngineListenerException e)
				{
					// TODO
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void submitEvent(CIEngineEvent ciEngineEvent) throws CIEngineException
	{// TODO in CIEngineFacadeImpl.onEvent()

	}

	@Override public Module findModuleByGitUrl(String gitUrl)
	{// TODO
		Module module = new Module();
		return module;
	}

	@Override public Build runOnNode(Node node)
	{// TODO not used, remove? or extract from BuildRunnerImpl?
		return null;
	}

	@Override public void addListener(CIEngineListener ciEngineListener)
	{
		ciEngineListeners.add(ciEngineListener);
	}

	//	public static void main(String[] strings) {
	//
	//	}
}
