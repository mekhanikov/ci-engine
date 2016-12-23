package com.ciengine.master.facades;

import com.ciengine.common.Module;
import com.ciengine.common.dto.FindModulesRequest;

import java.util.List;


public interface ModuleFacade
{
	List<Module> findModules(FindModulesRequest findModulesRequest);
	Module findModuleByName(String moduleName);
}
