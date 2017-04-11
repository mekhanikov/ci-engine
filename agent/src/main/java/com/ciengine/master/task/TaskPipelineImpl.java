package com.ciengine.master.task;

import com.ciengine.master.listeners.RuleBuilder;
import com.ciengine.master.listeners.impl.AbstractPipelineImpl;
import com.ciengine.master.task.cspipeline.CreateBinariesTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    protected void prepareAll() {
        // TODO ExecutionList should be able create Flow (prototype or/and instance?) with tasks via client (REST-calls).
        // TODO all prototypes exists on agent side, exists on master as soon as master depends on agent,
        // TODO so no needs to create flow-prototype via rest REST-calls. All we need is ability to trigger flow via REST-call.
        flowFacade.createPrototype("build CS", (flowContext)->{
            Flow flow = new Flow();
            Task createBinaries = createCreateBinariesTask("createBinaries");
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

            flow.addTask(deployTask);
            return flow;
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

    private BuildTask createBuildTask(String taskName)
    {
        BuildTask buildTask = (BuildTask) applicationContext.getBean("buildTask", taskName);
        buildTask.setName(taskName);
//        ruleBuilderList.add(ruleBuilder);
        return buildTask;
    }

    private CreateBinariesTask createCreateBinariesTask(String taskName)
    {
        CreateBinariesTask createBinariesTask = (CreateBinariesTask) applicationContext.getBean("createBinariesTask", taskName);
        createBinariesTask.setName(taskName);
        //        ruleBuilderList.add(ruleBuilder);
        return createBinariesTask;
    }

}
