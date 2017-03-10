package com.ciengine.master.listeners.impl;


import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.DefaultCIEngineEvent;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.CIEngineListenerException;
import com.ciengine.master.listeners.RuleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


/**
 * Created by emekhanikov on 05.09.2016.
 */
@Component(value = "CIEngineListener")

public class CIEngineListenerImpl implements CIEngineListener
{

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void onEvent(CIEngineEvent ciEngineEvent) throws CIEngineListenerException {
		createRuleBuilder().onNewArtefact().processReleaseRule().createCIEngineListener().onEvent(ciEngineEvent);
		createRuleBuilder().onReleaseSubmited().triggerRelease().createCIEngineListener().onEvent(ciEngineEvent);
		//createRuleBuilder(ciEngineEvent).onCommit().forModules("modA").forBranches("develop, feature/.*").applyList("onCommitList").triggerBuild();
		createRuleBuilder().onCommit().forModules("modA").forBranches("develop").triggerBuild().createCIEngineListener().onEvent(ciEngineEvent);
		createRuleBuilder().onCommit().forModules("modA").
				forBranches("feature/.*").enableAutomergeFrom("develop").enableCrossBuild().triggerBuild();
		createRuleBuilder().onCommit().forModules("modA").forBranches("develop").triggerBuildsFor("modA", "feature/.*");
	}

	private RuleBuilder createRuleBuilder() {
		return applicationContext.getBean(RuleBuilder.class);
	}

	@Override
	public boolean isEventApplicable(CIEngineEvent defaultCIEngineEvent) {
		return true;
	}
}
