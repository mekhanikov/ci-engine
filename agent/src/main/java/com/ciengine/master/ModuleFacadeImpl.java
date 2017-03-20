package com.ciengine.master;

import com.ciengine.common.Module;
import com.ciengine.common.Repo;
import com.ciengine.common.dto.FindModulesRequest;
import com.ciengine.master.facades.ModuleFacade;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emekhanikov on 15.12.2016.
 */
//@Transactional
@Component
public class ModuleFacadeImpl implements ModuleFacade {

    private List<Module> modules = new ArrayList<>();

    public ModuleFacadeImpl() {
        // TODO each module may have own, need add all of them
        modules.add(createModule("ModA", "ssh://git@stash.hybris.com:7999/commerce/entitlements.git"));
        modules.add(createModule("ModB", "ssh://git@stash.hybris.com:7999/commerce/entitlements.git"));
        modules.add(createModule("ModC", "ssh://git@stash.hybris.com:7999/commerce/entitlements.git"));
        modules.add(createModule("de.hybris.platform:subscriptions-module", "ssh://git@stash.hybris.com:7999/commerce/subscriptions.git"));
        modules.add(createModule("de.hybris.platform:atdd-module", "ssh://git@stash.hybris.com:7999/platform/atdd.git"));
//        createEnvironmentData("modA", "feature/.*", "onCommitList", "dockerid");


//        createRuleBuilder(ciEngineEvent).onCommit().forModules("modA").forBranches("develop").triggerBuild();

//        createRuleBuilder(ciEngineEvent).onCommit().forModules("modA").
//                forBranches("feature/.*").enableAutomergeFrom("develop").enableCrossBuild().triggerBuild();
//        createRuleBuilder(ciEngineEvent).onCommit().forModules("modA").forBranches("develop").triggerBuildsFor("modA", "feature/.*");


    }

    @Override
    public List<Module> findModules(FindModulesRequest findModulesRequest) {
        List<Module> result;
        if (findModulesRequest.getModuleNames() == null || findModulesRequest.getModuleNames().isEmpty()) {
            result = modules;
        } else {
            result = new ArrayList<>();
            for (Module module : modules) {
                if (findModulesRequest.getModuleNames().contains(module.getName())) {
                    result.add(module);
                }

            }
        }
        return result;
    }

    @Override
    public Module findModuleByName(String moduleName) {
        return null;
    }

    private Module createModule(String name, String gitUrl) {
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
        return module;
    }

}
