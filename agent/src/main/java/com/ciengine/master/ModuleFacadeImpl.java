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

    @Override
    public void addModule(Module module) {
        modules.add(module);
    }


}
