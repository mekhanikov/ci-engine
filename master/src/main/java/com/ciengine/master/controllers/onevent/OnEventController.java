package com.ciengine.master.controllers.onevent;

import com.ciengine.common.DefaultCIEngineEvent;
import com.ciengine.common.dto.OnEventResponse;
import com.ciengine.master.controllers.CIEngineFacade;
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

	// TODO 1. Used to receive events from Agents/Slaves. Should be secure?
	// TODO 2. Used for link from Stash to concrete build: to see logs, artefacts, status and so on.
	// TODO 3. See list of builds with filtration by fields.
	@RequestMapping(value = "/onevent",produces = {"application/json","application/xml"}, method = RequestMethod.POST)
	@ResponseBody
	OnEventResponse onevent(@RequestBody DefaultCIEngineEvent defaultCIEngineEvent) {//
		OnEventResponse onEventResponse = new OnEventResponse();
		return onEventResponse;
	}

}
