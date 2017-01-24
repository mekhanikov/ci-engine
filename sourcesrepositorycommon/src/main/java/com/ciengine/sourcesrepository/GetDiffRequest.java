package com.ciengine.sourcesrepository;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Created by emekhanikov on 14.09.2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GetDiffRequest
{
	private String repositoryUrl;
	private String sourceBranchName;
	private String destinationBranchName;

	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}

	public String getSourceBranchName() {
		return sourceBranchName;
	}

	public void setSourceBranchName(String sourceBranchName) {
		this.sourceBranchName = sourceBranchName;
	}

	public String getDestinationBranchName() {
		return destinationBranchName;
	}

	public void setDestinationBranchName(String destinationBranchName) {
		this.destinationBranchName = destinationBranchName;
	}
}
