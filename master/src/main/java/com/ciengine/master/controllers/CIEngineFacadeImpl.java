package com.ciengine.master.controllers;

import com.ciengine.master.controllers.getbuilds.GetBuildsResponse;
import com.ciengine.master.dao.BuildDao;
import com.ciengine.master.model.BuildModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * Created by emekhanikov on 14.09.2016.
 */
@Transactional
@Component
public class CIEngineFacadeImpl implements CIEngineFacade
{
	@Autowired
	private BuildDao buildDao;

	public CIEngineFacadeImpl() {
	System.out.println("sd");
	}

	public GetBuildsResponse getBuildsResponse() {
		GetBuildsResponse getBuildsResponse = new GetBuildsResponse();
		//		buildDao.getAll();
//		BuildModel buildModel = new BuildModel();
//		buildModel.setStartTimestamp(new Date());
		buildDao.getAll();
		return getBuildsResponse;
	}
}
