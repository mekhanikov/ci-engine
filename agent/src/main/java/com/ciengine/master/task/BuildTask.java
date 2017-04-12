package com.ciengine.master.task;

import com.ciengine.common.CIEngineList;
import com.ciengine.common.EnvironmentVariables;
import com.ciengine.common.EnvironmentVariablesConstants;
import com.ciengine.common.dto.*;
import com.ciengine.master.facades.CIEngineFacade;
import com.ciengine.master.listeners.impl.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


/**
 * Created by emekhanikov on 23.03.2017.
 */
public class BuildTask extends Task {
    private String buildId;

//    private EnvironmentData environmentData;
    private String moduleName = "ModA";
    private String branchName = "develop";
    private String applyList = "createBinariesList";
    private String dockerImageId = "dockerid";
    private EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @Autowired
    private CIEngineFacade ciEngineFacade;

    public BuildTask(String name) {
        super(name);
    }

    @Override
    public void run() {
        String buildExternalId = UUID.randomUUID().toString();
        EnvironmentVariables environmentVariablesFromEvent = new EnvironmentVariables();
        environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.BUILD_EXTERNAL_ID, buildExternalId);
        AddBuildRequest addBuildRequest = new AddBuildRequest();
        addBuildRequest.setExecutionList(applyList);
        addBuildRequest.setNodeId(null);
        addBuildRequest.setDockerImageId(dockerImageId);
        addBuildRequest.setInputParams(Utils.makeString(Utils.merge(environmentVariablesFromEvent, environmentVariables)));
//        addBuildRequest.setModuleName(moduleName);// TODO add to metadata
        addBuildRequest.setReasonOfTrigger("BuildTask");
        addBuildRequest.setBranchName(branchName);
        addBuildRequest.setExternalId(buildExternalId);
        AddBuildResponse addBuildResponse = ciEngineFacade.addBuild(addBuildRequest);
        buildId = buildExternalId;
    }

    public void update() {
        if (buildId != null) {
            FindBuildsRequest findBuildsRequest = new FindBuildsRequest();
            FindBuildsResponse findBuildsResponse = ciEngineFacade.findBuild(findBuildsRequest);
            Build build = findBuildsResponse.getBuildList().get(0);
            setStatus(build.getStatus());
        }
    }

    protected void addBuild(CIEngineList createBinariesList)
    {
        createBinariesList.setCIEngineMasterUrl("http://127.0.0.1:8081");
        EnvironmentVariables environmentVariables = createBinariesList.createEnvironmentVariables();
        String buildExternalId = UUID.randomUUID().toString();
        EnvironmentVariables environmentVariablesFromEvent = new EnvironmentVariables();
        environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.BUILD_EXTERNAL_ID, buildExternalId);
        AddBuildRequest addBuildRequest = new AddBuildRequest();
        addBuildRequest.setExecutionList(createBinariesList.getBeanName());
        addBuildRequest.setNodeId(null);
        addBuildRequest.setDockerImageId(dockerImageId);
        addBuildRequest.setInputParams(Utils.makeString(Utils.merge(environmentVariablesFromEvent, environmentVariables)));
//        addBuildRequest.setModuleName(moduleName);// TODO add to metadata
        addBuildRequest.setReasonOfTrigger("BuildTask");
        addBuildRequest.setBranchName(branchName);
        addBuildRequest.setExternalId(buildExternalId);
        AddBuildResponse addBuildResponse = ciEngineFacade.addBuild(addBuildRequest);
        buildId = buildExternalId;
    }

    public String getBuildId()
    {
        return buildId;
    }

    public void setBuildId(String buildId)
    {
        this.buildId = buildId;
    }
}
