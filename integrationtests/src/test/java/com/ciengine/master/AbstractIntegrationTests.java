package com.ciengine.master;

import com.ciengine.IsMach;
import com.ciengine.WaitForEventListener;
import com.ciengine.master.facades.CIEngineFacade;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Mocked master-agent communication.
 */
@Ignore("I'm just abstract without tests")
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {TestConfiguration.class}, properties = "server.port=8080")
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
