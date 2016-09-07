package main.java.com.ciengine.agent.steps.impl;

import main.java.com.ciengine.common.EnvironmentVariables;
import main.java.com.ciengine.agent.steps.CIEngineStep;
import main.java.com.ciengine.agent.steps.CIEngineStepException;
import org.springframework.util.StringUtils;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class CheckoutStep implements CIEngineStep
{
	@Override
	public void doStep(EnvironmentVariables environmentVariables) throws CIEngineStepException
	{
		String gitUrl = environmentVariables.getProperty("GIT_URL");
		String branchName = environmentVariables.getProperty("BRANCH_NAME");
		String commitId = environmentVariables.getProperty("COMMIT_ID");
		if (StringUtils.isEmpty(commitId)) {
			if (StringUtils.isEmpty(branchName)) {
				throw new CIEngineStepException();
			}
			commitId = getTheLattestCommitForBranch(gitUrl, branchName);
			environmentVariables.addProperty("COMMIT_ID", commitId);
		}
		String hash = environmentVariables.getProperty("HASH");
		hash = calcHash(hash, "CheckoutStep", gitUrl, commitId);
		environmentVariables.addProperty("HASH", hash);
		// TODO do checkout
	}

	private String calcHash(String... args)
	{
		String result = "";
		for(String s : args) {
			// TODO
		}
		return result;
	}

	private String getTheLattestCommitForBranch(String gitUrl, String branchName)
	{// TODO
		return null;
	}
}
