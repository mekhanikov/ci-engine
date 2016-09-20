package com.ciengine.master.controllers.onevent;

import com.ciengine.common.DefaultCIEngineEvent;
import com.ciengine.common.dto.OnEventResponse;
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
public class OnEventController
{
	@Autowired
	CIEngineFacade ciEngineFacade;

	@RequestMapping(value = "/onevent",produces = {"application/json","application/xml"}, method = RequestMethod.POST)
	@ResponseBody
	OnEventResponse onevent(@RequestBody DefaultCIEngineEvent defaultCIEngineEvent) {//
		OnEventResponse onEventResponse = new OnEventResponse();
		ciEngineFacade.onEvent(defaultCIEngineEvent);
		return onEventResponse;
	}

}
