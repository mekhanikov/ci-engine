package com.ciengine.master.facades;

import com.ciengine.common.Module;

import java.util.List;


public interface ModuleFacade
{
	List<Module> findModules();
	Module findModuleByName(String moduleName);
}
