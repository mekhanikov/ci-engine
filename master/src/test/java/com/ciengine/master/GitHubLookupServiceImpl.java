package com.ciengine.master;

import com.ciengine.common.BuildStatus;
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
    public Future<String> executeList(CIEngineList ciEngineList)  {
        try {
            ciEngineList.doList(null);
            return new AsyncResult<>(BuildStatus.SUCCESS);
        } catch (CIEngineStepException e) {
            e.printStackTrace();
            return new AsyncResult<>(BuildStatus.FAILED);
        }

    }
}