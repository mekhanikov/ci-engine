package com.ciengine.master.task;

/**
 * Created by emekhanikov on 24.03.2017.
 */
public abstract class Flow {
    private String name;
    public Flow(String name) {
this.name = name;
    }

    abstract void createFlow(FlowContext flowContext);
}
