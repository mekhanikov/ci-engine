package main.java.com.ciengine.master;

import main.java.com.ciengine.common.*;
import main.java.com.ciengine.common.CIEngineEvent;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class CIEngineImpl implements CIEngine
{
	@Override
	public void submitEvent(CIEngineEvent ciEngineEvent) throws CIEngineException
	{// TODO

	}

	@Override public Module findModuleByGitUrl(String gitUrl)
	{// TODO
		return null;
	}

	@Override public Build runOnNode(Node node)
	{
		return null;
	}

	public static void main(String[] strings) {
		// TODO Run all controllers
		// TODO initiate all Listeners

	}
}
