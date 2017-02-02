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
import java.util.*;
import java.util.stream.Collectors;


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
		String gitUrl = environmentVariables.getProperty(EnvironmentVariablesConstants.GIT_URL);
		String branchName = environmentVariables.getProperty(EnvironmentVariablesConstants.RELEASE_BRANCH_NAME);
		String commitId = environmentVariables.getProperty(EnvironmentVariablesConstants.COMMIT_ID);
		String buildId = environmentVariables.getProperty(EnvironmentVariablesConstants.BUILD_EXTERNAL_ID);
		String goingToRelease = environmentVariables.getProperty(EnvironmentVariablesConstants.GOING_TO_RELEASE);
		String moduleNameToRelease = environmentVariables.getProperty(EnvironmentVariablesConstants.MODULE_NAME);
		String url = environmentVariables.getProperty(EnvironmentVariablesConstants.CIENGINE_MASTER_URL);
		String dockerImageId = environmentVariables.getProperty(EnvironmentVariablesConstants.DOCKER_IMAGE_ID);

		String path = Utils.clone(gitUrl, branchName);
		List<String> dependencies = retrieveDependencies(path + "/pom.xml");
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

	public static void updateDependencies(String s, Map<String, String> dependencyVersionMap) {
		Document doc = readXML(s);
		List<String> dependencies = new ArrayList<>();
		NodeList deps = doc.getElementsByTagName("dependency");
		for (int i = 0; i < deps.getLength(); i++) {
			Node dep = deps.item(i);
			if (dep.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) dep;
				String artifactId = eElement
						.getElementsByTagName("artifactId")
						.item(0)
						.getTextContent();

				String groupId = eElement
						.getElementsByTagName("groupId")
						.item(0)
						.getTextContent();

				String dependency = groupId + ":" + artifactId;
				String newVersion = dependencyVersionMap.get(dependency);
				if (newVersion != null) {
					String version = eElement
							.getElementsByTagName("version")
							.item(0)
							.getTextContent();

					if (version.startsWith("$")) {
						String propertyName = version.replace("${", "").replace("}", "");
						doc.getElementsByTagName(propertyName).item(0).setTextContent(newVersion);
					}
				}



//				dependencies.add();

			}
		}


		try {
			Transformer transformer = null;
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
//			StreamResult console = new StreamResult(System.out);
			//			transformer.transform(source, console);
			StreamResult result = new StreamResult(new File(s));
			transformer.transform(source, result);



			// write the content into xml file


//			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}


	}

	protected List<String> retrieveDependencies(String s) {
		Document doc = readXML(s);
		List<String> dependencies = new ArrayList<>();
		NodeList deps = doc.getElementsByTagName("dependency");
		for (int i = 0; i < deps.getLength(); i++) {
			Node dep = deps.item(i);
			if (dep.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) dep;
				String artifactId = eElement
						.getElementsByTagName("artifactId")
						.item(0)
						.getTextContent();

				String groupId = eElement
						.getElementsByTagName("groupId")
						.item(0)
						.getTextContent();
				dependencies.add(groupId + ":" + artifactId);

			}
		}
		return dependencies;
	}

	public static Document readXML(String xml) {
		String role1 = null;
		String role2 = null;
		String role3 = null;
		String role4 = null;
		ArrayList<String> rolev;
		rolev = new ArrayList<>();
		Document dom;
		// Make an  instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// use the factory to take an instance of the document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			// parse using the builder to get the DOM mapping of the
			// XML file
			dom = db.parse(xml);
			return dom;


		} catch (ParserConfigurationException pce) {
			System.out.println(pce.getMessage());
		} catch (SAXException se) {
			System.out.println(se.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		return null;
	}

	public static void main(String[] ss) {
		Map<String, String> map = new HashMap<>();
		map.put("de.hybris.platform:atdd-module", "new ver");
		ReleaseList.updateDependencies("D:\\prj\\ci-engine\\tmp2\\sources\\_commerce_subscriptions\\pom.xml", map);
	}
}
