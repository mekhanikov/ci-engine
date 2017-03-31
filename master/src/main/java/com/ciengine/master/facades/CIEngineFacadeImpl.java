package com.ciengine.master.facades;

import com.ciengine.common.*;
import com.ciengine.common.dto.*;
import com.ciengine.common.events.OnBuildStatusChangedEvent;
import com.ciengine.common.events.OnReleaseSubmitedEvent;
import com.ciengine.master.GetModulesResponse;
import com.ciengine.master.dao.BuildDao;
import com.ciengine.master.dao.ReleaseDao;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.CIEngineListenerException;
import com.ciengine.master.listeners.OnReleaseRule;
import com.ciengine.master.model.BuildModel;
import com.ciengine.master.model.ReleaseModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.*;

/**
 * Created by emekhanikov on 14.09.2016.
 */
@Transactional
@Component
public class CIEngineFacadeImpl implements CIEngineFacade
{
//	private static final Logger LOG = Logger.getLogger(CIEngineFacadeImpl.class.getName());
	private static final Log logger = LogFactory.getLog(CIEngineFacadeImpl.class);

	@Autowired
	private BuildDao buildDao;

	@Autowired
	private ReleaseDao releaseDao;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private BinaryRepositoryClient binaryRepositoryClient;

	@Autowired
	private EnvironmentFacade environmentFacade;

	@Autowired
	private ModuleFacade moduleFacade;

	private List<CIEngineListener> ciEngineListeners = new ArrayList<>();

//	private List<Module> modules = new ArrayList<>();

	public FindBuildsResponse findBuilds() {
		FindBuildsResponse findBuildsResponse = new FindBuildsResponse();
		//		buildDao.getAll();
//		BuildModel buildModel = new BuildModel();
//		buildModel.setStartTimestamp(new Date());
		List<Build> builds = new ArrayList<>();
		List<BuildModel> buildModelList = buildDao.getAll();
		for (BuildModel buildModel : buildModelList) {
			Build build = new Build();
			build.setStatus(buildModel.getStatus());
			build.setModuleName(buildModel.getModuleName());
			build.setBranchName(buildModel.getBranchName());
			build.setInputParams(buildModel.getInputParams());
			build.setStatusDescription(buildModel.getStatusDescription());
			builds.add(build);
		}
		findBuildsResponse.setBuildList(builds);
		return findBuildsResponse;
	}

	@Override public AddBuildResponse addBuild(AddBuildRequest addBuildRequest)
	{
		AddBuildResponse addBuildResponse = new AddBuildResponse();
		BuildModel buildModel = new BuildModel();
		buildModel.setDockerImageId(addBuildRequest.getDockerImageId());
		buildModel.setBranchName(addBuildRequest.getBranchName());
		buildModel.setExecutionList(addBuildRequest.getExecutionList());
		buildModel.setInputParams(addBuildRequest.getInputParams());
		buildModel.setModuleName(addBuildRequest.getModuleName());
		buildModel.setNodeId(addBuildRequest.getNodeId());
		buildModel.setReasonOfTrigger(addBuildRequest.getReasonOfTrigger());
		buildModel.setStatus(BuildStatus.QUEUED);
		buildModel.setStartTimestamp(new Date());
		buildModel.setExternalId(addBuildRequest.getExternalId());
		buildDao.save(buildModel);
		addBuildResponse.setBuildId(buildModel.getId());
		return addBuildResponse;
	}

	@Override public void onEvent(DefaultCIEngineEvent defaultCIEngineEvent)
	{// TODO only add not trigger listeners, trigger them in separate thread.
		logger.debug("defaultCIEngineEvent: " + defaultCIEngineEvent);
		Map<String, CIEngineListener> stringCIEngineListenerMap = applicationContext.getBeansOfType(CIEngineListener.class);
		if (stringCIEngineListenerMap != null) {
			Collection<CIEngineListener> ciEngineListenerCollection = stringCIEngineListenerMap.values();
			processEventForListeners(defaultCIEngineEvent, ciEngineListenerCollection);
		}
		processEventForListeners(defaultCIEngineEvent, ciEngineListeners);
	}

