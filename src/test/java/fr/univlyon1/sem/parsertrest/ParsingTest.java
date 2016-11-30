/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univlyon1.sem.parsertrest;

import org.junit.Test;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;


/**
 *
 * @author p1103800
 */
public class ParsingTest {
    @Test
    public void testICSParser()
    {
        /*
        String file = "testack.ics";
        ICSParser parser = new ICSParser();
        ArrayList<Entity> entities = parser.parse(new File(file));
        assert entities.size() == 1;
        assert entities.get(0).getProperties().size() == 5;
        for(Entity e: entities)
        {
            System.out.println("TypeIri: " + e.getTypeIri());
            System.out.println("IdIri: " + e.getIdIri());
            for(LiteralProperty lp : e.getProperties())
            {
                System.out.println("predicat: " + lp.getPredicate());
                System.out.println("valeur: " + lp.getValue());
            }
        }
        */
    }
    
    @Test
    public void testXMLParser() throws TransformerException, TransformerConfigurationException, IOException
    {
        /*
        XMLParser parser = new XMLParser();
        //parser.parse(new File("testXML.xml"), new File("test.xslt"), config);*/
    }
    @Test
    public void testCSVParser()
    {
        /*
        CSVParser parser = new CSVParser();
        CSVData data = parser.parse(new File("test.csv"),",");*/
        
    }

    @Test
    public void testJSONParser()
    {
        /*File f = new File("eswc-2014-complete.rdf");
        File f2 = new File("eswc-2014-complete.jsonld");
        Model m = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open(f.getAbsolutePath());
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + f + " not found");
        }

        OutputStream output= null;
        try {
            output = new FileOutputStream("eswc-2014-complete.jsonld");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (output != null) {
            TDBLoader.loadModel(m, "eswc-2014-complete.rdf");
            m.write(System.out, "JSON-LD");
            m.write(output, "JSON-LD");
        }
*/
    }

}
