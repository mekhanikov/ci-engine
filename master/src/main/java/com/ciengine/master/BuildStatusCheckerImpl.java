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
				if (checkIsStillRunning(node, buildModel.getId()))
				// TODO gen Node by buildModel.getNodeId(), connect to the Node and check build status.
				log.info(String.valueOf(buildModel));
			}

//			buildModel.setStatus("IN PROGRESS");
//			run(buildModel);
//			buildDao.update(buildModel);
		}


	}

	private boolean checkIsStillRunning(Node node, int id)
	{
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


//			File f5 = new File("D:/prj/ci-engine/agent/target/agent-1.0-SNAPSHOT.jar");
//			sftpChannel.put(new FileInputStream(f5), f5.getName(), ChannelSftp.OVERWRITE);
//
//			String permissionStringInDecimal = "777";
//			sftpChannel.chmod(Integer.parseInt(permissionStringInDecimal,8), f6.getName());

//			OutputStream outputStream = new FileOutputStream("D:\\prj\\ci-engine\\statuss.txt");
			InputStream inputStream = sftpChannel.get("status");
			StringWriter writer = new StringWriter();
			IOUtils.copy(inputStream, writer, "utf-8");
			String theString = writer.toString();
//			outputStream.flush();
//			outputStream.close();
			//			InputStream out= null;
			//			out= sftpChannel.get(remoteFile);
			//			BufferedReader br = new BufferedReader(new InputStreamReader(out));
			//			String line;
			//			while ((line = br.readLine()) != null)
			//			{
			//				System.out.println(line);
			//			}
			//			br.close();

//			Channel channel = session.openChannel("exec");
//			//nohup ./build.sh > logs.txt 2>&1 & echo $! > run.pid
//			// TODO kill build.sh and all gpid
//			//((ChannelExec) channel).setCommand("cd " + workspaceRemotePath + "; pkill -TERM -P 15237");// is pid of build.sh from pid file
//			sftpChannel.chmod(Integer.parseInt("600",8), workspaceRemotePath + "/id_rsa");
//			((ChannelExec) channel).setCommand("cd " + workspaceRemotePath + "; nohup ./build0.sh > build0nohuplogs.txt 2>&1 &");
//			channel.connect();
//
//			///
//			channel.setInputStream(null);
//
//			((ChannelExec) channel).setErrStream(System.err);
//
//			InputStream in = channel.getInputStream();
//
//			channel.connect();
//
//			byte[] tmp = new byte[1024];
//			while (true) {
//				while (in.available() > 0) {
//					int i = in.read(tmp, 0, 1024);
//					if (i < 0)
//						break;
//					System.out.print(new String(tmp, 0, i));
//					//					rez = new String(tmp, 0, i);
//				}
//				if (channel.isClosed()) {
//					System.out.println("exit-status: "+channel.getExitStatus());
//					break;
//				}
//				try {
//					Thread.sleep(1000);
//				} catch (Exception e) {
//					System.out.print(e);
//					//					rez = e.toString();
//				}
//			}
			///


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
		return false;
	}

	public void run(BuildModel buildModel)
	{

	}
}
