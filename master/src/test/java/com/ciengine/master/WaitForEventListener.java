package com.ciengine.master;

import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.DefaultCIEngineEvent;
import com.ciengine.common.EnvironmentVariables;
import com.ciengine.common.events.OnCommitEvent;
import com.ciengine.common.events.OnNewArtifactEvent;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.CIEngineListenerException;
import com.ciengine.master.listeners.impl.oncommit.OnCommitRule;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class WaitForEventListener implements CIEngineListener
{

	private List<OnCommitRule> rules;
	private Class<DefaultCIEngineEvent> onNewArtifactEventClass;
	private CIEngineEvent ciEngineEvent;
	CountDownLatch startSignal = new CountDownLatch(1);

	public WaitForEventListener(Class onNewArtifactEventClass)
	{
		this.onNewArtifactEventClass = onNewArtifactEventClass;
	}

	public CIEngineEvent waitEvent(long timeout) throws InterruptedException
	{
		startSignal.await(timeout, TimeUnit.SECONDS);
		return ciEngineEvent;
	}

	@Override public void onEvent(CIEngineEvent ciEngineEvent) throws CIEngineListenerException
	{
		this.ciEngineEvent = ciEngineEvent;
		startSignal.countDown();
	}

	@Override public boolean isEventApplicable(DefaultCIEngineEvent defaultCIEngineEvent)
	{
		return onNewArtifactEventClass.getName().equals(defaultCIEngineEvent.getClass().getName());
	}
}
