package com.ciengine.master.listeners.impl;

import com.ciengine.common.Module;
import com.ciengine.common.Repo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evgenymekhanikov on 11.03.17.
 */
@Component
public class PipelineImpl extends AbstractPipelineImpl {

    @Override
    protected void prepareAll() {
        createModule("ModA", "ssh://git@stash.hybris.com:7999/commerce/entitlements.git");
        createModule("ModB", "ssh://git@stash.hybris.com:7999/commerce/entitlements.git");
        createModule("ModC", "ssh://git@stash.hybris.com:7999/commerce/entitlements.git");
        createModule("de.hybris.platform:subscriptions-module", "ssh://git@stash.hybris.com:7999/commerce/subscriptions.git");
        createModule("de.hybris.platform:atdd-module", "ssh://git@stash.hybris.com:7999/platform/atdd.git");

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

    protected void createModule(String name, String gitUrl) {
        Module module = new Module();
        module.setName(name);
        List<String> brabchesFrom = new ArrayList<>();
        brabchesFrom.add("develop");
        brabchesFrom.add("future/6.4");
        List<String> brabchesTo = new ArrayList<>();
        brabchesTo.add("release/6.3.0");
        brabchesTo.add("release/6.4.0");
        module.setBranchesFrom(brabchesFrom);
        module.setBranchesTo(brabchesTo);
        List<Repo> repoList = new ArrayList<>();
        Repo repo = new Repo();
        repo.setGitUrl(gitUrl);
        repoList.add(repo);
        module.setRepoList(repoList);
        getModuleFacade().addModule(module);
    }


}
