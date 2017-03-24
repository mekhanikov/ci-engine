package com.ciengine.master.task;

import com.ciengine.common.Module;
import com.ciengine.common.Repo;
import com.ciengine.master.listeners.impl.AbstractPipelineImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evgenymekhanikov on 11.03.17.
 */
@Component
public class TaskPipelineImpl extends AbstractPipelineImpl {

    @Override
    protected void prepareAll() {
        //
//        createRuleBuilder().onCommit().forModules("modA").forBranches("develop").triggerBuild();
    }
}
