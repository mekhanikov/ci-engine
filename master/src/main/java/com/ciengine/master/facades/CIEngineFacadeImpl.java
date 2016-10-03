package com.ciengine.master.facades;

import com.ciengine.common.*;
import com.ciengine.common.events.OnReleaseSubmitedEvent;
import com.ciengine.master.controllers.addbuild.AddBuildRequest;
import com.ciengine.master.controllers.getbuilds.GetBuildsResponse;
import com.ciengine.master.dao.BuildDao;
import com.ciengine.master.dao.ReleaseDao;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.CIEngineListenerException;
import com.ciengine.master.listeners.impl.onrelease.OnReleaseRule;
import com.ciengine.master.model.BuildModel;
import com.ciengine.master.model.ReleaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * Created by emekhanikov on 14.09.2016.
 */
@Transactional
@Component
public class CIEngineFacadeImpl implements CIEngineFacade
{
	@Autowired
	private BuildDao buildDao;

	@Autowired
	private ReleaseDao releaseDao;

	@Autowired
	private ApplicationContext applicationContext;

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
		buildDao.save(buildModel);
		return getBuildsResponse;
	}

	@Override public void onEvent(DefaultCIEngineEvent defaultCIEngineEvent)
	{
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
//			onReleaseRule.setEnvironmentVariables(releaseModel.getInputParams()); // TODo parse
//			onReleaseRule.setDockerImageId(releaseModel.getDockerImageId());// TODO remove?
			onReleaseRule.setApplyList(releaseModel.getApplyList());
			onReleaseRule.setGoingToRelease(releaseModel.getGoingToRelease());
			onReleaseRule.setMergeFromCommitId(releaseModel.getMergeFromCommitId());
			onReleaseRule.setReleaseBranchName(releaseModel.getReleaseBranchName());
			result.add(onReleaseRule);
		}
		return result;
	}

	//	public static void main(String[] strings) {
	//
	//	}
}
