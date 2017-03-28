package com.ciengine.master.task.impl;

import com.ciengine.master.task.Flow;
import com.ciengine.master.task.FlowFacade;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emekhanikov on 24.03.2017.
 */
@Component
public class FlowFacadeImpl implements FlowFacade {
    List<Flow> flowList = new ArrayList<>();

    @Override
    public void updateAll() {

    }

    @Override
    public void updateBuildTaskStatusForBuildId(String buildId, String newStatus) {
        // TODO Find BuildTask by buildId.
        // TODO Set new status.
        // TODO Evaluate Tasks.
    }
}
