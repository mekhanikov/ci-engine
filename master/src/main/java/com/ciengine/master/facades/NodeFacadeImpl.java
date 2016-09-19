package com.ciengine.master.facades;

import com.ciengine.common.Node;
import org.springframework.stereotype.Component;


/**
 * Created by emekhanikov on 19.09.2016.
 */
@Component
public class NodeFacadeImpl implements NodeFacade
{
	@Override public Node findBestNode()
	{// TODO implement me
		Node node = getNode();
		return node;
	}

	@Override public Node findBestNodeById(String nodeId)
	{// TODO implement me
		return getNode();
	}

	protected Node getNode()
	{
		Node node = new Node();
		node.setId("1");
		node.setUser("ev");
		node.setPassword("weter");
		node.setHost("127.0.0.1");
		node.setPort(22);
		node.setRootWorkspace("/home/ev");
		return node;
	}
}
