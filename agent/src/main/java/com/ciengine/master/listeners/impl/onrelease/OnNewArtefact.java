package com.ciengine.master.listeners.impl.onrelease;

import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.EnvironmentVariables;
import com.ciengine.common.EnvironmentVariablesConstants;
import com.ciengine.common.dto.AddBuildRequest;
import com.ciengine.common.dto.IsModuleReleasedRequest;
import com.ciengine.common.dto.IsModuleReleasedResponse;
import com.ciengine.common.events.OnNewArtifactEvent;
import com.ciengine.master.facades.CIEngineFacade;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.CIEngineListenerBuilder;
import com.ciengine.master.listeners.CIEngineListenerException;
import com.ciengine.master.listeners.OnReleaseRule;
import com.ciengine.master.listeners.impl.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * Created by evgenymekhanikov on 03.12.16.
 */
public class OnNewArtefact implements CIEngineListenerBuilder {
    @Autowired
    private CIEngineFacade ciEngineFacade;

    private CIEngineListener ciEngineListener;

    public OnNewArtefact processReleaseRule() {
        ciEngineListener = new CIEngineListener() {
            @Override
            public void onEvent(CIEngineEvent ciEngineEvent) throws CIEngineListenerException {
                if (isEventApplicable(ciEngineEvent)) {
                    String reasonOfTrigger = "";
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
                            String moduleNameToReleaseWithoutVersion = onReleaseRule.getModuleNameToRelease().substring(0, onReleaseRule.getModuleNameToRelease().lastIndexOf(':'));
                            String gitUrl = ciEngineFacade.findGitUrlByModuleName(moduleNameToReleaseWithoutVersion);
                            environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.GIT_URL, gitUrl);
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
                            environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.CIENGINE_MASTER_URL, "http://127.0.0.1:8081"); // TODO to conf?
                            addBuildRequest.setInputParams(Utils.makeString(Utils.merge(environmentVariablesFromEvent, onReleaseRule.getEnvironmentVariables())));
                            ciEngineFacade.addBuild(addBuildRequest);
                        }
                    }
                }

            }

            @Override
            public boolean isEventApplicable(CIEngineEvent defaultCIEngineEvent) {
                return defaultCIEngineEvent instanceof OnNewArtifactEvent;
            }
        };
        return this;
    }



    public List<OnReleaseRule> getRules()
    {
        return ciEngineFacade.findAllReleases();
    }

    @Override
    public CIEngineListener createCIEngineListener() {
        return ciEngineListener;
    }
}
