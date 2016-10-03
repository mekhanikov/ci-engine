package com.ciengine.master;

import com.ciengine.common.CIEngineList;
import com.ciengine.common.CIEngineStepException;

import java.util.concurrent.Future;

/**
 * Created by evgenymekhanikov on 29.09.16.
 */
public interface GitHubLookupService {
    public Future<String> executeList(CIEngineList ciEngineList) throws InterruptedException, CIEngineStepException;
}