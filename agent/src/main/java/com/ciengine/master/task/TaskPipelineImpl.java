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
        Flow flow = new Flow("build CS") {
            @Override
            void createFlow() {// TODO add FlowContext? to pas arguments from event/action?
                Task createBinaries = new Task("createBinaries");
                Task createSources = new Task("createSources");

                List<Task> tests = new ArrayList<>();
                for(int i=0; i < 2; i++) {
                    Task task = new Task("test" +i);
                    task.dependsOn(createBinaries, createSources);
                    tests.add(task);
                }

                Task javadocTask = new Task("javadocTask");
                javadocTask.dependsOn(createBinaries, createSources);

                Task deployTask = new Task("deployTask");

                Task[] myArray = tests.toArray(new Task[0]);
                deployTask.dependsOn(createBinaries, createSources, javadocTask);
                deployTask.dependsOn(myArray);
            }
        };
        // TODO on commit to pom.xml or on manual trigger (sction?) run flow. O have build?
        // TODO on build status changed (/periodicaly), update according Task nd reevaluate other.
        // /periodicaly
        //
//        createRuleBuilder().onCommit().forModules("modA").forBranches("develop").triggerBuild();
    }
}
