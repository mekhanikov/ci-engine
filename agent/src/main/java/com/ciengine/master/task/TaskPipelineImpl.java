package com.ciengine.master.task;

import com.ciengine.master.listeners.impl.AbstractPipelineImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evgenymekhanikov on 11.03.17.
 */
@Component
public class TaskPipelineImpl extends AbstractPipelineImpl {
    @Autowired
    private FlowFacade flowFacade;

    @Override
    protected void prepareAll() {
        flowFacade.createPrototype("build CS", (flowContext, flow)->{
            Task createBinaries = flow.createBuildTask("createBinaries");
            Task createSources = flow.createBuildTask("createSources");

            List<Task> tests = new ArrayList<>();
            for(int i=0; i < 2; i++) {
                Task task = flow.createBuildTask("test" +i);
                task.dependsOn(createBinaries, createSources);
                tests.add(task);
            }

            Task javadocTask = flow.createBuildTask("javadocTask");
            javadocTask.dependsOn(createBinaries, createSources);

            Task deployTask = flow.createBuildTask("deployTask");

            Task[] myArray = tests.toArray(new Task[0]);
            deployTask.dependsOn(createBinaries, createSources, javadocTask);
            deployTask.dependsOn(myArray);
        });


        // TODO on commit to pom.xml or on manual trigger (sction?) run flow. O have build?
        // TODO on build status changed (/periodicaly), update according Task nd reevaluate other.
        // /periodicaly
        //
//        createRuleBuilder().onCommit().forModules("modA").forBranches("develop").triggerBuild();
        createRuleBuilder().onBuildStatusChanged().execute((t,e)->{
            flowFacade.updateBuildTaskStatusForBuildId(e.getBuildId(), e.getNewStatus());
        });
    }
}
