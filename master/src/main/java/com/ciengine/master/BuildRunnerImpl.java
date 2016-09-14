package com.ciengine.master;

import com.ciengine.master.dao.BuildDao;
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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by emekhanikov on 13.09.2016.
 */
@Component
public class BuildRunnerImpl implements BuildRunner
{
	private static final Logger log = LoggerFactory.getLogger(BuildRunnerImpl.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


	@Autowired
	BuildDao buildDao;

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
		// TODO Run all controllers
		// TODO initiate all Listeners
		String user = "ev";
		String password = "weter";
		String host = "127.0.0.1";
		int port=22;

		try
		{
			JSch jsch = new JSch();
			Session session = jsch.getSession(user, host, port);
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");
			System.out.println("Establishing Connection...");
			session.connect();
			System.out.println("Connection established.");
			System.out.println("Crating SFTP Channel.");
			ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect();
			System.out.println("SFTP Channel created.");
			File f1 = new File("D:\\prj\\ci-engine\\master\\src\\main\\resources\\build.sh");
			//			sftpChannel.rm("tmp");
			//			sftpChannel.mkdir("tmp");
			sftpChannel.cd("tmp");
			sftpChannel.put(new FileInputStream(f1), f1.getName(), ChannelSftp.OVERWRITE);
			File f2 = new File("D:\\prj\\ci-engine\\master\\src\\main\\resources\\Dockerfile");
			sftpChannel.put(new FileInputStream(f2), f2.getName(), ChannelSftp.OVERWRITE);
			//sftpChannel.cd("/home/ev/.ssh");
			File f3 = new File("C:\\cygwin\\home\\emekhanikov\\.ssh\\id_rsa");
			sftpChannel.put(new FileInputStream(f3), f3.getName(), ChannelSftp.OVERWRITE);

			File f4 = new File("C:\\Users\\emekhanikov\\.m2\\settings.xml");
			sftpChannel.put(new FileInputStream(f4), f4.getName(), ChannelSftp.OVERWRITE);

			File f5 = new File("D:\\prj\\ci-engine\\agent\\target\\agent-1.0-SNAPSHOT.jar");
			sftpChannel.put(new FileInputStream(f5), f5.getName(), ChannelSftp.OVERWRITE);

			File f6 = new File("D:\\prj\\ci-engine\\environment_variables.properties");
			;
			InputStream stream = new ByteArrayInputStream(buildModel.getInputParams().getBytes(StandardCharsets.UTF_8));
			sftpChannel.put(stream, f6.getName(), ChannelSftp.OVERWRITE);

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

			((ChannelExec) channel).setCommand("cd tmp; ./build.sh");
			String permissionStringInDecimal = "777";
			sftpChannel.chmod(Integer.parseInt(permissionStringInDecimal,8), "/home/ev/tmp/build.sh");
			sftpChannel.chmod(Integer.parseInt(permissionStringInDecimal,8), "/home/ev/tmp/Dockerfile");
			sftpChannel.chmod(Integer.parseInt("600",8), "/home/ev/tmp/id_rsa");

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
			System.out.println(e);
		}
		catch (SftpException e)
		{
			e.printStackTrace();
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
