package com.ciengine.master;

import com.ciengine.common.BinaryRepositoryClient;
import com.ciengine.common.Module;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by emekhanikov on 12.10.2016.
 */
public class ArtefactoryBinaryRepositoryClient implements BinaryRepositoryClient {
    private Map<String, Module> stringModuleMap = new HashMap<>();
// TODO But BinaryRepo(Artefactory) works with mave artefacts, not with modules, should we rename method to isArtefactReeleased?
    @Override
    public boolean isModuleReleased(String module) {
        return stringModuleMap.containsKey(module);
    }

    public void addModule(String s) {
        stringModuleMap.put(s, null);
    }
}
