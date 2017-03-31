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
        Flow flow = new Flow("build CS") {
            @Override
            public void createFlow(FlowContext flowContext) {
                Task createBinaries = createBuildTask("createBinaries");
                Task createSources = createBuildTask("createSources");

                List<Task> tests = new ArrayList<>();
                for(int i=0; i < 2; i++) {
                    Task task = createBuildTask("test" +i);
                    task.dependsOn(createBinaries, createSources);
                    tests.add(task);
                }

                Task javadocTask = createBuildTask("javadocTask");
                javadocTask.dependsOn(createBinaries, createSources);

                Task deployTask = createBuildTask("deployTask");

                Task[] myArray = tests.toArray(new Task[0]);
                deployTask.dependsOn(createBinaries, createSources, javadocTask);
                deployTask.dependsOn(myArray);
            }
        };

        flowFacade.addFlow(flow);


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
