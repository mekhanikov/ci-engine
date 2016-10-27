package com.ciengine.master.controllers.findbuild;

import com.ciengine.common.dto.AddBuildRequest;
import com.ciengine.common.dto.AddBuildResponse;
import com.ciengine.master.controllers.getbuilds.GetBuildsResponse;
import com.ciengine.master.facades.CIEngineFacade;
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
public class FindBuildController
{
	@Autowired
	CIEngineFacade ciEngineFacade;

	// TODO 1. Should be secure?
	@RequestMapping(value = "/findbuild",produces = {"application/json"}, method = RequestMethod.POST)
	@ResponseBody
	AddBuildResponse addbuild(@RequestBody AddBuildRequest addBuildRequest) {
//		GetBuildsResponse getBuildsResponse = new GetBuildsResponse();
		return ciEngineFacade.findBuild(addBuildRequest);
	}

}
