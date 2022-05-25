package org.formation.eeos.minieclipse.model.technique.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.stream.Stream;

import javafx.beans.property.StringProperty;

public class CompilExec {
    private static void fireCommand(String[] cmd, StringProperty message) { 
        (new Thread() {
            public void run() {
                try {
                	System.out.println(String.join(" ", cmd));
                    Process p = Runtime.getRuntime().exec(cmd);
                    
                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

                    String s = null;
                    if(message != null) {
                    	 while ((s = stdInput.readLine()) != null) {
                         	message.set(message.get()+s+"\r\n");
                         }

                         while ((s = stdError.readLine()) != null) {
                         	message.set(message.get()+s+"\r\n");
                         }
                    }
                    p.waitFor();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String rootpath;
    private String sourcepath;
    private String libpath;
    private String classpath;
    private String basepack;

    private String main;

    public CompilExec(String rootpath, String sourcepath, String libpath, String classpath, String main, String basepack) {
        this.rootpath = rootpath;
        this.sourcepath = sourcepath;
        this.libpath = libpath;
        this.classpath = classpath;
        this.main = main;
        this.basepack = basepack;
    }

    public void compile(StringProperty returnMessage) {
        StringJoiner lib = new StringJoiner(";");
        lib.add(sourcepath);
        try (Stream<Path> walk = Files.walk(Paths.get(libpath))) {
            walk.filter(Files::isRegularFile).forEach(file -> {
                lib.add(file.toString());
            });
        } catch (Exception e) {
            e.printStackTrace(); 
        }

        String src = Paths.get(sourcepath, basepack.replace(".", System.getProperty("file.separator"))).toString() + System.getProperty("file.separator") + "*.java";

        String[] cmd;
        if (System.getProperty("os.name").startsWith("Windows")) {
            cmd = new String[] {"cmd.exe", "/c", "javac", "-d", classpath, src};
        } else {
            cmd = new String[] {"javac", "-d", classpath, "-cp", lib.toString(), src};
        }
        fireCommand(cmd, returnMessage);


    }

    public void execute() {
        String[] cmd;
        if(main == null || main.trim().equals("")) {
        	
        }
        if (System.getProperty("os.name").startsWith("Windows")) {
            cmd = new String[] {"cmd.exe", "/c", "java", "-cp", classpath, main};
        } else {
            cmd = new String[] {"java", "-cp", classpath, main};
        }

        fireCommand(cmd, null);
    }

    /**
     * @return the rootpath
     */
    public String getRootpath() {
        return rootpath;
    }

    /**
     * @param rootpath the rootpath to set
     */
    public void setRootpath(String rootpath) {
        this.rootpath = rootpath;
    }

    /**
     * @return the sourcepath
     */
    public String getSourcepath() {
        return sourcepath;
    }

    /**
     * @param sourcepath the sourcepath to set
     */
    public void setSourcepath(String sourcepath) {
        this.sourcepath = sourcepath;
    }

    /**
     * @return the libpath
     */
    public String getLibpath() {
        return libpath;
    }

    /**
     * @param libpath the libpath to set
     */
    public void setLibpath(String libpath) {
        this.libpath = libpath;
    }

    /**
     * @return the classpath
     */
    public String getClasspath() {
        return classpath;
    }

    /**
     * @param classpath the classpath to set
     */
    public void setClasspath(String classpath) {
        this.classpath = classpath;
    }

    /**
     * @return the main
     */
    public String getMain() {
        return main;
    }

    /**
     * @param main the main to set
     */
    public void setMain(String main) {
        this.main = main;
    }

    /**
     * @return the basepack
     */
    public String getBasepack() {
        return basepack;
    }

    /**
     * @param basepack the basepack to set
     */
    public void setBasepack(String basepack) {
        this.basepack = basepack;
    }
    
}
