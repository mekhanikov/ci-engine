package com.ciengine.master;

import com.ciengine.common.BuildStatus;
import com.ciengine.common.Node;
import com.ciengine.master.dao.BuildDao;
import com.ciengine.master.facades.CIAgentFacade;
import com.ciengine.master.facades.NodeFacade;
import com.ciengine.master.model.BuildModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;


/**
 * Created by emekhanikov on 13.09.2016.
 */
@Component
public class BuildRunner// implements BuildRunner
{
	private static final Log logger = LogFactory.getLog(BuildRunner.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


	@Autowired
	BuildDao buildDao;

	@Autowired
	NodeFacade nodeFacade;

	@Autowired
	private CIAgentFacade ciAgentFacade;

	@Scheduled(fixedRate = 2000)
	public void reportCurrentTime() {
		BuildModel buildModel = buildDao.getNextToBuild();
		if (buildModel != null) {
			Node node = nodeFacade.findBestNode();
			buildModel.setNodeId(node.getId());
			buildModel.setStatus(BuildStatus.IN_PROGRESS);
			buildDao.update(buildModel);
			ciAgentFacade.run(buildModel, node);
			logger.info(String.valueOf(buildModel));
		}
	}

	public void setCiAgentFacade(CIAgentFacade ciAgentFacade)
	{
		this.ciAgentFacade = ciAgentFacade;
	}


	// If no free nodes - exit if used Scheduller.
}
