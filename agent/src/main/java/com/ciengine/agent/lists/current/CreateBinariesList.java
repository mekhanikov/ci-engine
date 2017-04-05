package com.ciengine.agent.lists.current;


import com.ciengine.common.CIEngineList;
import com.ciengine.common.CIEngineStepException;
import com.ciengine.common.EnvironmentVariables;
import com.ciengine.common.EnvironmentVariablesConstants;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by emekhanikov on 05.09.2016.
 */
@Component
public class CreateBinariesList  implements CIEngineList
{

	private String gitUrl;
	private String CIEngineMasterUrl;

	@Override public void doList(EnvironmentVariables environmentVariables) throws CIEngineStepException
	{
		try {
			// TODO sync files from previus task?
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override public EnvironmentVariables createEnvironmentVariables()
	{
		EnvironmentVariables environmentVariables = new EnvironmentVariables();
		environmentVariables.addProperty(EnvironmentVariablesConstants.GIT_URL, gitUrl);
		environmentVariables.addProperty(EnvironmentVariablesConstants.CIENGINE_MASTER_URL, CIEngineMasterUrl);
		return environmentVariables;
	}

	@Override public String getBeanName()
	{
		return "createBinariesList";
	}

	public void setGitUrl(String gitUrl)
	{
		this.gitUrl = gitUrl;
	}

	public void setCIEngineMasterUrl(String CIEngineMasterUrl)
	{
		this.CIEngineMasterUrl = CIEngineMasterUrl;
	}
}
