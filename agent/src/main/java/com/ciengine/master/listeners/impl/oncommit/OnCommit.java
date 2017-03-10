package com.ciengine.master.listeners.impl.oncommit;

import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.EnvironmentVariables;
import com.ciengine.common.EnvironmentVariablesConstants;
import com.ciengine.common.Module;
import com.ciengine.common.dto.AddBuildRequest;
import com.ciengine.common.events.OnCommitEvent;
import com.ciengine.master.facades.CIEngineFacade;
import com.ciengine.master.facades.EnvironmentData;
import com.ciengine.master.facades.EnvironmentFacade;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.CIEngineListenerBuilder;
import com.ciengine.master.listeners.CIEngineListenerException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;

/**
 * Created by evgenymekhanikov on 03.12.16.
 */
public class OnCommit implements CIEngineListenerBuilder {
    @Autowired
    private CIEngineFacade ciEngineFacade;

    @Autowired
    private EnvironmentFacade environmentFacade;

    private CIEngineListener ciEngineListener;

//    private List<OnCommitRule> rules = new ArrayList<>();
    //private OnCommitRule rule;

//    private CIEngineEvent ciEngineEvent;
//    private boolean eventOk = false;
    private String modules=".*";
    private String branches=".*";
//    private String applyList;
    private String mergeFromBranchName;
    private boolean crossBuildEnabled;

//    public OnCommit(CIEngineEvent ciEngineEvent) {
//        this.ciEngineEvent = ciEngineEvent;
//        eventOk = ciEngineEvent instanceof OnCommitEvent;
//        //rules.add(createOnCommitRule("modA", "develop, feature/.*"));
////		onCommitRules.add(createOnCommitRule("modB", "develop"));
////		onCommitRules.add(createOnCommitRule("modC", "develop"));
//    }

    public OnCommit triggerBuild() {
        ciEngineListener = new CIEngineListener() {
            @Override
            public void onEvent(CIEngineEvent ciEngineEvent) throws CIEngineListenerException {
                if (isEventApplicable(ciEngineEvent)) {
                    OnCommitEvent onCommitEvent = (OnCommitEvent) ciEngineEvent;
                    Module module = ciEngineFacade.findModuleByGitUrl(((OnCommitEvent) ciEngineEvent).getGitUrl());
                    if (module == null) {
                        // TODO add warning?
                        return;
                    }
                    EnvironmentVariables environmentVariablesFromEvent = new EnvironmentVariables();
                    environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.GIT_URL, onCommitEvent.getGitUrl());
                    environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.BRANCH_NAME, onCommitEvent.getBranchName());
                    environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.COMMIT_ID, onCommitEvent.getComitId());
                    if (mergeFromBranchName != null) {
                        environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.MERGE_FROM_BRANCH_NAME, mergeFromBranchName);
                    }
                    if (crossBuildEnabled) {
                        environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.ENABLE_CROSS_BUILD, "true");
                    }
                    environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.MODULE_NAME, module.getName());
                    environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.CIENGINE_MASTER_URL, "http://127.0.0.1:8081"); // TODO to conf?


                    // TODO set module specific values
                    // TODO if applyList is not specified find in buildLists by module branch.
//            if (applyList == null) {
                    EnvironmentData environmentData = environmentFacade.findApplyList(module.getName(), onCommitEvent.getBranchName());
//                applyList = environmentData != null ? environmentData.getApplyList() : null;
//            }
//            EnvironmentData environmentData = environmentFacade.findApplyList(module.getName(), onCommitEvent.getBranchName());
                    if(environmentData != null) {

                        EnvironmentVariables environmentVariablesFromEventTmp = new EnvironmentVariables();
                        environmentVariablesFromEventTmp.addProperties(environmentVariablesFromEvent.getProperties());
                        String buildExternalId = UUID.randomUUID().toString();
                        environmentVariablesFromEventTmp.addProperty(EnvironmentVariablesConstants.BUILD_EXTERNAL_ID, buildExternalId);
                        AddBuildRequest addBuildRequest = new AddBuildRequest();
                        addBuildRequest.setExecutionList(environmentData.getApplyList());
                        addBuildRequest.setNodeId(null);
                        addBuildRequest.setDockerImageId(environmentData.getDockerImageId());
                        addBuildRequest.setInputParams(makeString(merge(environmentVariablesFromEventTmp, environmentData.getEnvironmentVariables())));
                        addBuildRequest.setModuleName(module.getName());
                        addBuildRequest.setReasonOfTrigger("commit");
                        addBuildRequest.setBranchName(onCommitEvent.getBranchName());
                        addBuildRequest.setExternalId(buildExternalId);
                        ciEngineFacade.addBuild(addBuildRequest);
//				ciEngineFacade.onEvent(onQueueBuildEvent);

//				try
//				{
//
//				}
//				catch (CIEngineException e)
//				{
//					throw new CIEngineListenerException(e);
//				}
                    }

                }
            }

            @Override
            public boolean isEventApplicable(CIEngineEvent ciEngineEvent) {
                return ciEngineEvent instanceof OnCommitEvent;
            }
        };
        return this;
    }

    private String makeString(EnvironmentVariables merge)
    {
        StringBuilder stringBuilder = new StringBuilder();
        if (merge != null && merge.getProperties() != null) {
            for (Map.Entry<String, Object> kvEntry : merge.getProperties().entrySet()) {
                stringBuilder.append(kvEntry.getKey());
                stringBuilder.append("=");
                stringBuilder.append(kvEntry.getValue());
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    private EnvironmentVariables merge(EnvironmentVariables environmentVariablesFromEvent,
                                       EnvironmentVariables environmentVariables)
    {
        EnvironmentVariables environmentVariablesMerged = new EnvironmentVariables();
        if (environmentVariablesFromEvent != null && environmentVariablesFromEvent.getProperties() != null) {
            environmentVariablesMerged.addProperties(environmentVariablesFromEvent.getProperties());
        }
        if (environmentVariables != null && environmentVariables.getProperties() != null) {
            environmentVariablesMerged.addProperties(environmentVariables.getProperties());
        }

        return environmentVariablesMerged;
    }

//    private OnCommitRule createOnCommitRule(String forModules, String forBranches, String applyList)
//    {
//        OnCommitRule onCommitRule = new OnCommitRule();
////		onCommitRule.setDockerImageId();
//        onCommitRule.setApplyList(applyList);//"onCommitList"
////		onCommitRule.setEnvironmentVariables();
//        onCommitRule.setForBranches(forBranches);
//        onCommitRule.setForModules(forModules);
//        return onCommitRule;
//    }

    public OnCommit forModules(String modules) {
        this.modules = modules;
     return this;
    }

    public OnCommit forBranches(String branches) {
        this.branches = branches;
        return this;
    }
//
//    public OnCommit applyList(String applyList) {
//        this.applyList = applyList;
//        return this;
//    }

    public void triggerBuildsFor(String modules, String branches) {
        // TODO find all related module/branch and resolve applyList for them and trigger.
    }

    public OnCommit enableAutomergeFrom(String mergeFromBranchName) {
        this.mergeFromBranchName = mergeFromBranchName;
        return this;
    }

    public OnCommit enableCrossBuild() {
        this.crossBuildEnabled = true;
        return this;
    }

    @Override
    public CIEngineListener createCIEngineListener() {
        return ciEngineListener;
    }
}
