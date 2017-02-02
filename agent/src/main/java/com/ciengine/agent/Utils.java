package com.ciengine.agent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by evgenymekhanikov on 20.01.17.
 */
public class Utils {
    //public static String baseDir = "tmp/";

    public static String getWorkspace() {
        return currentDir() + "/workspace";
    }

    public static void clone(String url, String branchName, String toDirPath) {
        //String url = "ssh://git@stash.hybris.com:7999/commerce/entitlements.git";
//        URI uri = null;
//        try {
//            uri = new URI(url);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        String dirName = uri.getPath().replace(".git", "").replace("/", "_");
        File toDirFile = new File(toDirPath);
        String sourcesDirPath = toDirFile.getParent();
        String toDirName = toDirFile.getName();
//        String sourcesDirPath = getWorkspace();
//        String modulePath = sourcesDirPath + toDirName;
        if (!new File(toDirPath).exists()) {
            try {
                Utils.executeCommand(sourcesDirPath, "git", "clone", url, toDirName);

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
//        String sourceBranchName = "origin/develop";
//        String destinationBranchName = "origin/release/6.4.0";

        try {
            Utils.executeCommand(toDirPath, "git", "fetch", "--prune");

            //System.out.print(s);
            // PR develop to release
            //git log --oneline origin/release/6.4.0..origin/develop
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
//        return modulePath;
    }

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


    public static void updateDependencies(String s, Map<String, String> dependencyVersionMap) {
        Document doc = readXML(s);
        List<String> dependencies = new ArrayList<>();
        NodeList deps = doc.getElementsByTagName("dependency");
        for (int i = 0; i < deps.getLength(); i++) {
            Node dep = deps.item(i);
            if (dep.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) dep;
                String artifactId = eElement
                        .getElementsByTagName("artifactId")
                        .item(0)
                        .getTextContent();

                String groupId = eElement
                        .getElementsByTagName("groupId")
                        .item(0)
                        .getTextContent();

                String dependency = groupId + ":" + artifactId;
                String newVersion = dependencyVersionMap.get(dependency);
                if (newVersion != null) {
                    String version = eElement
                            .getElementsByTagName("version")
                            .item(0)
                            .getTextContent();

                    if (version.startsWith("$")) {
                        String propertyName = version.replace("${", "").replace("}", "");
                        doc.getElementsByTagName(propertyName).item(0).setTextContent(newVersion);
                    }
                }



//				dependencies.add();

            }
        }


        try {
            Transformer transformer = null;
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
//			StreamResult console = new StreamResult(System.out);
            //			transformer.transform(source, console);
            StreamResult result = new StreamResult(new File(s));
            transformer.transform(source, result);



            // write the content into xml file


//			transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }


    }

    public static List<String> retrieveDependencies(String s) {
        Document doc = readXML(s);
        List<String> dependencies = new ArrayList<>();
        NodeList deps = doc.getElementsByTagName("dependency");
        for (int i = 0; i < deps.getLength(); i++) {
            Node dep = deps.item(i);
            if (dep.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) dep;
                String artifactId = eElement
                        .getElementsByTagName("artifactId")
                        .item(0)
                        .getTextContent();

                String groupId = eElement
                        .getElementsByTagName("groupId")
                        .item(0)
                        .getTextContent();
                dependencies.add(groupId + ":" + artifactId);

            }
        }
        return dependencies;
    }

    public static Document readXML(String xml) {
        String role1 = null;
        String role2 = null;
        String role3 = null;
        String role4 = null;
        ArrayList<String> rolev;
        rolev = new ArrayList<>();
        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the
            // XML file
            dom = db.parse(xml);
            return dom;


        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        return null;
    }
}
