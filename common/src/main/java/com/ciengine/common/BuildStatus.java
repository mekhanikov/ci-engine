package com.ciengine.common;

/**
 * Created by emekhanikov on 29.09.2016.
 */
public class BuildStatus
{
	/**
	 * Has when build was just added
	 */
	public static String QUEUED = "QUEUED";

	/**
	 * BuildRunner set the status when run it on Node
	 */
	public static String IN_PROGRESS = "IN PROGRESS";


	/**
	 *
	 */
	public static String SUCCESS = "SUCCESS";

	/**
	 *
	 */
	public static String FAILED = "FAILED";

	// TODO If build decided no need build (duplicate run) DISCARTED? SKIPED? with skip comment.

}
