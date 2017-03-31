package com.ciengine.master.task.impl;

import com.ciengine.master.task.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emekhanikov on 24.03.2017.
 */
@Component
public class FlowFacadeImpl implements FlowFacade {
    private List<Flow> flowPrototypeList = new ArrayList<>();
    private List<Flow> flowInstanceList = new ArrayList<>();

    private TaskEvaluator taskEvaluator = new TaskEvaluator();

    @Override
    public void updateAll() {

    }

    @Override
    public void updateBuildTaskStatusForBuildId(String buildId, String newStatus) {
        for (Flow flow : flowInstanceList) {
            boolean needEvaluate = false;
            for(Task task : flow.getTaskList()) {
                if (task instanceof BuildTask) {
                    BuildTask buildTask = (BuildTask)task;
                    if (buildId.equals(buildTask.getBuildId()) && !newStatus.equals(buildTask.getStatus())) {
                        buildTask.setStatus(newStatus);
                        needEvaluate = true;
                        // TODO break? only one Task per Build?
                    }
                }
            }
            if (needEvaluate) {
                taskEvaluator.evaluate(flow.getTaskList());
            }
        }
    }

    @Override public void triggerFlow(String flowName, FlowContext flowContext)
    {
        Flow flow = findFlowByName(flowName);
        if (flow != null) {
            flow.createFlow(flowContext);
            flowInstanceList.add(flow);
            taskEvaluator.evaluate(flow.getTaskList());
        }
    }

    private Flow findFlowByName(String flowName)
    {
        for (Flow flow : flowPrototypeList) {
            if(flowName.equals(flow.getName())) {
                return flow;
            }
        }
        return null;
    }

    @Override public void addFlow(Flow flow)
    {
        flowPrototypeList.add(flow);
    }
}
