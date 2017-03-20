package com.ciengine.master.listeners.impl;

import org.springframework.stereotype.Component;

/**
 * Created by evgenymekhanikov on 11.03.17.
 */
@Component
public class PipelineImpl extends AbstractPipelineImpl {

    @Override
    protected void prepareAll() {
        // We need store it separate on in rules, because triggerBuildsFor() method then requires lots of configurations.
        createEnvironmentData("modA", "develop", "onCommitList", "dockerid");
        createEnvironmentData("modA", "feature/.*", "onCommitList", "dockerid");
        createEnvironmentData("ModA", "release/.*", "mockReleaseList", "dockerid");
        createEnvironmentData("ModB", "release/.*", "mockReleaseList", "dockerid");
        createEnvironmentData("ModC", "release/.*", "mockReleaseList", "dockerid");
        createEnvironmentData("de.hybris.platform:subscriptions-module", "release/.*", "releaseList", "dockerid");
        createEnvironmentData("de.hybris.platform:atdd-module", "release/.*", "releaseList", "dockerid");
        //createRuleBuilder(ciEngineEvent).onCommit().forModules("modA").forBranches("develop, feature/.*").applyList("onCommitList").triggerBuild();
        //createRuleBuilder().onCommit().forModules("modA").forBranches("develop").triggerBuildsFor("modA", "feature/.*");
        //		createRuleBuilder().onCommit().forModules("modA").
//				forBranches("feature/.*").enableAutomergeFrom("develop").enableCrossBuild().triggerBuild();

        createRuleBuilder().onNewArtefact().processReleaseRule();
        createRuleBuilder().onReleaseSubmited().triggerRelease();
        createRuleBuilder().onCommit().forModules("modA").forBranches("develop").triggerBuild();
    }

    protected void createEnvironmentData(String moduleName, String branchName, String commitId, String dockerid) {
        getEnvironmentFacade().createEnvironmentData(moduleName, branchName, commitId, dockerid);
    }
}
