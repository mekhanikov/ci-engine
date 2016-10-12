package com.ciengine.common;

/**
 * Created by emekhanikov on 12.10.2016.
 */
public interface BinaryRepositoryClient {
    /**
     * Can be modA, ModA:1.2, groupId:artName:124
     * @param module
     * @return
     */
    boolean isModuleReleased(String module);
}
