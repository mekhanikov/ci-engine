package com.ciengine.master.task.cspipeline;

import com.ciengine.agent.lists.current.CreateBinariesList;
import com.ciengine.common.CIEngineList;
import com.ciengine.common.EnvironmentVariables;
import com.ciengine.common.EnvironmentVariablesConstants;
import com.ciengine.common.dto.*;
import com.ciengine.master.facades.CIEngineFacade;
import com.ciengine.master.listeners.impl.Utils;
import com.ciengine.master.task.BuildTask;
import com.ciengine.master.task.Task;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


/**
 * Created by emekhanikov on 23.03.2017.
 */
public class CreateBinariesTask extends BuildTask
{
    @Autowired
    private CIEngineFacade ciEngineFacade;

    public CreateBinariesTask(String name)
    {
        super(name);
    }

    @Override
    public void run() {
        CreateBinariesList createBinariesList = new CreateBinariesList();

        createBinariesList.setGitUrl("");
        // TODO set all other data.
        addBuild(createBinariesList);
    }
}
