package com.ciengine.master.controllers.setbuildstatus;

import com.ciengine.common.dto.SetBuildStatusRequest;
import com.ciengine.common.dto.SetBuildStatusResponse;
import com.ciengine.master.facades.CIEngineFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Created by emekhanikov on 06.09.2016.
 */
@RestController
public class SetBuildStatusController
{
	@Autowired
	CIEngineFacade ciEngineFacade;

	@RequestMapping(value = "/setbuildstatus",produces = {"application/json","application/xml"}, method = RequestMethod.POST)
	@ResponseBody
	SetBuildStatusResponse onevent(@RequestBody SetBuildStatusRequest setBuildStatusRequest) {//
		SetBuildStatusResponse setBuildStatusResponse = new SetBuildStatusResponse();
		ciEngineFacade.setBuildStatus(setBuildStatusRequest);
		return setBuildStatusResponse;
	}

}
