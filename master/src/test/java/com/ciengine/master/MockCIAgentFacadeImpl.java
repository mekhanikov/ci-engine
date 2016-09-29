package com.ciengine.master;

import com.ciengine.common.Node;
import com.ciengine.master.facades.CIAgentFacade;
import com.ciengine.master.model.BuildModel;
import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by emekhanikov on 14.09.2016.
 */
@Transactional
@Component
public class MockCIAgentFacadeImpl implements CIAgentFacade
{
	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public String getStatus(Node node, int id)
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

	@Override
	public void run(BuildModel buildModel, Node node)
	{
		// todo find list bean by name.
		applicationContext.getBean(buildModel.getExecutionList());
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
}
