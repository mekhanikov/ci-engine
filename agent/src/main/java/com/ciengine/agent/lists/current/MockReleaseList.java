package com.ciengine.agent.lists.current;



import com.ciengine.common.*;
import com.ciengine.common.events.OnNewArtifactEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by emekhanikov on 05.09.2016.
 */
@Component
public class MockReleaseList implements CIEngineList
{// TODO rename to ReleaseList
	@Autowired
	private CIEngineClient ciEngineClient;

	@Override public void doList(EnvironmentVariables environmentVariables) throws CIEngineStepException
	{
		// TODO check if module already relesed -> delete Release from DB (need releaseId in in args)
		// TODO get list of deps from pom
		// TODO map them to module-name
		// TODO check if it is going to release AND not released yet -> SKIP

System.out.print("d");
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//throw new CIEngineStepException("");
		OnNewArtifactEvent onNewArtifactEvent = new OnNewArtifactEvent();

		String gitUrl = environmentVariables.getProperty("GIT_URL");
		String branchName = environmentVariables.getProperty("BRANCH_NAME");
		String commitId = environmentVariables.getProperty("COMMIT_ID");
		String buildId = environmentVariables.getProperty("BUILD_ID");
		onNewArtifactEvent.setComitId(commitId);
		onNewArtifactEvent.setGitUrl(gitUrl);
		onNewArtifactEvent.setBranchName(branchName);
		ciEngineClient.sendEvent(onNewArtifactEvent);
		ciEngineClient.setBuildStatus(buildId, BuildStatus.SKIPED);
	}
}
