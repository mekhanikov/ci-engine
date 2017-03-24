package com.ciengine.master.task;

import com.ciengine.common.BuildStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emekhanikov on 23.03.2017.
 */
public class Task {
    private String name;
    private List<Task> dependOnTasks = new ArrayList<>();
    private String status = BuildStatus.QUEUED;

    public Task(String name) {
        this.name = name;
    }

    public void dependsOn(Task... task) {
        for(int i = 0; i < task.length; i++){
            dependOnTasks.add(task[i]);
        }
    }

    public void run() {
        // If finidhed - return?
        //
    }

    public List<Task> getDependOnTasks() {
        return dependOnTasks;
    }

    public void setDependOnTasks(List<Task> dependOnTasks) {
        this.dependOnTasks = dependOnTasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
