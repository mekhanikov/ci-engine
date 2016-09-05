package main.java.com.ciengine;

/**
 * Created by emekhanikov on 05.09.2016.
 */
public interface CIEngine
{
	void submitBuild(String moduleName, String branchName, String executionListId, String dockerImageId,
			EnvironmentVariables environmentVariables) throws CIEngineException;
}
