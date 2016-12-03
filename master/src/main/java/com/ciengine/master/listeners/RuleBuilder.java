package com.ciengine.master.listeners;

import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.EnvironmentVariables;
import com.ciengine.common.EnvironmentVariablesConstants;
import com.ciengine.common.dto.AddBuildRequest;
import com.ciengine.common.dto.IsModuleReleasedRequest;
import com.ciengine.common.dto.IsModuleReleasedResponse;
import com.ciengine.common.events.OnNewArtifactEvent;
import com.ciengine.common.events.OnReleaseSubmitedEvent;
import com.ciengine.master.facades.CIEngineFacade;
import com.ciengine.master.listeners.impl.onrelease.OnReleaseRule;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * Created by evgenymekhanikov on 03.12.16.
 */
public class RuleBuilder {
    @Autowired
    private CIEngineFacade ciEngineFacade;

    private CIEngineEvent ciEngineEvent;
    private boolean eventOk = false;
    public RuleBuilder(CIEngineEvent ciEngineEvent) {
        this.ciEngineEvent = ciEngineEvent;
    }

    public RuleBuilder onNewArtefact() {
        eventOk = ciEngineEvent instanceof OnNewArtifactEvent;
        return this;
    }

    public RuleBuilder processReleaseRule() {
        String reasonOfTrigger = "";
        if (eventOk) {
            OnNewArtifactEvent ciEngineEvent1 = (OnNewArtifactEvent) ciEngineEvent;
            reasonOfTrigger = "Released module: " + ciEngineEvent1.getModuleName();
            EnvironmentVariables environmentVariablesFromEvent = new EnvironmentVariables();
            // TODO set module specific values
            List<OnReleaseRule> onReleaseRuleList = getRules();
            for(OnReleaseRule onReleaseRule : onReleaseRuleList) {
                IsModuleReleasedRequest isModuleReleasedRequest = new IsModuleReleasedRequest();
                isModuleReleasedRequest.setModule(onReleaseRule.getModuleNameToRelease());
                IsModuleReleasedResponse isModuleReleasedResponse = ciEngineFacade.isModuleReleased(isModuleReleasedRequest);
                if (!isModuleReleasedResponse.isReleased()) {
                    // TODO check for dups right here?
                    AddBuildRequest addBuildRequest = new AddBuildRequest();
                    addBuildRequest.setExecutionList(onReleaseRule.getApplyList());
                    addBuildRequest.setNodeId(null);
                    addBuildRequest.setDockerImageId(onReleaseRule.getDockerImageId());
                    addBuildRequest.setModuleName(onReleaseRule.getModuleNameToRelease());
                    //addBuildRequest.setReasonOfTrigger("commit");
                    addBuildRequest.setBranchName(onReleaseRule.getReleaseBranchName());
                    addBuildRequest.setReasonOfTrigger(reasonOfTrigger);
                    String buildExternalId = UUID.randomUUID().toString();
                    addBuildRequest.setExternalId(buildExternalId);
                    environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.BUILD_EXTERNAL_ID, buildExternalId);
                    environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.GOING_TO_RELEASE, onReleaseRule.getGoingToRelease());
                    //onReleaseRule.getApplyList();
                    //onReleaseRule.getDockerImageId();
                    onReleaseRule.getModuleNameToRelease();
                    onReleaseRule.getReleaseBranchName();
                    environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.MERGE_FROM_COMMIT_ID, onReleaseRule.getMergeFromCommitId());
                    environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.MODULE_NAME, onReleaseRule.getModuleNameToRelease());
                    environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.RELEASE_BRANCH_NAME, onReleaseRule.getReleaseBranchName());
                    environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.DOCKER_IMAGE_ID, onReleaseRule.getDockerImageId());
                    environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.CIENGINE_MASTER_URL, "http://127.0.0.1:8080"); // TODO to conf?
                    addBuildRequest.setInputParams(Utils.makeString(Utils.merge(environmentVariablesFromEvent, onReleaseRule.getEnvironmentVariables())));
                    ciEngineFacade.addBuild(addBuildRequest);
                }
            }
        }
        return this;
    }



    public List<OnReleaseRule> getRules()
    {
        return ciEngineFacade.findAllReleases();
    }

    public RuleBuilder onReleaseSubmited() {
        if((ciEngineEvent instanceof OnReleaseSubmitedEvent)) {
            eventOk = true;
        }
        return this;
    }

    public void triggerRelease() {
        if(eventOk) {
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
            environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.BUILD_EXTERNAL_ID, buildExternalId);
            environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.GOING_TO_RELEASE, onReleaseSubmitedEvent.getGoingToRelease());
            environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.MERGE_FROM_COMMIT_ID, onReleaseSubmitedEvent.getMergeFromCommitId());
            environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.MODULE_NAME, onReleaseSubmitedEvent.getModuleNameToRelease());
            environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.RELEASE_BRANCH_NAME, onReleaseSubmitedEvent.getReleaseBranchName());
            environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.DOCKER_IMAGE_ID, onReleaseSubmitedEvent.getDockerImageId());
            environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.CIENGINE_MASTER_URL, "http://127.0.0.1:8080"); // TODO to conf?
            addBuildRequest.setInputParams(Utils.makeString(Utils.merge(environmentVariablesFromEvent, Utils.getEnvironmentVariables(onReleaseSubmitedEvent.getInputParams()))));
            ciEngineFacade.addBuild(addBuildRequest);
        }
    }
}
