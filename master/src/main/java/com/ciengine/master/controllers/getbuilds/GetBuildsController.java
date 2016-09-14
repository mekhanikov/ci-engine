package com.ciengine.master.controllers.getbuilds;

import com.ciengine.master.dao.BuildDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by emekhanikov on 06.09.2016.
 */
@Controller
public class GetBuildsController
{
	@Autowired
	private BuildDao buildDao;
	// TODO 1. Used to receive events from Agents/Slaves. Should be secure?
	// TODO 2. Used for link from Stash to concrete build: to see logs, artefacts, status and so on.
	// TODO 3. See list of builds with filtration by fields.
	@RequestMapping(value = "/getBuildsResponse",produces = {"application/json"}, method = RequestMethod.GET)
	@ResponseBody
	GetBuildsResponse getBuildsResponse() {
		GetBuildsResponse getBuildsResponse = new GetBuildsResponse();
		return getBuildsResponse;
	}

}
