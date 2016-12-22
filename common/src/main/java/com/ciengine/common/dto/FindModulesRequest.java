package com.ciengine.common.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by emekhanikov on 16.12.2016.
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FindModulesRequest {
    private List<String> moduleNames;

    public List<String> getModuleNames() {
        return moduleNames;
    }

    public void setModuleNames(List<String> moduleNames) {
        this.moduleNames = moduleNames;
    }
}
