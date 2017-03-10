package com.ciengine.master.listeners.impl;

import com.ciengine.master.facades.EnvironmentFacade;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.RuleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import sun.misc.Contended;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evgenymekhanikov on 10.03.17.
 */
public abstract class AbstractPipelineImpl implements Pipeline {

    @Autowired
    private ApplicationContext applicationContext;

    public EnvironmentFacade getEnvironmentFacade() {
        return environmentFacade;
    }

    public void setEnvironmentFacade(EnvironmentFacade environmentFacade) {
        this.environmentFacade = environmentFacade;
    }

    @Autowired
    private EnvironmentFacade environmentFacade;

    private List<RuleBuilder> ruleBuilderList = new ArrayList<>();

    @Override
    public List<RuleBuilder> prepare() {
        prepareAll();
        return ruleBuilderList;
    }

    protected abstract void prepareAll();

    protected RuleBuilder createRuleBuilder() {
        RuleBuilder ruleBuilder = applicationContext.getBean(RuleBuilder.class);
        ruleBuilderList.add(ruleBuilder);
        return ruleBuilder;
    }
}
