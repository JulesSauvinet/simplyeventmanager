/*	
 * To change this license header, choose License Headers in Project Properties.	
 * To change this template file, choose Tools | Templates	
 * and open the template in the editor.	
 */	
package fr.univlyon1.sem.parser;

import edu.emory.mathcs.backport.java.util.Arrays;
import fr.univlyon1.sem.model.csv.CSVData;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class CSVParser extends AbstractParser {
    public CSVParser()	
    {	
        
    }
    	
    public CSVData parse(File file, String sep)
    {
        CSVData data = new CSVData(null);	
        try{
            InputStream ips=new FileInputStream(file); 
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);
            String ligne;
            boolean FirstLigne = true;
            while ((ligne=br.readLine())!=null){
                List<String> line = Arrays.asList(ligne.split(sep));
                if(FirstLigne)
                {
                    data = new CSVData(line);
                    FirstLigne = false;
                } else {
                    if(!data.ajouterLigne(line))
                    {
                        //Exception car fichier mal foutu
                        System.err.println("Pas le meme nombre de colonnes. Parsing faux.");
                    }
                }
            }
            br.close(); 
        }                
        catch (Exception e){
            System.out.println(e.toString());
        }
        return data;
    }
}