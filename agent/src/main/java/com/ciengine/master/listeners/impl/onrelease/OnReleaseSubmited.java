package com.ciengine.master.listeners.impl.onrelease;

import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.EnvironmentVariables;
import com.ciengine.common.EnvironmentVariablesConstants;
import com.ciengine.common.dto.AddBuildRequest;
import com.ciengine.common.events.OnReleaseSubmitedEvent;
import com.ciengine.master.facades.CIEngineFacade;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.CIEngineListenerBuilder;
import com.ciengine.master.listeners.CIEngineListenerException;
import com.ciengine.master.listeners.impl.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * Created by evgenymekhanikov on 03.12.16.
 */
public class OnReleaseSubmited  implements CIEngineListenerBuilder {
    @Autowired
    private CIEngineFacade ciEngineFacade;

    private CIEngineListener ciEngineListener;

    public OnReleaseSubmited triggerRelease() {
        ciEngineListener = new CIEngineListener() {
            @Override
            public void onEvent(CIEngineEvent ciEngineEvent) throws CIEngineListenerException {
                if (isEventApplicable(ciEngineEvent)) {
                    OnReleaseSubmitedEvent onReleaseSubmitedEvent = (OnReleaseSubmitedEvent) ciEngineEvent;
                    String reasonOfTrigger = "Added ReleaseRule for: " + onReleaseSubmitedEvent.getModuleNameToRelease();
                    // TODO run only current one?
                    AddBuildRequest addBuildRequest = new AddBuildRequest();
                    addBuildRequest.setExecutionList(onReleaseSubmitedEvent.getApplyList());
                    addBuildRequest.setNodeId(null);
                    addBuildRequest.setDockerImageId(onReleaseSubmitedEvent.getDockerImageId());
                    addBuildRequest.setModuleName(onReleaseSubmitedEvent.getModuleNameToRelease());
                    addBuildRequest.setBranchName(onReleaseSubmitedEvent.getReleaseBranchName());
                    addBuildRequest.setReasonOfTrigger(reasonOfTrigger);
                    String buildExternalId = UUID.randomUUID().toString();
                    addBuildRequest.setExternalId(buildExternalId);
                    EnvironmentVariables environmentVariablesFromEvent = new EnvironmentVariables();
                    String moduleNameToReleaseWithoutVersion = onReleaseSubmitedEvent.getModuleNameToRelease().substring(0, onReleaseSubmitedEvent.getModuleNameToRelease().lastIndexOf(':'));
                    String gitUrl = ciEngineFacade.findGitUrlByModuleName(moduleNameToReleaseWithoutVersion);
                    environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.GIT_URL, gitUrl);
                    environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.BUILD_EXTERNAL_ID, buildExternalId);
                    environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.GOING_TO_RELEASE, onReleaseSubmitedEvent.getGoingToRelease());
                    environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.MERGE_FROM_COMMIT_ID, onReleaseSubmitedEvent.getMergeFromCommitId());
                    environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.MODULE_NAME, onReleaseSubmitedEvent.getModuleNameToRelease());
                    environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.RELEASE_BRANCH_NAME, onReleaseSubmitedEvent.getReleaseBranchName());
                    environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.DOCKER_IMAGE_ID, onReleaseSubmitedEvent.getDockerImageId());
                    environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.CIENGINE_MASTER_URL, "http://127.0.0.1:8081"); // TODO to conf?
                    addBuildRequest.setInputParams(Utils.makeString(Utils.merge(environmentVariablesFromEvent, Utils.getEnvironmentVariables(onReleaseSubmitedEvent.getInputParams()))));
                    ciEngineFacade.addBuild(addBuildRequest);
                }
            }

            @Override
            public boolean isEventApplicable(CIEngineEvent ciEngineEvent) {
                return ciEngineEvent instanceof OnReleaseSubmitedEvent;
            }
        };
        return this;
    }


    @Override
    public CIEngineListener createCIEngineListener() {
        return ciEngineListener;
    }
}
