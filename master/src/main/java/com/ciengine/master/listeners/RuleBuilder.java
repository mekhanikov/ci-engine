package com.ciengine.master.listeners;

import com.ciengine.common.CIEngineEvent;
import com.ciengine.master.facades.CIEngineFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Created by evgenymekhanikov on 03.12.16.
 */
public class RuleBuilder {
    @Autowired
    private CIEngineFacade ciEngineFacade;
    @Autowired
    private ApplicationContext applicationContext;

    private CIEngineEvent ciEngineEvent;
    public RuleBuilder(CIEngineEvent ciEngineEvent) {
        this.ciEngineEvent = ciEngineEvent;
    }

    public OnNewArtefact onNewArtefact() {
        OnNewArtefact onNewArtefact = applicationContext.getBean(OnNewArtefact.class, ciEngineEvent);
        return onNewArtefact;
    }

    public OnReleaseSubmited onReleaseSubmited() {
        OnReleaseSubmited onReleaseSubmited = applicationContext.getBean(OnReleaseSubmited.class, ciEngineEvent);
        return onReleaseSubmited;
    }

}
