package com.ciengine.master.controllers.submitreleases;

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
public class SubmitReleasesController
{
	@Autowired
	CIEngineFacade ciEngineFacade;

	// TODO 1. Should be secure?
	@RequestMapping(value = "/submitreleases",produces = {"application/json"}, method = RequestMethod.POST)
	@ResponseBody
	SubmitReleasesResponse addbuild(@RequestBody SubmitReleasesRequest submitReleasesRequest) {
//		GetBuildsResponse getBuildsResponse = new GetBuildsResponse();
		return ciEngineFacade.submitReleases(submitReleasesRequest);
	}

}
