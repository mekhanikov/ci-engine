package com.ciengine.common.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by emekhanikov on 16.12.2016.
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FindBuildsResponse {
    @XmlElementWrapper(name = "modules")
    @XmlElement(name = "module")
    private List<Build> buildList;

    public List<Build> getBuildList() {
        return buildList;
    }

    public void setBuildList(List<Build> buildList) {
        this.buildList = buildList;
    }
}
