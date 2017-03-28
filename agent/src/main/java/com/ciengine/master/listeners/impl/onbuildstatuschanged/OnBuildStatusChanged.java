package com.ciengine.master.listeners.impl.onbuildstatuschanged;

import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.EnvironmentVariables;
import com.ciengine.common.EnvironmentVariablesConstants;
import com.ciengine.common.Module;
import com.ciengine.common.dto.AddBuildRequest;
import com.ciengine.common.events.OnBuildStatusChangedEvent;
import com.ciengine.common.events.OnCommitEvent;
import com.ciengine.master.facades.CIEngineFacade;
import com.ciengine.master.facades.EnvironmentData;
import com.ciengine.master.facades.EnvironmentFacade;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.CIEngineListenerBuilder;
import com.ciengine.master.listeners.CIEngineListenerException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;

/**
 * Created by evgenymekhanikov on 03.12.16.
 */
public class OnBuildStatusChanged implements CIEngineListenerBuilder {

    private CIEngineListener ciEngineListener;



    EventExecutor<OnBuildStatusChanged, OnBuildStatusChangedEvent> executor;


    @Override
    public CIEngineListener createCIEngineListener() {
        return ciEngineListener;
    }

    public OnBuildStatusChanged execute(EventExecutor<OnBuildStatusChanged, OnBuildStatusChangedEvent> executor) {
        this.executor = executor;
        final OnBuildStatusChanged onBuildStatusChanged = this;
        ciEngineListener = new CIEngineListener() {
            @Override
            public void onEvent(CIEngineEvent ciEngineEvent) throws CIEngineListenerException {
                if (isEventApplicable(ciEngineEvent)) {
                    OnBuildStatusChangedEvent onBuildStatusChangedEvent = (OnBuildStatusChangedEvent) ciEngineEvent;
                    executor.execute(onBuildStatusChanged, onBuildStatusChangedEvent);
                }
            }

            @Override
            public boolean isEventApplicable(CIEngineEvent ciEngineEvent) {
                return ciEngineEvent instanceof OnBuildStatusChangedEvent;
            }
        };
        return this;
    }
}
