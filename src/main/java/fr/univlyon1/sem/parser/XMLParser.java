package fr.univlyon1.sem.parser;

import fr.univlyon1.sem.model.rdf.Entity;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDF;
import org.apache.log4j.BasicConfigurator;
import org.springframework.stereotype.Component;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class XMLParser extends AbstractParser {

   // public File temp;

    public List<Entity> parse(File xml, File xslt, String nameModel) throws TransformerException, IOException {

        File temp = new File(conf.UPLOAD_PATH + "/" + "temp" + "-" + UUID.randomUUID()+".rdf");

            if (!temp.exists()) {
                temp.createNewFile();
            }
            OutputStream outputStream = new FileOutputStream(temp);

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Source stylesource = new StreamSource(xslt);
            Templates template = tFactory.newTemplates(stylesource);
            Transformer trans = template.newTransformer();
            StringWriter out = new StringWriter();
            Source xmls = new StreamSource(xml);
            Result r = new StreamResult(outputStream);
            trans.transform(xmls, r);

            BasicConfigurator.configure();
            Model m = ModelFactory.createDefaultModel();
            InputStream in = FileManager.get().open(temp.getAbsolutePath());
            if (in == null) {
                throw new IllegalArgumentException(
                        "File: " + temp + " not found");
            }
            m.read(in, null,"RDF/XML");

            List<Entity> entities = new ArrayList<>();

            StmtIterator iter = m.listStatements();

            int entityIndex=0;
            while (iter.hasNext()) {

                Statement stmt = iter.nextStatement();
                Resource subject = stmt.getSubject();
                Property predicate = stmt.getPredicate();
                RDFNode object = stmt.getObject();

                if (predicate.equals(RDF.type)){

                    Entity entity = new Entity();

                    entity.setTypeIri(subject.getNameSpace());
                    entity.setLabel("entity" + entityIndex);
                    entityIndex++;

                    //if (object instanceof Resource) {
                      //  entity.addRelation(predicate.getURI(), object.toString());
                    //}

                    // L'objet est un litéral
                    entities.add(entity);
                }
                else if (object instanceof Resource) {
                    if (entities.size() != 0) {
                        Entity entity = entities.get(entityIndex);
                        entity.addProperty(predicate.getURI(), object.toString());
                    }
                }
            }
                // L'objet est une resource => relation
        temp.delete();

        return entities;
    }

    public Model valideModel(String nameModel) throws TransformerException, IOException {
        RDFParser parser = new RDFParser();
        //Model m = parser.parse(temp, nameModel);

        return null; //m;
    }
}
