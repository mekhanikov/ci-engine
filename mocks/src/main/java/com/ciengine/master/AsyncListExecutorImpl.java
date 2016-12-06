package com.ciengine.master;

import com.ciengine.common.BuildStatus;
import com.ciengine.common.CIEngineList;
import com.ciengine.common.EnvironmentVariables;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * Created by evgenymekhanikov on 29.09.16.
 */
@Service
public class AsyncListExecutorImpl implements AsyncListExecutor {
    private static final Log logger = LogFactory.getLog(AsyncListExecutorImpl.class);

    @Async
    public Future<String> executeList(CIEngineList ciEngineList, EnvironmentVariables environmentVariables)  {
        try {
            logger.debug("!!!!! Start: " + environmentVariables);
            ciEngineList.doList(environmentVariables);
            logger.debug("!!!!! END: " + environmentVariables);
            return new AsyncResult<>(BuildStatus.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResult<>(BuildStatus.FAILED);
        }

    }
}
