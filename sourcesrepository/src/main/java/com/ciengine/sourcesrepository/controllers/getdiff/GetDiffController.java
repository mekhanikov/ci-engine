package com.ciengine.sourcesrepository.controllers.getdiff;

import com.ciengine.sourcesrepository.GetDiffRequest;
import com.ciengine.sourcesrepository.GetDiffResponse;
import com.ciengine.sourcesrepository.SourcesRepositoryFacade;
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
public class GetDiffController
{
	@Autowired
	SourcesRepositoryFacade sourcesRepositoryFacade;
//
	@RequestMapping(value = "/getdiff",produces = {"application/json"}, method = RequestMethod.POST)
	@ResponseBody
	GetDiffResponse getdiff(@RequestBody GetDiffRequest getDiffRequest) {
		return sourcesRepositoryFacade.getDiff(getDiffRequest);
	}

}
