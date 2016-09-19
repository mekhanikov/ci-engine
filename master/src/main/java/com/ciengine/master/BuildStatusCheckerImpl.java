package com.ciengine.master;

import com.ciengine.common.Node;
import com.ciengine.master.dao.BuildDao;
import com.ciengine.master.facades.NodeFacade;
import com.ciengine.master.model.BuildModel;
import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by emekhanikov on 13.09.2016.
 */
@Component
public class BuildStatusCheckerImpl
{
	private static final Logger log = LoggerFactory.getLogger(BuildStatusCheckerImpl.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


	@Autowired
	BuildDao buildDao;

	@Autowired
	NodeFacade nodeFacade;

	@Scheduled(fixedRate = 2000)
	public void reportCurrentTime() {
		List<BuildModel> buildModelList = buildDao.getNextBuildsInProgress();
		if (buildModelList != null) {
			for (BuildModel buildModel : buildModelList) {
				Node node = nodeFacade.findBestNodeById(buildModel.getNodeId());
				String s = getStatus(node, buildModel.getId());
				if (!buildModel.getStatus().equals(s)) {
					buildModel.setStatus(s);
					buildDao.update(buildModel);
				}
				// Finished with status s.
				log.info(String.valueOf(buildModel));
			}

//			buildModel.setStatus("IN PROGRESS");
//			run(buildModel);
//			buildDao.update(buildModel);
		}


	}

	private String getStatus(Node node, int id)
	{
		String result = "IN PROGRESS";
		String workspaceRemotePath = node.getRootWorkspace() + "/build_" + id;
		try
		{
			JSch jsch = new JSch();
			Session session = jsch.getSession(node.getUser(), node.getHost(), node.getPort());
			session.setPassword(node.getPassword());
			session.setConfig("StrictHostKeyChecking", "no");
			System.out.println("Establishing Connection...");
			session.connect();
			System.out.println("Connection established.");
			System.out.println("Crating SFTP Channel.");
			ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect();
			System.out.println("SFTP Channel created.");

			try
			{
				sftpChannel.rm(workspaceRemotePath);
			} catch (SftpException e) {
				// it is ok.
			}
			try
			{
				sftpChannel.mkdir(workspaceRemotePath);
			} catch (SftpException e) {
				// it is ok. but why?
			}
			sftpChannel.cd(workspaceRemotePath);

			InputStream inputStream = sftpChannel.get("status");
			StringWriter writer = new StringWriter();
			IOUtils.copy(inputStream, writer, "utf-8");
			String theString = writer.toString();
			if ("0".equals(theString.trim())) {
				result = "SUCCESS";
			} else {
				result = "FAIL";
			}
//			result = theString;
			sftpChannel.disconnect();
			session.disconnect();
		}
		catch(JSchException | IOException e)
		{
			e.printStackTrace();
		}
		catch (SftpException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public void run(BuildModel buildModel)
	{

	}
}
