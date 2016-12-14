package com.ciengine.common.dto;

import java.util.List;

/**
 * Created by emekhanikov on 14.12.2016.
 */
public class Release {
    private String name;
    private String brancheFrom;
    private String version;
    private String brancheTo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrancheFrom() {
        return brancheFrom;
    }

    public void setBrancheFrom(String brancheFrom) {
        this.brancheFrom = brancheFrom;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBrancheTo() {
        return brancheTo;
    }

    public void setBrancheTo(String brancheTo) {
        this.brancheTo = brancheTo;
    }
}
