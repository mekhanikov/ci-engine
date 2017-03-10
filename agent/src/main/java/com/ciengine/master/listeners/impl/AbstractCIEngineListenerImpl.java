package com.ciengine.master.listeners.impl;


import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.DefaultCIEngineEvent;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.CIEngineListenerBuilder;
import com.ciengine.master.listeners.CIEngineListenerException;
import com.ciengine.master.listeners.RuleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by emekhanikov on 05.09.2016.
 */

public abstract class AbstractCIEngineListenerImpl implements CIEngineListener
{
	private List<RuleBuilder> ruleBuilderList = new ArrayList<>();
	private List<CIEngineListener> ciEngineListenerList = new ArrayList<>();

	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	public void init() {
		createRules();
		ruleBuilderList.forEach(t->{ciEngineListenerList.add(t.getCIEngineListenerBuilder().createCIEngineListener());});
	}

	@Override
	public void onEvent(CIEngineEvent ciEngineEvent) throws CIEngineListenerException {
		ciEngineListenerList.forEach(t->{
			try {
				t.onEvent(ciEngineEvent);
			} catch (CIEngineListenerException e) {
				e.printStackTrace();
			}
		});
	}

	protected abstract void createRules();
//	{
//		//createRuleBuilder(ciEngineEvent).onCommit().forModules("modA").forBranches("develop, feature/.*").applyList("onCommitList").triggerBuild();
//		//createRuleBuilder().onCommit().forModules("modA").forBranches("develop").triggerBuildsFor("modA", "feature/.*");
//		//		createRuleBuilder().onCommit().forModules("modA").
////				forBranches("feature/.*").enableAutomergeFrom("develop").enableCrossBuild().triggerBuild();
//
//		createRuleBuilder().onNewArtefact().processReleaseRule();
//		createRuleBuilder().onReleaseSubmited().triggerRelease();
//		createRuleBuilder().onCommit().forModules("modA").forBranches("develop").triggerBuild();
//	}

	protected RuleBuilder createRuleBuilder() {
		RuleBuilder ruleBuilder = applicationContext.getBean(RuleBuilder.class);
		ruleBuilderList.add(ruleBuilder);
		return ruleBuilder;
	}

	@Override
	public boolean isEventApplicable(CIEngineEvent defaultCIEngineEvent) {
		return true;
	}
}
