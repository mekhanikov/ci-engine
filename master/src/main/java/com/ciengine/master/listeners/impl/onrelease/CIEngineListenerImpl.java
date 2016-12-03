package com.ciengine.master.listeners.impl.onrelease;


import com.ciengine.MasterApplication;
import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.DefaultCIEngineEvent;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.CIEngineListenerException;
import com.ciengine.master.listeners.RuleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
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
		RuleBuilder ruleBuilder = applicationContext.getBean(RuleBuilder.class, ciEngineEvent);
		ruleBuilder.onNewArtefact().processReleaseRule();
	}

	@Override
	public boolean isEventApplicable(DefaultCIEngineEvent defaultCIEngineEvent) {
		return true;
	}
}
