package com.ciengine.sourcesrepository;

/**
 * Created by emekhanikov on 17.01.2017.
 */
public interface SourcesRepositoryFacade {
    GetDiffResponse getDiff(GetDiffRequest getDiffRequest);
}
