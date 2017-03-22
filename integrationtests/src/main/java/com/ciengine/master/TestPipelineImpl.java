package com.ciengine.master;

import com.ciengine.common.Module;
import com.ciengine.common.Repo;
import com.ciengine.master.listeners.impl.AbstractPipelineImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evgenymekhanikov on 11.03.17.
 */
@Component
public class TestPipelineImpl extends AbstractPipelineImpl {

    @Override
    protected void prepareAll() {
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
