package com.ciengine.master.listeners.impl;


import com.ciengine.common.CIEngineEvent;
import com.ciengine.master.listeners.CIEngineListener;
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
@Component(value = "CIEngineListener")
class CIEngineListenerImpl implements CIEngineListener
{
	private List<CIEngineListener> ciEngineListenerList = new ArrayList<>();

	@Autowired
	private Pipeline pipeline;

	@PostConstruct
	public void init() {
		List<RuleBuilder> ruleBuilderList = pipeline.prepare();
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



	@Override
	public boolean isEventApplicable(CIEngineEvent defaultCIEngineEvent) {
		return true;
	}
}
