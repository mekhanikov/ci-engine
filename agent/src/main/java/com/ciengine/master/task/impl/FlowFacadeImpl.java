package com.ciengine.master.task.impl;

import com.ciengine.master.task.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by emekhanikov on 24.03.2017.
 */
@Component
public class FlowFacadeImpl implements FlowFacade {
//    private List<Flow> flowPrototypeList = new ArrayList<>();
    private List<Flow> flowInstanceList = new ArrayList<>();

    private Map<String, FlowExecutor> stringFlowExecutorMap = new HashMap<>();

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
        FlowExecutor flowExecutor = findFlowExecutorByName(flowName);
        Flow flow = flowExecutor.execute(flowContext);
        //            flow.createFlow(flowContext);
        flowInstanceList.add(flow);
        taskEvaluator.evaluate(flow.getTaskList());
    }

    private FlowExecutor findFlowExecutorByName(String flowName)
    {
        return stringFlowExecutorMap.get(flowName);
    }

//    @Override public void addFlow(Flow flow)
//    {
//        flowPrototypeList.add(flow);
//    }

    @Override public void createPrototype(String flowName, FlowExecutor executor)
    {
        stringFlowExecutorMap.put(flowName, executor);
    }
}