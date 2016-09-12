package com.ciengine.master;



import com.ciengine.common.*;
import com.jcraft.jsch.*;

import java.io.*;


/**
 * Created by emekhanikov on 05.09.2016.
 */
public class CIEngineImpl implements CIEngine
{
	@Override
	public void submitEvent(CIEngineEvent ciEngineEvent) throws CIEngineException
	{// TODO

	}

	@Override public Module findModuleByGitUrl(String gitUrl)
	{// TODO
		return null;
	}

	@Override public Build runOnNode(Node node)
	{
		return null;
	}

	public static void main(String[] strings) {
		// TODO Run all controllers
		// TODO initiate all Listeners
		String user = "ev";
		String password = "weter";
		String host = "127.0.0.1";
		int port=22;

		String remoteFile="sample.txt";

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
}
