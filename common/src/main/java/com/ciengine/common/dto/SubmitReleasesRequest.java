package com.ciengine.common.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by emekhanikov on 14.12.2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SubmitReleasesRequest {

    private List<Release> releaseList;

    public List<Release> getReleaseList() {
        return releaseList;
    }

    public void setReleaseList(List<Release> releaseList) {
        this.releaseList = releaseList;
    }
}
