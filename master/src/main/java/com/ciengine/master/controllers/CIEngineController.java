package com.ciengine.master.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by emekhanikov on 06.09.2016.
 */
@Controller
public class CIEngineController
{
	// TODO 1. Used to receive events from Agents/Slaves. Should be secure?
	// TODO 2. Used for link from Stash to concrete build: to see logs, artefacts, status and so on.
	// TODO 3. See list of builds with filtration by fields.
	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!";
	}

}
