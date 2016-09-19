package com.ciengine.master;



import com.ciengine.common.*;
import org.springframework.stereotype.Component;


/**
 * Created by emekhanikov on 05.09.2016.
 */
@Component
public class CIEngineImpl implements CIEngine
{
	@Override
	public void submitEvent(CIEngineEvent ciEngineEvent) throws CIEngineException
	{// TODO in CIEngineFacadeImpl.onEvent()

	}

	@Override public Module findModuleByGitUrl(String gitUrl)
	{// TODO
		return null;
	}

	@Override public Build runOnNode(Node node)
	{// TODO not used, remove? or extract from BuildRunnerImpl?
		return null;
	}

//	public static void main(String[] strings) {
//
//	}
}
