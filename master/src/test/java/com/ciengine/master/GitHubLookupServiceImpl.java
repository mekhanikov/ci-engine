package com.ciengine.master;

import com.ciengine.common.CIEngineList;
import com.ciengine.common.CIEngineStepException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * Created by evgenymekhanikov on 29.09.16.
 */
@Service
public class GitHubLookupServiceImpl implements GitHubLookupService {

    @Async
    public Future<String> executeList(CIEngineList ciEngineList) throws InterruptedException, CIEngineStepException {
        ciEngineList.doList(null);
        return new AsyncResult<>("");
    }
}
