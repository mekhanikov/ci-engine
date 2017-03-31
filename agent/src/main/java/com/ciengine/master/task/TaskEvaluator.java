package com.ciengine.master.task;

import com.ciengine.common.BuildStatus;
import com.ciengine.master.task.Task;

import java.util.List;

/**
 * Created by emekhanikov on 23.03.2017.
 */
public class TaskEvaluator {

    // TODO
    public void evaluate(Task deployTask) {
        if (!needToEvaluate(deployTask)) {
            return;
        }
        // TODO if has failed dependent tasks, mark this task as failed (with mark why).

        if (isAllDependenciesFinishedAndSuccess(deployTask.getDependOnTasks())) {
            runTask(deployTask);
        } else {
            for (Task task : deployTask.getDependOnTasks()) {
                evaluate(task);
            }
        }
    }

    protected boolean needToEvaluate(Task task) {
        return BuildStatus.QUEUED.equals(task.getStatus());
    }

    private void runTask(Task task) {
        // TODO run task in separate thread.
        task.setStatus(BuildStatus.IN_PROGRESS);
        // TODO
    }

    private boolean isAllDependenciesFinishedAndSuccess(List<Task> tasks) {
        if (tasks != null) {
            for (Task task : tasks) {
                if (!isDependenciesFinishedAndSuccess(task)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isDependenciesFinishedAndSuccess(Task task) {
        return BuildStatus.SUCCESS.equals(task.getStatus());
    }

    public void evaluate(List<Task> taskList)
    {// TODO it is not effective?
        for(Task task : taskList) {
            evaluate(task);
        }
    }
}
