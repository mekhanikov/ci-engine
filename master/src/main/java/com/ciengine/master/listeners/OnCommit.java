package com.ciengine.master.listeners;

import com.ciengine.common.*;
import com.ciengine.common.dto.AddBuildRequest;
import com.ciengine.common.dto.IsModuleReleasedRequest;
import com.ciengine.common.dto.IsModuleReleasedResponse;
import com.ciengine.common.events.OnCommitEvent;
import com.ciengine.common.events.OnNewArtifactEvent;
import com.ciengine.master.facades.CIEngineFacade;
import com.ciengine.master.listeners.impl.oncommit.OnCommitRule;
import com.ciengine.master.listeners.impl.onrelease.OnReleaseRule;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
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

    private List<OnCommitRule> rules = new ArrayList<>();

    private CIEngineEvent ciEngineEvent;
    private boolean eventOk = false;
    public OnCommit(CIEngineEvent ciEngineEvent) {
        this.ciEngineEvent = ciEngineEvent;
        eventOk = ciEngineEvent instanceof OnCommitEvent;
        rules.add(createOnCommitRule("modA", "develop, feature/.*"));
//		onCommitRules.add(createOnCommitRule("modB", "develop"));
//		onCommitRules.add(createOnCommitRule("modC", "develop"));
    }

    public void triggerBuild() {
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

            environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.MODULE_NAME, module.getName());
            environmentVariablesFromEvent.addProperty(EnvironmentVariablesConstants.CIENGINE_MASTER_URL, "http://127.0.0.1:8080"); // TODO to conf?


            // TODO set module specific values

            List<OnCommitRule> onCommitRuleList = getRules();
            for(OnCommitRule onCommitRule : onCommitRuleList) {
                if(isApplicable(onCommitRule, onCommitEvent)) {
                    EnvironmentVariables environmentVariablesFromEventTmp = new EnvironmentVariables();
                    environmentVariablesFromEventTmp.addProperties(environmentVariablesFromEvent.getProperties());
                    String buildExternalId = UUID.randomUUID().toString();
                    environmentVariablesFromEventTmp.addProperty(EnvironmentVariablesConstants.BUILD_EXTERNAL_ID, buildExternalId);
                    AddBuildRequest addBuildRequest = new AddBuildRequest();
                    addBuildRequest.setExecutionList(onCommitRule.getApplyList());
                    addBuildRequest.setNodeId(null);
                    addBuildRequest.setDockerImageId(onCommitRule.getDockerImageId());
                    addBuildRequest.setInputParams(makeString(merge(environmentVariablesFromEventTmp, onCommitRule.getEnvironmentVariables())));
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

    public List<OnCommitRule> getRules()
    {
        // TODO load from OnCommitListener.csv on each event.
        // TODO or only on start? because resources inside jar will never be overriten in runtime?
        return rules;
    }

    private OnCommitRule createOnCommitRule(String forModules, String forBranches)
    {
        OnCommitRule onCommitRule = new OnCommitRule();
//		onCommitRule.setDockerImageId();
        onCommitRule.setApplyList("onCommitList");
//		onCommitRule.setEnvironmentVariables();
        onCommitRule.setForBranches(forBranches);
        onCommitRule.setForModules(forModules);
        return onCommitRule;
    }

    public void setRules(List<OnCommitRule> rules)
    {
        this.rules = rules;
    }
}
