package com.ciengine.master.controllers.getbuilds;

import com.ciengine.common.dto.FindBuildsResponse;
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
public class FindBuildsController
{
	@Autowired
	CIEngineFacade ciEngineFacade;
	// TODO 1. Should be secure?
	// TODO See list of builds with filtration by fields.
	// TODO 2. Used for link from Stash to concrete build: to see logs, artefacts, status and so on.

	@RequestMapping(value = "/findbuilds",produces = {"application/json"}, method = RequestMethod.POST)
	@ResponseBody
	FindBuildsResponse findbuilds() {
//		GetBuildsResponse getBuildsResponse = new GetBuildsResponse();
		return ciEngineFacade.findBuilds();
	}

}