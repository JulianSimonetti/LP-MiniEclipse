package org.formation.eeos.minieclipse.model.technique.util;

import java.io.IOException;
import java.util.Scanner;

import org.formation.eeos.minieclipse.model.metier.Projet;
import org.formation.eeos.minieclipse.model.technique.exceptions.NoMainException;

public interface Executer {
	
	/*public static void execute(Projet projet) {
        Runtime rt = Runtime.getRuntime();
        System.out.println(projet.getConfig().getOutPutPath().toString());
        try {
            if (projet.getMain()==null) {throw new NoMainException();}
            if(projet.getMain().getClasses().size()<1){throw new NoMainException();
            }
            String str = projet.getMain().getClasses().get(0).getNom();
            System.out.println(str);
            String cmd =  String.join(" ", "java -classpath", projet.getConfig().getOutPutPath().toString(),str);
            System.out.println(cmd);
            Process pr = rt.exec(cmd);

             Scanner s = new Scanner(pr.getInputStream()).useDelimiter("\\A");
             String result = s.hasNext() ? s.next() : "";
             System.out.println(result);

        } catch (IOException | NoMainException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    } */
	public static void execute(Projet projet) {
		Runtime rt = Runtime.getRuntime();
		try {
			Process pr = rt.exec("java -classpath E:\\Documents\\mEclipse\\mp\\target\\classes App");
			
			 Scanner s = new Scanner(pr.getInputStream()).useDelimiter("\\A");
			 String result = s.hasNext() ? s.next() : "";
			 System.out.println(result);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
