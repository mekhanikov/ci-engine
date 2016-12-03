package com.ciengine.master.listeners.impl.oncommit;

import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.EnvironmentVariables;
import com.ciengine.common.EnvironmentVariablesConstants;
import com.ciengine.common.Module;
import com.ciengine.common.dto.AddBuildRequest;
import com.ciengine.common.events.OnCommitEvent;
import com.ciengine.master.facades.CIEngineFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by evgenymekhanikov on 03.12.16.
 */
public class OnCommit {
    @Autowired
    private CIEngineFacade ciEngineFacade;

//    private List<OnCommitRule> rules = new ArrayList<>();
    //private OnCommitRule rule;

    private CIEngineEvent ciEngineEvent;
    private boolean eventOk = false;
    private String modules=".*";
    private String branches=".*";
    private String applyList;
    private String mergeFromBranchName;

    public OnCommit(CIEngineEvent ciEngineEvent) {
        this.ciEngineEvent = ciEngineEvent;
        eventOk = ciEngineEvent instanceof OnCommitEvent;
        //rules.add(createOnCommitRule("modA", "develop, feature/.*"));
//		onCommitRules.add(createOnCommitRule("modB", "develop"));
//		onCommitRules.add(createOnCommitRule("modC", "develop"));
    }

    public void triggerBuilds() {
        if (eventOk) {
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
            environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.MODULE_NAME, module.getName());
            environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.CIENGINE_MASTER_URL, "http://127.0.0.1:8080"); // TODO to conf?


            // TODO set module specific values
            // TODO if applyList is not specified find in buildLists by module branch.
            if (applyList == null) {
                applyList = findApplyListFor(module.getName(), onCommitEvent.getBranchName());
            }
            OnCommitRule rule = createOnCommitRule(modules, branches, applyList);
                if(isApplicable(rule, onCommitEvent)) {

                    EnvironmentVariables environmentVariablesFromEventTmp = new EnvironmentVariables();
                    environmentVariablesFromEventTmp.addProperties(environmentVariablesFromEvent.getProperties());
                    String buildExternalId = UUID.randomUUID().toString();
                    environmentVariablesFromEventTmp.addProperty(EnvironmentVariablesConstants.BUILD_EXTERNAL_ID, buildExternalId);
                    AddBuildRequest addBuildRequest = new AddBuildRequest();
                    addBuildRequest.setExecutionList(rule.getApplyList());
                    addBuildRequest.setNodeId(null);
                    addBuildRequest.setDockerImageId(rule.getDockerImageId());
                    addBuildRequest.setInputParams(makeString(merge(environmentVariablesFromEventTmp, rule.getEnvironmentVariables())));
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

    private String findApplyListFor(String name, String branchName) {
        // TODO search in BuildApplyListMap
        return "onCommitList";
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

    private boolean isApplicable(OnCommitRule onCommitRule, OnCommitEvent onCommitEvent)
    {
        Module module = ciEngineFacade.findModuleByGitUrl(onCommitEvent.getGitUrl());
        if (module == null) {
            return false;
        }
        boolean branchIsApplicable = isMach(onCommitRule.getForBranches(), onCommitEvent.getBranchName());
        boolean moduleIsApplicable = isMach(onCommitRule.getForModules(), module.getName());
        return branchIsApplicable && moduleIsApplicable;
    }

    private boolean isMach(String commaSeparatedRegexps, String stringToMach)
    {
        boolean branchIsApplicable = false;
        String[] branches = commaSeparatedRegexps.split(",");
        for (String s : branches) {
            String pattern = "(" + s.trim() + ")";
            // Create a Pattern object
            Pattern r = Pattern.compile(pattern);
            // Now create matcher object.
            Matcher m = r.matcher(stringToMach);
            if (m.find()) {
                branchIsApplicable = true;
            }
        }
        return branchIsApplicable;
    }

    private OnCommitRule createOnCommitRule(String forModules, String forBranches, String applyList)
    {
        OnCommitRule onCommitRule = new OnCommitRule();
//		onCommitRule.setDockerImageId();
        onCommitRule.setApplyList(applyList);//"onCommitList"
//		onCommitRule.setEnvironmentVariables();
        onCommitRule.setForBranches(forBranches);
        onCommitRule.setForModules(forModules);
        return onCommitRule;
    }

    public OnCommit forModules(String modules) {
        this.modules = modules;
     return this;
    }

    public OnCommit forBranches(String branches) {
        this.branches = branches;
        return this;
    }

    public OnCommit applyList(String applyList) {
        this.applyList = applyList;
        return this;
    }

    public void triggerBuildsFor(String modules, String branches) {
        // TODO find all related module/branch and resolve applyList for them and trigger.
    }

    public OnCommit enableAutomergeFrom(String mergeFromBranchName) {
        this.mergeFromBranchName = mergeFromBranchName;
        return this;
    }
}
