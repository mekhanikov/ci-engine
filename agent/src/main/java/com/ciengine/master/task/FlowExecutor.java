package com.ciengine.master.task;

import com.ciengine.common.CIEngineEvent;


/**
 * Created by emekhanikov on 28.03.2017.
 */
public interface FlowExecutor {
    Flow execute(FlowContext context);
}
