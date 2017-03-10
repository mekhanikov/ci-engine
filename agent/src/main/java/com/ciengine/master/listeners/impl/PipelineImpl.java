package com.ciengine.master.listeners.impl;

import org.springframework.stereotype.Component;

/**
 * Created by evgenymekhanikov on 11.03.17.
 */
@Component
public class PipelineImpl extends AbstractPipelineImpl {

    protected void prepareAll() {
        // We need store it separate on in rules, because triggerBuildsFor() method then requires lots of configurations.
        getEnvironmentFacade().createEnvironmentData("modA", "develop", "onCommitList", "dockerid");
        getEnvironmentFacade().createEnvironmentData("modA", "feature/.*", "onCommitList", "dockerid");
        getEnvironmentFacade().createEnvironmentData("ModA", "release/.*", "mockReleaseList", "dockerid");
        getEnvironmentFacade().createEnvironmentData("ModB", "release/.*", "mockReleaseList", "dockerid");
        getEnvironmentFacade().createEnvironmentData("ModC", "release/.*", "mockReleaseList", "dockerid");
        getEnvironmentFacade().createEnvironmentData("de.hybris.platform:subscriptions-module", "release/.*", "releaseList", "dockerid");
        getEnvironmentFacade().createEnvironmentData("de.hybris.platform:atdd-module", "release/.*", "releaseList", "dockerid");
        //createRuleBuilder(ciEngineEvent).onCommit().forModules("modA").forBranches("develop, feature/.*").applyList("onCommitList").triggerBuild();
        //createRuleBuilder().onCommit().forModules("modA").forBranches("develop").triggerBuildsFor("modA", "feature/.*");
        //		createRuleBuilder().onCommit().forModules("modA").
//				forBranches("feature/.*").enableAutomergeFrom("develop").enableCrossBuild().triggerBuild();

        createRuleBuilder().onNewArtefact().processReleaseRule();
        createRuleBuilder().onReleaseSubmited().triggerRelease();
        createRuleBuilder().onCommit().forModules("modA").forBranches("develop").triggerBuild();
    }
}
