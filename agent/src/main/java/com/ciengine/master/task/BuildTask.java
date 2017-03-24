package com.ciengine.master.task;

import com.ciengine.common.dto.AddBuildRequest;
import com.ciengine.common.dto.AddBuildResponse;
import com.ciengine.master.facades.CIEngineFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emekhanikov on 23.03.2017.
 */
public class BuildTask extends Task {
//    pr

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

    }
}
