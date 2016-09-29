package com.ciengine.master;

import com.ciengine.common.CIEngineList;
import com.ciengine.common.CIEngineStepException;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.concurrent.Future;

/**
 * Created by evgenymekhanikov on 29.09.16.
 */
public interface GitHubLookupService {
    public Future<String> executeList(CIEngineList ciEngineList) throws InterruptedException, CIEngineStepException;
}
