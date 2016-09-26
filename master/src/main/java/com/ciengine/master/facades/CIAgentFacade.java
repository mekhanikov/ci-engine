package com.ciengine.master.facades;

import com.ciengine.common.Node;
import com.ciengine.master.model.BuildModel;


public interface CIAgentFacade
{
	String getStatus(Node node, int id);
	public void run(BuildModel buildModel, Node node);
}
