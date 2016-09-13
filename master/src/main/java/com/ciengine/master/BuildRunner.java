package com.ciengine.master;

import com.jcraft.jsch.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by emekhanikov on 05.09.2016.
 */
interface BuildRunner
{
	void run();
}
