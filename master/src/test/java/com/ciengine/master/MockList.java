package com.ciengine.master;



import com.ciengine.common.CIEngineList;
import com.ciengine.common.CIEngineStepException;
import com.ciengine.common.EnvironmentVariables;
import org.springframework.stereotype.Component;


/**
 * Created by emekhanikov on 05.09.2016.
 */
@Component
public class MockList implements CIEngineList
{
	@Override public void doList(EnvironmentVariables environmentVariables) throws CIEngineStepException
	{
System.out.print("d");
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//throw new CIEngineStepException("");
	}
}
