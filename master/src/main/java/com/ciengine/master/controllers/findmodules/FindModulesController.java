package com.ciengine.master.controllers.findmodules;

import com.ciengine.common.dto.FindModulesRequest;
import com.ciengine.common.dto.FindModulesResponse;
import com.ciengine.common.dto.SubmitReleasesRequest;
import com.ciengine.common.dto.SubmitReleasesResponse;
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
public class FindModulesController
{
	@Autowired
	CIEngineFacade ciEngineFacade;

	// TODO 1. Should be secure?
	@RequestMapping(value = "/findmodules",produces = {"application/json"}, method = RequestMethod.POST)
	@ResponseBody
	FindModulesResponse findmodules(@RequestBody FindModulesRequest findModulesRequest) {
//		GetBuildsResponse getBuildsResponse = new GetBuildsResponse();
		return ciEngineFacade.findModules(findModulesRequest);
	}

}
