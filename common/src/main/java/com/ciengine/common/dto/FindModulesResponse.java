package com.ciengine.common.dto;

import com.ciengine.common.Module;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by emekhanikov on 16.12.2016.
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FindModulesResponse {
    @XmlElementWrapper(name = "modules")
    @XmlElement(name = "module")
    private List<Module> modules;

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}
