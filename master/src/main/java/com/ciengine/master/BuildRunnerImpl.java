package com.ciengine.master;

import com.ciengine.common.Node;
import com.ciengine.master.dao.BuildDao;
import com.ciengine.master.facades.NodeFacade;
import com.ciengine.master.model.BuildModel;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;


/**
 * Created by emekhanikov on 13.09.2016.
 */
@Component
public class BuildRunnerImpl// implements BuildRunner
{
	private static final Logger log = LoggerFactory.getLogger(BuildRunnerImpl.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


	@Autowired
	BuildDao buildDao;

	@Autowired
	NodeFacade nodeFacade;

	@Scheduled(fixedRate = 2000)
	public void reportCurrentTime() {
		BuildModel buildModel = buildDao.getNextToBuild();
		if (buildModel != null) {
			buildModel.setStatus("IN PROGRESS");
			run(buildModel);
			buildDao.update(buildModel);
		}

		log.info(String.valueOf(buildModel));
	}

	public void run(BuildModel buildModel)
	{
		Node node = nodeFacade.findBestNode();

		String workspaceRemotePath = node.getRootWorkspace() + "/build_" + buildModel.getId();
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
			String s = getDockerImagesRoot() + buildModel.getDockerImageId();
			syncFolder(sftpChannel, s);
			//sftpChannel.cd("/home/ev/.ssh");
			prepareBuildFolderDockerImageSpecific(sftpChannel);

			File f5 = new File("D:/prj/ci-engine/agent/target/agent-1.0-SNAPSHOT.jar");
			sftpChannel.put(new FileInputStream(f5), f5.getName(), ChannelSftp.OVERWRITE);

			File f6 = new File(getDockerImagesRoot() + "/build0.sh");
			sftpChannel.put(new FileInputStream(f6), f6.getName(), ChannelSftp.OVERWRITE);
			String permissionStringInDecimal = "777";
			sftpChannel.chmod(Integer.parseInt(permissionStringInDecimal,8), f6.getName());

			InputStream stream = new ByteArrayInputStream(buildModel.getInputParams().getBytes(StandardCharsets.UTF_8));
			sftpChannel.put(stream, "environment_variables.properties", ChannelSftp.OVERWRITE);

			//			InputStream out= null;
			//			out= sftpChannel.get(remoteFile);
			//			BufferedReader br = new BufferedReader(new InputStreamReader(out));
			//			String line;
			//			while ((line = br.readLine()) != null)
			//			{
			//				System.out.println(line);
			//			}
			//			br.close();

			Channel channel = session.openChannel("exec");
//nohup ./build.sh > logs.txt 2>&1 & echo $! > run.pid
			// TODO kill build.sh and all gpid
			//((ChannelExec) channel).setCommand("cd " + workspaceRemotePath + "; pkill -TERM -P 15237");// is pid of build.sh from pid file
			sftpChannel.chmod(Integer.parseInt("600",8), workspaceRemotePath + "/id_rsa");
			((ChannelExec) channel).setCommand("cd " + workspaceRemotePath + "; nohup ./build0.sh > build0nohuplogs.txt 2>&1 &");
			channel.connect();

			///
			channel.setInputStream(null);

			((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();

			channel.connect();

			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
					//					rez = new String(tmp, 0, i);
				}
				if (channel.isClosed()) {
					System.out.println("exit-status: "+channel.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					System.out.print(e);
					//					rez = e.toString();
				}
			}
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
	}

	private void prepareBuildFolderDockerImageSpecific(ChannelSftp sftpChannel) throws SftpException, FileNotFoundException
	{// TODO move to some place image specific
		File f3 = new File("C:/cygwin/home/emekhanikov/.ssh/id_rsa");
		sftpChannel.put(new FileInputStream(f3), f3.getName(), ChannelSftp.OVERWRITE);

		File f4 = new File("C:/Users/emekhanikov/.m2/settings.xml");
		sftpChannel.put(new FileInputStream(f4), f4.getName(), ChannelSftp.OVERWRITE);
	}

	private String getDockerImagesRoot()
	{
		return "D:/prj/ci-engine/master/docker/";
	}

	private void syncFolder(ChannelSftp sftpChannel, String folder) throws SftpException, FileNotFoundException
	{// TODO add recursion for folders in the folder?
		File f = new File(folder);
		if (f.listFiles() != null) {
			for(File ff : f.listFiles()) {
				System.out.println(ff);
				sftpChannel.put(new FileInputStream(ff), ff.getName(), ChannelSftp.OVERWRITE);
				String permissionStringInDecimal = "777";
				sftpChannel.chmod(Integer.parseInt(permissionStringInDecimal,8), ff.getName());
			}
		}
	}
	// TODO run in separate thread bacause can be time consumption to make listeners work as fas as posible,
	// TODO because otherwise Controller will hang and then timeout.
	// load from build quiue table quiuied build with lowest start time.
	// find node good for the build.
	// If no free nodes - wait / exit if used Scheduller.
	// upload to node build plan.
	// Upload of CS can take a time. Better if Node will download it by it self.
}
