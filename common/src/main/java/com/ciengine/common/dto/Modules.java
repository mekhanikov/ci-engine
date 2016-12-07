package com.ciengine.common.dto;

import com.ciengine.common.Module;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by emekhanikov on 26.10.2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Modules {

    @XmlElementWrapper(name = "modules")
    @XmlElement(name = "module")
    private List<Module> modules;

//    private String n;

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
//
//    public String getN() {
//        return n;
//    }
//
//    public void setN(String n) {
//        this.n = n;
//    }
}
