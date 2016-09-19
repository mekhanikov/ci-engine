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
	{
		Node node = new Node();
		node.setUser("ev");
		node.setPassword("weter");
		node.setHost("127.0.0.1");
		node.setPort(22);
		node.setRootWorkspace("/home/ev");
		return node;
	}
}