	private void processEventForListeners(DefaultCIEngineEvent defaultCIEngineEvent, Collection<CIEngineListener> ciEngineListenerCollection)
	{// TODO had java.util.ConcurrentModificationException for ciEngineListenerCollection! Happened twice!
		for (CIEngineListener ciEngineListener : ciEngineListenerCollection) {
			if (ciEngineListener.isEventApplicable(defaultCIEngineEvent)) {
				try
				{
					ciEngineListener.onEvent(defaultCIEngineEvent);
				}
				catch (CIEngineListenerException e)
				{
					// TODO
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void submitEvent(CIEngineEvent ciEngineEvent) throws CIEngineException
	{// TODO in CIEngineFacadeImpl.onEvent()

	}

	@Override public Module findModuleByGitUrl(String gitUrl)
	{
		FindModulesRequest findModulesRequest = new FindModulesRequest();
		// TODO
		List<Module> modules = moduleFacade.findModules(findModulesRequest);
		for (Module module: modules) {
			for (Repo repo : module.getRepoList()) {
				if (gitUrl.equals(repo.getGitUrl())) {
					return module;
				}
			}
		}
		return null;
	}

	@Override public void addListener(CIEngineListener ciEngineListener)
	{
		ciEngineListeners.add(ciEngineListener);
	}


	@Override
	public void submitRelease(OnReleaseRule release) {
		ReleaseModel releaseModel = new ReleaseModel();
		releaseModel.setModuleNameToRelease(release.getModuleNameToRelease());
		releaseModel.setGoingToRelease(release.getGoingToRelease());
		releaseModel.setApplyList(release.getApplyList());
		releaseModel.setReleaseBranchName(release.getReleaseBranchName());
		releaseModel.setMergeFromCommitId(release.getMergeFromCommitId());
		releaseModel.setInputParams(makeString(release.getEnvironmentVariables()));
		releaseModel.setDockerImageId(release.getDockerImageId());
		releaseDao.save(releaseModel);
		OnReleaseSubmitedEvent onReleaseSubmitedEvent = new OnReleaseSubmitedEvent();
       		// TODO
		onReleaseSubmitedEvent.setModuleNameToRelease(release.getModuleNameToRelease());
        onReleaseSubmitedEvent.setModuleNameToRelease(release.getModuleNameToRelease());
        onReleaseSubmitedEvent.setGoingToRelease(release.getGoingToRelease());
        onReleaseSubmitedEvent.setApplyList(release.getApplyList());
        onReleaseSubmitedEvent.setReleaseBranchName(release.getReleaseBranchName());
        onReleaseSubmitedEvent.setMergeFromCommitId(release.getMergeFromCommitId());
        onReleaseSubmitedEvent.setInputParams(makeString(release.getEnvironmentVariables()));
        onReleaseSubmitedEvent.setDockerImageId(release.getDockerImageId());
		onEvent(onReleaseSubmitedEvent);
	}

	@Override
	public SubmitReleasesResponse submitReleases(SubmitReleasesRequest submitReleasesRequest) {
		SubmitReleasesResponse submitReleasesResponse = new SubmitReleasesResponse();
		List<String> modulesToRelease = new ArrayList<>();
		for (Release release : submitReleasesRequest.getReleaseList()) {
			modulesToRelease.add(release.getName() + ":" + release.getVersion());
		}
		String goingToRelease = String.join(",", modulesToRelease);
		for (Release release : submitReleasesRequest.getReleaseList()) {
			EnvironmentData environmentData = environmentFacade.findApplyList(release.getName(), release.getBrancheTo());
			if (environmentData != null) {
				ReleaseModel releaseModel = new ReleaseModel();
				releaseModel.setModuleNameToRelease(release.getName() + ":" + release.getVersion());
				releaseModel.setGoingToRelease(goingToRelease);
				releaseModel.setApplyList(environmentData.getApplyList());
				releaseModel.setReleaseBranchName(release.getBrancheTo());
				releaseModel.setMergeFromCommitId(release.getBrancheFrom());
//			releaseModel.setInputParams(makeString(release.getEnvironmentVariables()));
				releaseModel.setDockerImageId(environmentData.getDockerImageId());
				releaseDao.save(releaseModel);
				OnReleaseSubmitedEvent onReleaseSubmitedEvent = new OnReleaseSubmitedEvent();
				// TODO 1. send events after data commited to DB
				// TODO 2. Dont wait until listeners finish.
				onReleaseSubmitedEvent.setModuleNameToRelease(releaseModel.getModuleNameToRelease());
				onReleaseSubmitedEvent.setGoingToRelease(goingToRelease);
			onReleaseSubmitedEvent.setApplyList(environmentData.getApplyList());
				onReleaseSubmitedEvent.setReleaseBranchName(release.getBrancheTo());
				onReleaseSubmitedEvent.setMergeFromCommitId(release.getBrancheFrom());
//			onReleaseSubmitedEvent.setInputParams(makeString(release.getEnvironmentVariables()));
			onReleaseSubmitedEvent.setDockerImageId(environmentData.getDockerImageId());
				onEvent(onReleaseSubmitedEvent);
			}

		}
		return submitReleasesResponse;
	}

	@Override
	public FindModulesResponse findModules(FindModulesRequest findModulesRequest) {
		FindModulesResponse findModulesResponse = new FindModulesResponse();
		List<Module> modules = moduleFacade.findModules(findModulesRequest);
		findModulesResponse.setModules(modules);
		return findModulesResponse;
	}

	@Override
	public String findGitUrlByModuleName(String moduleNameWithoutVersion) {
		FindModulesRequest findModulesRequest = new FindModulesRequest();
		List<String> moduleNames = new ArrayList<>();
		moduleNames.add(moduleNameWithoutVersion);
		findModulesRequest.setModuleNames(moduleNames);
		FindModulesResponse findModulesResponse = findModules(findModulesRequest);
		return findModulesResponse.getModules().get(0).getRepoList().get(0).getGitUrl();
	}

	@Override
	public List<OnReleaseRule> findAllReleases() {
		List<OnReleaseRule> result = new ArrayList<>();
		List<ReleaseModel> releaseModels = releaseDao.getAll();
		for (ReleaseModel releaseModel : releaseModels) {
			OnReleaseRule onReleaseRule = new OnReleaseRule();
			onReleaseRule.setModuleNameToRelease(releaseModel.getModuleNameToRelease());
			onReleaseRule.setEnvironmentVariables(getEnvironmentVariables(releaseModel.getInputParams()));
			onReleaseRule.setDockerImageId(releaseModel.getDockerImageId());
			onReleaseRule.setApplyList(releaseModel.getApplyList());
			onReleaseRule.setGoingToRelease(releaseModel.getGoingToRelease());
			onReleaseRule.setMergeFromCommitId(releaseModel.getMergeFromCommitId());
			onReleaseRule.setReleaseBranchName(releaseModel.getReleaseBranchName());
			result.add(onReleaseRule);
		}
		return result;
	}

	@Override
	public FindBuildsResponse findBuild(FindBuildsRequest addBuildRequest) {
		FindBuildsResponse findBuildsResponse = new FindBuildsResponse();
		List<BuildModel> buildModelList = buildDao.find(
				addBuildRequest.getModuleName(),
				addBuildRequest.getBranchName(),
				addBuildRequest.getExecutionList(),
				addBuildRequest.getDockerImageId(),
				addBuildRequest.getInputParams()
				);

		List<Build> builds = new ArrayList<>();
		for(BuildModel buildModel : buildModelList) {
			Build build = new Build();
			build.setDockerImageId(buildModel.getDockerImageId());
			build.setBranchName(buildModel.getBranchName());
			build.setExecutionList(buildModel.getExecutionList());
			build.setModuleName(buildModel.getModuleName());
			build.setInputParams(buildModel.getInputParams());
			build.setStatus(buildModel.getStatus());
			build.setExternalId(buildModel.getExternalId());
			build.setId(buildModel.getId());
			build.setInputParamsHash(buildModel.getInputParamsHash());
			build.setReasonOfTrigger(buildModel.getReasonOfTrigger());
			build.setStatusDescription(buildModel.getStatusDescription());
			//	build.setEndTimestamp(buildModel.getEndTimestamp());
			// TODO map other
			builds.add(build);
		}
		findBuildsResponse.setBuildList(builds);
		return findBuildsResponse;
	}

	@Override
	public void setBuildStatus(SetBuildStatusRequest setBuildStatusRequest) {
// TODO
		BuildModel buildModel = buildDao.findByExternalId(setBuildStatusRequest.getExternalBuildId());
		if (!buildModel.getStatus().equals(setBuildStatusRequest.getStatus())) {
			buildModel.setStatus(setBuildStatusRequest.getStatus());
			buildModel.setStatusDescription(setBuildStatusRequest.getStatusDescription());
			buildDao.update(buildModel);
			//buildDao.update(buildModel);
			//					OnNewArtifactEvent onNewArtifactEvent = new OnNewArtifactEvent();
			//					ciEngineFacade.onEvent(onNewArtifactEvent);
			OnBuildStatusChangedEvent onBuildStatusChangedEvent = new OnBuildStatusChangedEvent();
			onBuildStatusChangedEvent.setBuildId(buildModel.getExternalId());
			onBuildStatusChangedEvent.setNewStatus(buildModel.getStatus());
			onEvent(onBuildStatusChangedEvent);
			logger.info(String.valueOf(buildModel));
		}
//		buildModel.setStatus(setBuildStatusRequest.getStatus());
//		buildModel.setStatusDescription(setBuildStatusRequest.getStatusDescription());
	}

    @Override
    public IsModuleReleasedResponse isModuleReleased(IsModuleReleasedRequest isModuleReleasedRequest) {
        IsModuleReleasedResponse isModuleReleasedResponse = new IsModuleReleasedResponse();
        isModuleReleasedResponse.setReleased( binaryRepositoryClient.isModuleReleased(isModuleReleasedRequest.getModule()));
        return isModuleReleasedResponse;
    }

	@Override
	public GetModulesResponse getModulesResponse() {
		GetModulesResponse getModulesResponse = new GetModulesResponse();
		try {
			JAXBContext jaxbContext = null;
			jaxbContext = JAXBContext.newInstance(Modules.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Modules customer = (Modules) jaxbUnmarshaller.unmarshal(new File("modules.xml"));
			getModulesResponse.setModules(customer.getModules());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return getModulesResponse;
	}

	protected EnvironmentVariables getEnvironmentVariables(String inputParams) {
		EnvironmentVariables environmentVariables = new EnvironmentVariables();
		if (!StringUtils.isEmpty(inputParams)) {
			String[] lines = inputParams.split("\n");
			for (String line : lines) {
				if (!StringUtils.isEmpty(line)) {
					String[] keyValue = line.split("=");
					String key = keyValue.length > 0 ? keyValue[0] : "";
					String value = keyValue.length > 1 ? keyValue[1] : "";
					environmentVariables.addProperty(key, value);
				}
			}
		}
		return environmentVariables;
	}
	//	public static void main(String[] strings) {
	//
	//	}
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
}
