package fr.univlyon1.sem.parser;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.log4j.BasicConfigurator;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class RDFParser extends AbstractParser  {

    /**
     * n'est pas vraiment un parser, merge le modele rdf transformé avec le modele du dataset courant
     *
     * @param file le fichier rdf
     * @param name le nom du modele courant
     * @return
     */
    public Model parse(File file, String name)
    {
        BasicConfigurator.configure();
        Model m = ModelFactory.createDefaultModel();

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            Reader r = new InputStreamReader(fis, "utf8");
            if (r == null) {
                throw new IllegalArgumentException(
                        "File: " + file + " not found");
            }

            m.read(r, "", "RDF/XML");
            entityDao.importInModel(name, m);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //InputStream in = FileManager.get().open(file.getAbsolutePath());

        return m;
    }

    /**
     * Concrètement ne sert à rien
     *
     * @param file
     * @return
     */
    public Model parse(File file)
    {
        BasicConfigurator.configure();
        Model m = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open(file.getAbsolutePath());
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + file + " not found");
        }
        m.read(in, null);
        return m;
    }

}