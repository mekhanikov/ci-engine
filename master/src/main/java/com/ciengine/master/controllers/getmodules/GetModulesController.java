package com.ciengine.master.controllers.getmodules;

import com.ciengine.master.GetModulesResponse;
import com.ciengine.master.facades.CIEngineFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by emekhanikov on 06.09.2016.
 */
@Controller
public class GetModulesController
{
	@Autowired
	CIEngineFacade ciEngineFacade;
	// TODO 1. Should be secure?


	@RequestMapping(value = "/getModulesResponse",produces = {"application/json"}, method = RequestMethod.GET)
	@ResponseBody
    GetModulesResponse getModulesResponse() {
//		GetBuildsResponse getBuildsResponse = new GetBuildsResponse();
		return ciEngineFacade.getModulesResponse();
	}

}
