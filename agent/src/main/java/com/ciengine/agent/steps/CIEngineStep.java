package com.ciengine.agent.steps;



import com.ciengine.common.EnvironmentVariables;
import org.springframework.stereotype.Component;


/**
 * Created by emekhanikov on 05.09.2016.
 */
@Component
public interface CIEngineStep
{
	void doStep(EnvironmentVariables environmentVariables) throws CIEngineStepException;
}
