package com.ciengine.master.listeners.impl;

import org.springframework.stereotype.Component;

/**
 * Created by evgenymekhanikov on 10.03.17.
 */
@Component(value = "CIEngineListener")
public class CIEngineListenerImpl extends AbstractCIEngineListenerImpl {
    @Override
    protected void createRules() {
		//createRuleBuilder(ciEngineEvent).onCommit().forModules("modA").forBranches("develop, feature/.*").applyList("onCommitList").triggerBuild();
		//createRuleBuilder().onCommit().forModules("modA").forBranches("develop").triggerBuildsFor("modA", "feature/.*");
		//		createRuleBuilder().onCommit().forModules("modA").
//				forBranches("feature/.*").enableAutomergeFrom("develop").enableCrossBuild().triggerBuild();

		createRuleBuilder().onNewArtefact().processReleaseRule();
		createRuleBuilder().onReleaseSubmited().triggerRelease();
		createRuleBuilder().onCommit().forModules("modA").forBranches("develop").triggerBuild();
    }
}
