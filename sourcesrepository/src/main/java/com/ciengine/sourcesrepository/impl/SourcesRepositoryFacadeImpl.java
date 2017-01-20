package com.ciengine.sourcesrepository.impl;

import com.ciengine.sourcesrepository.GetDiffRequest;
import com.ciengine.sourcesrepository.GetDiffResponse;
import com.ciengine.sourcesrepository.SourcesRepositoryFacade;
import com.ciengine.sourcesrepository.Utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.ciengine.sourcesrepository.Utils.currentDir;

/**
 * Created by emekhanikov on 17.01.2017.
 */
public class SourcesRepositoryFacadeImpl implements SourcesRepositoryFacade {
    @Override
    public GetDiffResponse getDiff(GetDiffRequest getDiffRequest) {
        GetDiffResponse getDiffResponse = new GetDiffResponse();
        String url = "ssh://git@stash.hybris.com:7999/commerce/entitlements.git";
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String dirName = uri.getPath().replace(".git", "").replace("/", "_");
        String sourcesDirPath = currentDir() + "/tmp/sources/";
        String modulePath = sourcesDirPath + "/" + dirName;
        if (!new File(modulePath).exists()) {
            String branchName = "master";
            try {
                Utils.executeCommand(sourcesDirPath, "git", "clone", url, dirName);

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Utils.executeCommand(modulePath, "git", "fetch", "--prune");
            String s = Utils.executeCommand(modulePath, "git", "log", "--oneline", "origin/release/6.4.0..origin/develop");
            System.out.print(s);
            // PR develop to release
            //git log --oneline origin/release/6.4.0..origin/develop
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return getDiffResponse;
    }
}
