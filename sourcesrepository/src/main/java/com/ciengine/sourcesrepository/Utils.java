package com.ciengine.sourcesrepository;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
/**
 * Created by evgenymekhanikov on 20.01.17.
 */
public class Utils {
    //public static String baseDir = "tmp/";

    public static String executeCommand(String relativeDir, String... command) throws IOException, InterruptedException {
        String cmd = "Dir: " + relativeDir + "\n" + "CMD: " + Arrays.toString(command);
        System.out.println(cmd);
        File folder = new File( relativeDir+"/");
        folder.mkdirs();
        ProcessBuilder builder = new ProcessBuilder().directory(folder);
        //builder.redirectErrorStream(true);
        builder.command(command);
        Process process =  builder.start();
        IOThreadHandler outputHandler = new IOThreadHandler(
                process.getInputStream(), System.out);
        outputHandler.start();
        IOThreadHandler errorHandler = new IOThreadHandler(
                process.getErrorStream(), System.err);
        errorHandler.start();
        int result = process.waitFor();
        //System.out.println(outputHandler.getOutput());
        if (result != 0) {
            throw new RuntimeException(cmd + "\nProcess exited with result: " +  result);
        }
        outputHandler.join();
        return outputHandler.getOutput().toString();
    }

    public static String currentDir() {
        String currentDir = System.getProperty("user.dir");
//        Path currentRelativePath = Paths.get("");
//        String s = currentRelativePath.toAbsolutePath().toString();
        currentDir = currentDir.replace("\\", "/");
        //System.out.println("Current relative path is: " + currentDir);
        return currentDir;
    }

    private static class IOThreadHandler extends Thread {
        private InputStream inputStream;
        private StringBuilder output = new StringBuilder();
        private PrintStream out;

        IOThreadHandler(InputStream inputStream, PrintStream out) {
            this.inputStream = inputStream;
            this.out = out;
        }

        public void run() {
            Scanner br = null;
            try {
                br = new Scanner(new InputStreamReader(inputStream));
                String line = null;
                while (br.hasNextLine()) {
                    line = br.nextLine();
                    out.println(line);
                    output.append(line).append(System.getProperty("line.separator"));
                }
            } finally {
                br.close();
            }
        }

        public StringBuilder getOutput() {
            return output;
        }
    }
}