package com.ciengine.agent.lists.current;


import com.ciengine.agent.Utils;
import com.ciengine.common.EnvironmentVariables;
import com.ciengine.common.EnvironmentVariablesConstants;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import static java.nio.file.StandardCopyOption.*;

/**
 * Created by emekhanikov on 05.09.2016.
 */
@Component
public class ReleaseList extends AbstractReleaseList
{
	protected String getExecutionListName() {
		return "releaseList";
	}

	protected void build(EnvironmentVariables environmentVariables) {
		String moduleNameToRelease = environmentVariables.getProperty(EnvironmentVariablesConstants.MODULE_NAME);
		String goingToRelease = environmentVariables.getProperty(EnvironmentVariablesConstants.GOING_TO_RELEASE);
		//throw new CIEngineStepException("");
		// TODO parse Maven logs for "Uploaded artefacts?"
		// TODO are we interesting in Artefacts released? or in Module released? For Module Released we can not scan logs then.
		System.out.print("d");

		String workspace = Utils.getWorkspace() + "/" + moduleNameToRelease.replace(":", "_");
//		Set<String> goingToReleaseModules = new HashSet<>();
		Map<String, String> map = new HashMap<>();
		for (String goingToReleaseModule : Arrays.asList(goingToRelease.split(","))) {
			String moduleWithoutVersion = goingToReleaseModule.substring(0, goingToReleaseModule.lastIndexOf(':'));
			String moduleVersion = extractVersion(moduleNameToRelease);
			map.put(moduleWithoutVersion, moduleVersion);
		}
		String moduleVersion = extractVersion(moduleNameToRelease);
		Utils.updateDependenciesAndVersion(workspace + "/pom.xml", map, moduleVersion);

		try {
			String s = Utils.executeCommand(workspace, "mvn.bat", "clean", "install");
			System.out.print(s);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected Set<String> allDepsInPlace(EnvironmentVariables environmentVariables) {
		String gitUrl = environmentVariables.getProperty(EnvironmentVariablesConstants.GIT_URL);
		String branchName = environmentVariables.getProperty(EnvironmentVariablesConstants.RELEASE_BRANCH_NAME);
		String commitId = environmentVariables.getProperty(EnvironmentVariablesConstants.COMMIT_ID);
		String buildId = environmentVariables.getProperty(EnvironmentVariablesConstants.BUILD_EXTERNAL_ID);
		String goingToRelease = environmentVariables.getProperty(EnvironmentVariablesConstants.GOING_TO_RELEASE);
		String moduleNameToRelease = environmentVariables.getProperty(EnvironmentVariablesConstants.MODULE_NAME);
		String url = environmentVariables.getProperty(EnvironmentVariablesConstants.CIENGINE_MASTER_URL);
		String dockerImageId = environmentVariables.getProperty(EnvironmentVariablesConstants.DOCKER_IMAGE_ID);

//		String workspace = Utils.getWorkspace() + "/" + buildId;
		String workspace = Utils.getWorkspace() + "/" + moduleNameToRelease.replace(":", "_");

		Utils.clone(gitUrl, branchName, workspace + "/source");
		try {
			Files.copy(FileSystems.getDefault().getPath(workspace + "/source/pom.xml"),
                    FileSystems.getDefault().getPath(workspace + "/pom.xml"),
                    REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> dependencies = Utils.retrieveDependencies(workspace + "/pom.xml");
		Set<String> requiredModules = new HashSet<>(dependencies);

		// TODO checkot sources for module how to get GIT_URL? Should be passed to the build!?
		// TODO Get artefacts dep from pom.xml map them to modules (how?)
		Set<String> goingToReleaseModules = new HashSet<>(Arrays.asList(goingToRelease.split(",")));
//		String moduleName = moduleNameToRelease.split(":")[0];
		//String moduleVersion = moduleNameToRelease.substring(moduleNameToRelease.lastIndexOf(':')+1, moduleNameToRelease.length());
		//goingToReleaseModules = goingToReleaseModules.stream().map(it -> it.substring(0, it.lastIndexOf(':'))).collect(Collectors.toSet());
		//String moduleNameToReleaseWithoutVersion = onReleaseSubmitedEvent.getModuleNameToRelease().substring(0, onReleaseSubmitedEvent.getModuleNameToRelease().lastIndexOf(':'));

//		Map<String, String> modVersions = new HashMap<>();
		Set<String> requiredModulesThatGoingToReleaseWithVersions = new HashSet<>();
		for (String goingToReleaseModule : goingToReleaseModules) {
			String moduleWithoutVersion = goingToReleaseModule.substring(0, goingToReleaseModule.lastIndexOf(':'));
			if (requiredModules.contains(moduleWithoutVersion)) {
				requiredModulesThatGoingToReleaseWithVersions.add(goingToReleaseModule);
			}
		}

		Set<String> waitingModules = new HashSet<>();
		for (String requiredModule : requiredModulesThatGoingToReleaseWithVersions) {
			if (!getCiEngineClient().isModuleReleased(url, requiredModule)) {
				waitingModules.add(requiredModule);
			}
		}
		return waitingModules;
	}

	protected String extractVersion(String moduleNameWithVersion) {
		return  moduleNameWithVersion.substring(moduleNameWithVersion.lastIndexOf(':') + 1, moduleNameWithVersion.length());
	}
}
