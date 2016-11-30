package fr.univlyon1.sem.parser;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.log4j.BasicConfigurator;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;

@Component
public class JSONParser extends AbstractParser {

    /**
     * n'est pas vraiment un parser, merge le modele rdf transformé avec le modele du dataset courant
     *
     * @param file le fichier json
     * @param name le nom du modele courant
     * @return
     */
    public Model parse(File file, String name)
    {
        BasicConfigurator.configure();
        Model m = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open(file.getAbsolutePath());
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + file + " not found");
        }
        m.read(in, null, "JSON-LD");
        entityDao.importInModel(name, m);
        return m;
    }
}
