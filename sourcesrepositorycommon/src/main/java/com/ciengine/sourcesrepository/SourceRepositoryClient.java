package com.ciengine.sourcesrepository;

/**
 * Created by emekhanikov on 06.09.2016.
 */
public interface SourceRepositoryClient
{
    GetDiffResponse getDiff(String serverUrl, GetDiffRequest getDiffRequest);
}
