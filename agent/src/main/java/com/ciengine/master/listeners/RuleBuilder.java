package com.ciengine.master.listeners;

import com.ciengine.common.CIEngineEvent;
import com.ciengine.master.facades.CIEngineFacade;
import com.ciengine.master.listeners.impl.oncommit.OnCommit;
import com.ciengine.master.listeners.impl.onrelease.OnNewArtefact;
import com.ciengine.master.listeners.impl.onrelease.OnReleaseSubmited;
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


    public OnNewArtefact onNewArtefact() {
        OnNewArtefact onNewArtefact = applicationContext.getBean(OnNewArtefact.class);
        return onNewArtefact;
    }

    public OnReleaseSubmited onReleaseSubmited() {
        OnReleaseSubmited onReleaseSubmited = applicationContext.getBean(OnReleaseSubmited.class);
        return onReleaseSubmited;
    }

    public OnCommit onCommit() {
        OnCommit onCommit = applicationContext.getBean(OnCommit.class);
        return onCommit;
    }
}
