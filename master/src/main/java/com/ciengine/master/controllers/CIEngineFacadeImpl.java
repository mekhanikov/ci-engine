package com.ciengine.master.controllers;

import com.ciengine.master.controllers.addbuild.AddBuildRequest;
import com.ciengine.master.controllers.getbuilds.GetBuildsResponse;
import com.ciengine.master.dao.BuildDao;
import com.ciengine.master.model.BuildModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by emekhanikov on 14.09.2016.
 */
@Transactional
@Component
public class CIEngineFacadeImpl implements CIEngineFacade
{
	@Autowired
	private BuildDao buildDao;

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
		buildDao.save(buildModel);
		return getBuildsResponse;
	}
}
