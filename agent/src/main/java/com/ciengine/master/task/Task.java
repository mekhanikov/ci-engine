package com.ciengine.master.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emekhanikov on 23.03.2017.
 */
public class Task {
    private String name;
    private List<Task> dependOnTasks = new ArrayList<>();
    private boolean finished;
    private boolean inProgress;
    private boolean success;

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

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }


    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
