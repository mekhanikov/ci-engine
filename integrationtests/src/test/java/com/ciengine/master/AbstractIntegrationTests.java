package com.ciengine.master;

import com.ciengine.IsMach;
import com.ciengine.TestConfiguration;
import com.ciengine.WaitForEventListener;
import com.ciengine.common.Module;
import com.ciengine.common.Repo;
import com.ciengine.common.dto.IsModuleReleasedRequest;
import com.ciengine.common.dto.IsModuleReleasedResponse;
import com.ciengine.master.dao.BuildDao;
import com.ciengine.master.facades.CIEngineFacade;
import com.ciengine.master.listeners.impl.onrelease.OnReleaseRule;
import com.ciengine.master.model.BuildModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


/**
 * Mocked master-agent communication.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {TestConfiguration.class}, properties = "server.port=8080")
public class AbstractIntegrationTests {

	@Autowired
	private CIEngineFacade ciEngineFacade;

	protected WaitForEventListener waitForCondition(IsMach isMach) {
		WaitForEventListener waitForEventListener = new WaitForEventListener(isMach);
		ciEngineFacade.addListener(waitForEventListener);
		return waitForEventListener;
	}

	public CIEngineFacade getCiEngineFacade() {
		return ciEngineFacade;
	}

	public void setCiEngineFacade(CIEngineFacade ciEngineFacade) {
		this.ciEngineFacade = ciEngineFacade;
	}
}
