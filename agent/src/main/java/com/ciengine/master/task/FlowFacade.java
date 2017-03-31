package com.ciengine.master.task;

import com.ciengine.common.events.OnBuildStatusChangedEvent;
import com.ciengine.master.listeners.impl.onbuildstatuschanged.EventExecutor;
import com.ciengine.master.listeners.impl.onbuildstatuschanged.OnBuildStatusChanged;


/**
 * Created by emekhanikov on 24.03.2017.
 */
public interface FlowFacade {
    void updateAll();

    void updateBuildTaskStatusForBuildId(String buildId, String newStatus);

	void triggerFlow(String flowName, FlowContext flowContext);


//	void addFlow(Flow flow);

	void createPrototype(String flowName, FlowExecutor executor);
}
