package com.ciengine.master.controllers.ismodulereleased;

import com.ciengine.common.DefaultCIEngineEvent;
import com.ciengine.common.dto.IsModuleReleasedRequest;
import com.ciengine.common.dto.IsModuleReleasedResponse;
import com.ciengine.common.dto.OnEventResponse;
import com.ciengine.master.facades.CIEngineFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Created by emekhanikov on 06.09.2016.
 */
@RestController
public class IsModuleReleasedController
{
	@Autowired
	CIEngineFacade ciEngineFacade;

	@RequestMapping(value = "/ismodulereleased",produces = {"application/json","application/xml"}, method = RequestMethod.POST)
	@ResponseBody
	IsModuleReleasedResponse ismodulereleased(@RequestBody IsModuleReleasedRequest isModuleReleasedRequest) {//
//		IsModuleReleasedResponse isModuleReleasedResponse = new IsModuleReleasedResponse();

		return ciEngineFacade.isModuleReleased(isModuleReleasedRequest);
	}

}
