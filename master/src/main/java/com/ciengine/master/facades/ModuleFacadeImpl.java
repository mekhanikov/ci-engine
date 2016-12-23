package com.ciengine.master.facades;

import com.ciengine.common.Module;
import com.ciengine.common.dto.FindModulesRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by emekhanikov on 15.12.2016.
 */
//@Transactional
@Component
public class ModuleFacadeImpl implements ModuleFacade {

    private List<Module> modules = new ArrayList<>();

    public ModuleFacadeImpl() {
        // TODO each module may have own, need add all of them
        modules.add(createModule("ModA"));
        modules.add(createModule("ModB"));
        modules.add(createModule("ModC"));
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

    private Module createModule(String name) {
        Module module = new Module();
        module.setName(name);
        List<String> brabchesFrom = new ArrayList<>();
        brabchesFrom.add("develop");
        brabchesFrom.add("future/6.4");
        List<String> brabchesTo = new ArrayList<>();
        brabchesTo.add("release/6.3");
        brabchesTo.add("release/6.4");
        module.setBranchesFrom(brabchesFrom);
        module.setBranchesTo(brabchesTo);
        return module;
    }

}