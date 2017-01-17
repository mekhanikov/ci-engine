package com.ciengine.sourcesrepository.impl;

import com.ciengine.sourcesrepository.GetDiffRequest;
import com.ciengine.sourcesrepository.GetDiffResponse;
import com.ciengine.sourcesrepository.SourcesRepositoryFacade;

/**
 * Created by emekhanikov on 17.01.2017.
 */
public class SourcesRepositoryFacadeImpl implements SourcesRepositoryFacade {
    @Override
    public GetDiffResponse getDiff(GetDiffRequest getDiffRequest) {
        GetDiffResponse getDiffResponse = new GetDiffResponse();
        return getDiffResponse;
    }
}
