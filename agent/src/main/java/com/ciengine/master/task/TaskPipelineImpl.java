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
    private TaskFacade taskFacade;

    @Override
    protected void prepareAll() {
        Flow flow = new Flow("build CS") {
            @Override
            void createFlow(FlowContext flowContext) {
                Task createBinaries = new BuildTask("createBinaries");
                Task createSources = new BuildTask("createSources");

                List<Task> tests = new ArrayList<>();
                for(int i=0; i < 2; i++) {
                    Task task = new BuildTask("test" +i);
                    task.dependsOn(createBinaries, createSources);
                    tests.add(task);
                }

                Task javadocTask = new BuildTask("javadocTask");
                javadocTask.dependsOn(createBinaries, createSources);

                Task deployTask = new BuildTask("deployTask");

                Task[] myArray = tests.toArray(new Task[0]);
                deployTask.dependsOn(createBinaries, createSources, javadocTask);
                deployTask.dependsOn(myArray);
            }
        };
        FlowContext flowContext = new FlowContext();
        flow.createFlow(flowContext);
        // TODO on commit to pom.xml or on manual trigger (sction?) run flow. O have build?
        // TODO on build status changed (/periodicaly), update according Task nd reevaluate other.
        // /periodicaly
        //
//        createRuleBuilder().onCommit().forModules("modA").forBranches("develop").triggerBuild();
        createRuleBuilder().onBuildStatusChanged().execute(t->{});
    }
}
