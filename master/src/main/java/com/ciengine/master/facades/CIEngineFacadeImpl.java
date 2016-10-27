package com.ciengine.master.facades;

import com.ciengine.common.*;
import com.ciengine.common.dto.*;
import com.ciengine.common.events.OnReleaseSubmitedEvent;
import com.ciengine.master.controllers.getbuilds.GetBuildsResponse;
import com.ciengine.master.dao.BuildDao;
import com.ciengine.master.dao.ReleaseDao;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.CIEngineListenerException;
import com.ciengine.master.listeners.impl.onrelease.OnReleaseRule;
import com.ciengine.master.model.BuildModel;
import com.ciengine.master.model.ReleaseModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

	private List<CIEngineListener> ciEngineListeners = new ArrayList<>();

	private List<Module> modules = new ArrayList<>();

	public GetBuildsResponse getBuildsResponse() {
		GetBuildsResponse getBuildsResponse = new GetBuildsResponse();
		//		buildDao.getAll();
//		BuildModel buildModel = new BuildModel();
//		buildModel.setStartTimestamp(new Date());
		buildDao.getAll();
		return getBuildsResponse;
	}

	@Override public GetBuildsResponse addBuild(AddBuildRequest addBuildRequest)
	{
		GetBuildsResponse getBuildsResponse = new GetBuildsResponse();
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
		return getBuildsResponse;
	}

	@Override public void onEvent(DefaultCIEngineEvent defaultCIEngineEvent)
	{
		logger.debug("defaultCIEngineEvent: " + defaultCIEngineEvent);
		Map<String, CIEngineListener> stringCIEngineListenerMap = applicationContext.getBeansOfType(CIEngineListener.class);
		if (stringCIEngineListenerMap != null) {
			Collection<CIEngineListener> ciEngineListenerCollection = stringCIEngineListenerMap.values();
			processEventForListeners(defaultCIEngineEvent, ciEngineListenerCollection);
		}
		processEventForListeners(defaultCIEngineEvent, ciEngineListeners);
	}

	private void processEventForListeners(DefaultCIEngineEvent defaultCIEngineEvent, Collection<CIEngineListener> ciEngineListenerCollection)
	{
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

	public List<Module> getModules()
	{
		return modules;
	}

	public void setModules(List<Module> modules)
	{
		this.modules = modules;
	}

	@Override
	public void submitRelease(Release release) {
		ReleaseModel releaseModel = new ReleaseModel();
		releaseModel.setModuleNameToRelease(release.getModuleNameToRelease());
		releaseModel.setGoingToRelease(release.getGoingToRelease());
		releaseModel.setApplyList(release.getApplyList());
		releaseModel.setReleaseBranchName(release.getReleaseBranchName());
		releaseModel.setMergeFromCommitId(release.getMergeFromCommitId());
		releaseModel.setInputParams(release.getInputParams());
		releaseModel.setDockerImageId(release.getDockerImageId());
		releaseDao.save(releaseModel);
		OnReleaseSubmitedEvent onReleaseSubmitedEvent = new OnReleaseSubmitedEvent();
       		// TODO
		onEvent(onReleaseSubmitedEvent);
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
	public AddBuildResponse findBuild(AddBuildRequest addBuildRequest) {
		AddBuildResponse addBuildResponse = new AddBuildResponse();
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
			//	build.setEndTimestamp(buildModel.getEndTimestamp());
			// TODO map other
			builds.add(build);
		}
		addBuildResponse.setBuildList(builds);
		return addBuildResponse;
	}

	@Override
	public void setBuildStatus(SetBuildStatusRequest setBuildStatusRequest) {
// TODO
		BuildModel buildModel = buildDao.findByExternalId(setBuildStatusRequest.getExternalBuildId());
		buildModel.setStatus(setBuildStatusRequest.getStatus());
	}

    @Override
    public IsModuleReleasedResponse isModuleReleased(IsModuleReleasedRequest isModuleReleasedRequest) {
        IsModuleReleasedResponse isModuleReleasedResponse = new IsModuleReleasedResponse();
        isModuleReleasedResponse.setReleased( binaryRepositoryClient.isModuleReleased(isModuleReleasedRequest.getModule()));
        return isModuleReleasedResponse;
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
}
