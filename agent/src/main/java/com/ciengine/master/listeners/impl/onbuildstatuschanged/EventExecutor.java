package com.ciengine.master.listeners.impl.onbuildstatuschanged;

import com.ciengine.common.CIEngineEvent;

/**
 * Created by emekhanikov on 28.03.2017.
 */
public interface EventExecutor<T, E extends CIEngineEvent> {
    void execute(T context, E event);
}
