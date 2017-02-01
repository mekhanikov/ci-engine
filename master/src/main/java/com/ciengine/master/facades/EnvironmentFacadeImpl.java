package com.ciengine.master.facades;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by emekhanikov on 15.12.2016.
 */
//@Transactional
@Component
public class EnvironmentFacadeImpl implements EnvironmentFacade {

    private List<EnvironmentData> environmentDataList = new ArrayList<>();

    public EnvironmentFacadeImpl() {
        // TODO each module may have own, need add all of them
        createEnvironmentData("modA", "develop", "onCommitList", "dockerid");
        createEnvironmentData("modA", "feature/.*", "onCommitList", "dockerid");
        createEnvironmentData("ModA", "release/.*", "mockReleaseList", "dockerid");
        createEnvironmentData("ModB", "release/.*", "mockReleaseList", "dockerid");
        createEnvironmentData("ModC", "release/.*", "mockReleaseList", "dockerid");
        createEnvironmentData("subscriptions-module", "release/.*", "releaseList", "dockerid");
//        createEnvironmentData("modA", "feature/.*", "onCommitList", "dockerid");


//        createRuleBuilder(ciEngineEvent).onCommit().forModules("modA").forBranches("develop").triggerBuild();

//        createRuleBuilder(ciEngineEvent).onCommit().forModules("modA").
//                forBranches("feature/.*").enableAutomergeFrom("develop").enableCrossBuild().triggerBuild();
//        createRuleBuilder(ciEngineEvent).onCommit().forModules("modA").forBranches("develop").triggerBuildsFor("modA", "feature/.*");


    }

    private void createEnvironmentData(String forModules, String forBranches, String applyList, String dockerImageId) {
        EnvironmentData environmentData = new EnvironmentData(forModules, forBranches, applyList, dockerImageId, null);
        environmentDataList.add(environmentData);
    }


    @Override
    public List<EnvironmentData> findApplyLists(String moduleName, String branchName) {
        List<EnvironmentData> result = new ArrayList<>();
        for (EnvironmentData environmentData : environmentDataList) {
            if(isApplicable(environmentData, moduleName, branchName)) {
                result.add(environmentData);
            }
        }
        return result;
    }

    @Override
    public EnvironmentData findApplyList(String moduleName, String branchName) {
        List<EnvironmentData> result = new ArrayList<>();
        for (EnvironmentData environmentData : environmentDataList) {
            if(isApplicable(environmentData, moduleName, branchName)) {
                return environmentData;
            }
        }
        return null;
    }


    private boolean isApplicable(EnvironmentData environmentData, String moduleName, String branchName)
    {
        boolean branchIsApplicable = isMach(environmentData.getForBranches(), branchName);
        boolean moduleIsApplicable = isMach(environmentData.getForModules(), moduleName);
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
}
