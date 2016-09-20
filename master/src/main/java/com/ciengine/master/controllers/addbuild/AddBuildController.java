package com.ciengine.master.controllers.addbuild;

import com.ciengine.master.facades.CIEngineFacade;
import com.ciengine.master.controllers.getbuilds.GetBuildsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by emekhanikov on 06.09.2016.
 */
@Controller
public class AddBuildController
{
	@Autowired
	CIEngineFacade ciEngineFacade;

	// TODO 1. Should be secure?
	@RequestMapping(value = "/addbuild",produces = {"application/json"}, method = RequestMethod.POST)
	@ResponseBody
	GetBuildsResponse addbuild(@RequestBody AddBuildRequest addBuildRequest) {
//		GetBuildsResponse getBuildsResponse = new GetBuildsResponse();
		return ciEngineFacade.addBuild(addBuildRequest);
	}

}
