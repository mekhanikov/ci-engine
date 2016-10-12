package com.ciengine.master;

import com.ciengine.common.BinaryRepositoryClient;
import com.ciengine.common.Module;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by emekhanikov on 12.10.2016.
 */
public class MockBinaryRepositoryClient implements BinaryRepositoryClient {
    private Map<String, Module> stringModuleMap = new HashMap<>();

    @Override
    public boolean isModuleReleased(String module) {
        return stringModuleMap.containsKey(module);
    }
}
