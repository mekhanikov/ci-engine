package com.ciengine.master.facades;

import com.ciengine.common.Node;
import com.ciengine.master.model.BuildModel;


public interface CIAgentFacade
{
	String getStatus(Node node, int id);
	void run(BuildModel buildModel, Node node);
}
