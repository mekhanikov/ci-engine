package com.ciengine.master.listeners.impl;

import com.ciengine.master.facades.EnvironmentFacade;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.RuleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import sun.misc.Contended;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evgenymekhanikov on 10.03.17.
 */
@Component
public class PipelineImpl implements Pipeline {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private EnvironmentFacade environmentFacade;

    private List<RuleBuilder> ruleBuilderList = new ArrayList<>();

    @Override
    public List<RuleBuilder> prepare() {
        environmentFacade.createEnvironmentData("modA", "develop", "onCommitList", "dockerid");
        environmentFacade.createEnvironmentData("modA", "feature/.*", "onCommitList", "dockerid");
        environmentFacade.createEnvironmentData("ModA", "release/.*", "mockReleaseList", "dockerid");
        environmentFacade.createEnvironmentData("ModB", "release/.*", "mockReleaseList", "dockerid");
        environmentFacade.createEnvironmentData("ModC", "release/.*", "mockReleaseList", "dockerid");
        environmentFacade.createEnvironmentData("de.hybris.platform:subscriptions-module", "release/.*", "releaseList", "dockerid");
        environmentFacade.createEnvironmentData("de.hybris.platform:atdd-module", "release/.*", "releaseList", "dockerid");
        //createRuleBuilder(ciEngineEvent).onCommit().forModules("modA").forBranches("develop, feature/.*").applyList("onCommitList").triggerBuild();
        //createRuleBuilder().onCommit().forModules("modA").forBranches("develop").triggerBuildsFor("modA", "feature/.*");
        //		createRuleBuilder().onCommit().forModules("modA").
//				forBranches("feature/.*").enableAutomergeFrom("develop").enableCrossBuild().triggerBuild();

        createRuleBuilder().onNewArtefact().processReleaseRule();
        createRuleBuilder().onReleaseSubmited().triggerRelease();
        createRuleBuilder().onCommit().forModules("modA").forBranches("develop").triggerBuild();
        return ruleBuilderList;
    }

    protected RuleBuilder createRuleBuilder() {
        RuleBuilder ruleBuilder = applicationContext.getBean(RuleBuilder.class);
        ruleBuilderList.add(ruleBuilder);
        return ruleBuilder;
    }
}
