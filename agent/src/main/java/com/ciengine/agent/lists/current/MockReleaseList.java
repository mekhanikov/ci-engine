package com.ciengine.agent.lists.current;


import com.ciengine.common.EnvironmentVariables;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by emekhanikov on 05.09.2016.
 */
@Component
public class MockReleaseList extends ReleaseList
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

	protected Set<String> allDepsInPlace(String url, String module0, String goingToRelease) {
		Set<String> goingToReleaseModules = new HashSet<>(Arrays.asList(goingToRelease.split(",")));
		String moduleName = module0.split(":")[0];
		String moduleVersion = module0.split(":")[1];
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
