package com.ciengine;

import com.ciengine.common.CIEngineEvent;
import com.ciengine.common.DefaultCIEngineEvent;
import com.ciengine.master.listeners.CIEngineListener;
import com.ciengine.master.listeners.CIEngineListenerException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class WaitForEventListener implements CIEngineListener
{

//	private List<OnCommitRule> rules;
//	private Class<DefaultCIEngineEvent> onNewArtifactEventClass;
//	private CIEngineEvent ciEngineEvent;
	CountDownLatch startSignal = new CountDownLatch(1);

	private IsMach isMach;
	private boolean mach = false;

//	public WaitForEventListener(Class onNewArtifactEventClass)
//	{
//		this.onNewArtifactEventClass = onNewArtifactEventClass;
//	}

	public WaitForEventListener(IsMach isMach) {
		this.isMach = isMach;
	}

	public IsMach waitEvent(long timeout) throws InterruptedException
	{
		startSignal.await(timeout, TimeUnit.SECONDS);
		return isMach;
	}

	@Override public void onEvent(CIEngineEvent ciEngineEvent) throws CIEngineListenerException
	{
		if (isMach.IsMach(ciEngineEvent)) {
			mach = true;
			startSignal.countDown();
		}
//		this.ciEngineEvent = ciEngineEvent;

	}

	@Override public boolean isEventApplicable(DefaultCIEngineEvent defaultCIEngineEvent)
	{
		return true;
	}

	public boolean isMach() {
		return mach;
	}
}
