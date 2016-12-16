package com.ciengine.master;

import com.ciengine.common.Node;
import com.ciengine.master.dao.BuildDao;
import com.ciengine.master.facades.CIAgentFacade;
import com.ciengine.master.facades.CIEngineFacade;
import com.ciengine.master.facades.NodeFacade;
import com.ciengine.master.model.BuildModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by emekhanikov on 13.09.2016.
 */
@Component
public class BuildStatusChecker
{
	private static final Log logger = LogFactory.getLog(BuildStatusChecker.class);


	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


	@Autowired
	private BuildDao buildDao;

	@Autowired
	private NodeFacade nodeFacade;

	@Autowired
	private CIEngineFacade ciEngineFacade;

	@Autowired
	private CIAgentFacade ciAgentFacade;

	@Scheduled(fixedRate = 2000)
	public void reportCurrentTime() {
		List<BuildModel> buildModelList = buildDao.getNextBuildsInProgress();
		if (buildModelList != null) {
			for (BuildModel buildModel : buildModelList) {
				// TODO need to set state only if build finished, but status still in progress. It could because job was failed or terminated. What status to set then?
				Node node = nodeFacade.findBestNodeById(buildModel.getNodeId());
				String s = ciAgentFacade.getStatus(node, buildModel.getId());
				if (!buildModel.getStatus().equals(s)) {
					buildModel.setStatus(s);
					//buildDao.update(buildModel);
//					OnNewArtifactEvent onNewArtifactEvent = new OnNewArtifactEvent();
//					ciEngineFacade.onEvent(onNewArtifactEvent);
				}
				// Finished with status s.
				logger.info(String.valueOf(buildModel));
			}
		}
	}

	public BuildDao getBuildDao()
	{
		return buildDao;
	}

	public void setBuildDao(BuildDao buildDao)
	{
		this.buildDao = buildDao;
	}

	public NodeFacade getNodeFacade()
	{
		return nodeFacade;
	}

	public void setNodeFacade(NodeFacade nodeFacade)
	{
		this.nodeFacade = nodeFacade;
	}

	public CIEngineFacade getCiEngineFacade()
	{
		return ciEngineFacade;
	}

	public void setCiEngineFacade(CIEngineFacade ciEngineFacade)
	{
		this.ciEngineFacade = ciEngineFacade;
	}

	public CIAgentFacade getCiAgentFacade()
	{
		return ciAgentFacade;
	}

	public void setCiAgentFacade(CIAgentFacade ciAgentFacade)
	{
		this.ciAgentFacade = ciAgentFacade;
	}
}
