package org.formation.eeos.minieclipse.model.technique.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.formation.eeos.minieclipse.model.metier.Projet;


/**
 * Utilitaire de compilation
 *
 */
public interface Compiler {
	
public static void compiler(Projet leProjet) {
        
        List<Path> result;
        try {
            result =  Files.walk(leProjet.getConfig().getClassePath()).filter(Files::isRegularFile).collect(Collectors.toList());
            result.forEach(e->{
                String str =  String.join(" ", "javac -d", leProjet.getConfig().getOutPutPath().toString(), e.toString());
                Runtime rt = Runtime.getRuntime();
                System.out.println("la compil");
                try {
                    Process pr = rt.exec(str);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
