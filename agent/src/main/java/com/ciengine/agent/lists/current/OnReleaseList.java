package com.ciengine.agent.lists.current;


import com.ciengine.agent.steps.impl.AttachArtefactsStep;
import com.ciengine.agent.steps.impl.BuildStep;
import com.ciengine.agent.steps.impl.CheckoutStep;
import com.ciengine.agent.steps.impl.NewArtefactsReleasedStep;
import com.ciengine.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by emekhanikov on 05.09.2016.
 */
@Component
public class OnReleaseList implements CIEngineList
{
	@Autowired
	private CheckoutStep checkoutStep;

	@Autowired
	private BuildStep buildStep;

	@Autowired
	private AttachArtefactsStep attachArtefactsStep;

	@Autowired
	private NewArtefactsReleasedStep newArtefactsReleasedStep;

	@Override public void doList(EnvironmentVariables environmentVariables) throws CIEngineStepException
	{
		/*

		In: ruleId, version to release, list of ModX:vY, .. goig to release, develop commitid, release branch
release artefact -> id, name, giturl, branch
[check already released -> disable rule, exit]
[merge from develop] from comitid to release branch
[check all required modules on artifactory, not - exit]
[update pom-latest-approved-milestones] overwritten by Required artifacts
[update version]
[stash in progress]
Build
[stash success | failed]
[if success, commit version, add tag, commit release-snapshot, push]


		 */
		String gitUrl = environmentVariables.getProperty(EnvironmentVariablesConstants.GIT_URL);
		String branchName = environmentVariables.getProperty(EnvironmentVariablesConstants.BRANCH_NAME);
		String commitId = environmentVariables.getProperty(EnvironmentVariablesConstants.COMMIT_ID);

	}

	private void executeStep(CIEngineStep checkoutStep, EnvironmentVariables environmentVariables) throws CIEngineStepException
	{
		checkoutStep.doStep(environmentVariables);
	}
}
