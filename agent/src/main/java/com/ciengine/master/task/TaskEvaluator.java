package com.ciengine.master.task;

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
        return !(task.isFinished() || task.isInProgress());
    }

    private void runTask(Task task) {
        // TODO run task in separate thread.
        task.setInProgress(true);
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
        return task.isFinished() && task.isSuccess();
    }
}
