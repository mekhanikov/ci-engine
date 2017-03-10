package com.ciengine.agent.lists.current;


import com.ciengine.common.EnvironmentVariables;
import com.ciengine.common.EnvironmentVariablesConstants;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by emekhanikov on 05.09.2016.
 */
@Component
class MockReleaseList extends AbstractReleaseList
{
	protected String getExecutionListName() {
		return "mockReleaseList";
	}

	protected void build(EnvironmentVariables environmentVariables) {
		//throw new CIEngineStepException("");
		// TODO parse Maven logs for "Uploaded artefacts?"
		// TODO are we interesting in Artefacts released? or in Module released? For Module Released we can not scan logs then.
		System.out.print("d");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected Set<String> allDepsInPlace(EnvironmentVariables environmentVariables) {
		String goingToRelease = environmentVariables.getProperty(EnvironmentVariablesConstants.GOING_TO_RELEASE);
		String moduleNameToRelease = environmentVariables.getProperty(EnvironmentVariablesConstants.MODULE_NAME);
		String url = environmentVariables.getProperty(EnvironmentVariablesConstants.CIENGINE_MASTER_URL);
		Set<String> goingToReleaseModules = new HashSet<>(Arrays.asList(goingToRelease.split(",")));
		String moduleName = moduleNameToRelease.split(":")[0];
		String moduleVersion = moduleNameToRelease.split(":")[1];
		Set<String> requiredModules = new HashSet<>();
		if ("ModA".equals(moduleName)) {
			requiredModules.add("ModB:" + moduleVersion);
			requiredModules.add("ModC:" + moduleVersion);
		}
		if ("ModB".equals(moduleName)) {
			requiredModules.add("ModC:" + moduleVersion);
		}

		Set<String> waitingModules = new HashSet<>();
		for (String requiredModule : requiredModules) {
			if (goingToReleaseModules.contains(requiredModule) && !getCiEngineClient().isModuleReleased(url, requiredModule)) {
				waitingModules.add(requiredModule);
			}
		}
		return waitingModules;
	}
}
