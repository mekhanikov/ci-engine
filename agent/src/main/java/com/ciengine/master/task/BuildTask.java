package com.ciengine.master.task;

import com.ciengine.common.dto.*;
import com.ciengine.master.facades.CIEngineFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by emekhanikov on 23.03.2017.
 */
public class BuildTask extends Task {
    private Integer buildId;

    @Autowired
    private CIEngineFacade ciEngineFacade;

    public BuildTask(String name) {
        super(name);
    }

    @Override
    public void run() {
        AddBuildRequest addBuildRequest = new AddBuildRequest();
        // TODO
        AddBuildResponse addBuildResponse = ciEngineFacade.addBuild(addBuildRequest);
        buildId = addBuildResponse.getBuildId();
    }

    public void update() {
        if (buildId != null) {
            FindBuildsRequest findBuildsRequest = new FindBuildsRequest();
            FindBuildsResponse findBuildsResponse = ciEngineFacade.findBuild(findBuildsRequest);
            Build build = findBuildsResponse.getBuildList().get(0);
            setStatus(build.getStatus());
        }
    }

    public Integer getBuildId()
    {
        return buildId;
    }

    public void setBuildId(Integer buildId)
    {
        this.buildId = buildId;
    }
}
