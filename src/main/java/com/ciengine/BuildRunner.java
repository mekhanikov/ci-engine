package main.java.com.ciengine;

/**
 * Created by emekhanikov on 05.09.2016.
 */
public class BuildRunner
{
	// TODO run in separate thread bacause can be time consumption to make listeners work as fas as posible,
	// TODO because otherwise Controller will hang and then timeout.
	// load from build quiue table quiuied build with lowest start time.
	// find node good for the build.
	// If no free nodes - wait.
	// upload to node build plan.
}
