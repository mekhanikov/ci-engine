package com.ciengine.master.task;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by emekhanikov on 24.03.2017.
 */
public class Flow {
//    private String name;
    private List<Task> taskList = new ArrayList<>();

//    public Flow(String name) {
//this.name = name;
//    }

    void addTask(Task task)
    {
        taskList.add(task);
    }

//    public abstract void createFlow(FlowContext flowContext);

    BuildTask createBuildTask(String name) {
        BuildTask buildTask = new BuildTask(name);
        addTask(buildTask);
        return buildTask;
    }

//    public String getName()
//    {
//        return name;
//    }
//
//    public void setName(String name)
//    {
//        this.name = name;
//    }

    public List<Task> getTaskList()
    {
        return taskList;
    }

    public void setTaskList(List<Task> taskList)
    {
        this.taskList = taskList;
    }
}
