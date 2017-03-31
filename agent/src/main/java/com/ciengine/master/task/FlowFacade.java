package com.ciengine.master.task;

/**
 * Created by emekhanikov on 24.03.2017.
 */
public interface FlowFacade {
    void updateAll();

    void updateBuildTaskStatusForBuildId(String buildId, String newStatus);

	void triggerFlow(String flowName, FlowContext flowContext);


	void addFlow(Flow flow);
}
