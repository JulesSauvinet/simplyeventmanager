package fr.univlyon1.sem.dao.impl;

import fr.univlyon1.sem.dao.AbstractRdfDao;
import fr.univlyon1.sem.dao.EntityDao;
import fr.univlyon1.sem.exception.ResourceNotFoundException;
import fr.univlyon1.sem.model.rdf.Entity;
import fr.univlyon1.sem.model.rdf.LiteralProperty;
import fr.univlyon1.sem.model.rdf.RelationProperty;
import fr.univlyon1.sem.model.rdf.vocabulary.SEM;
import fr.univlyon1.sem.model.rdf.vocabulary.SWC;
import fr.univlyon1.sem.model.rdf.vocabulary.SWRC;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdf.model.impl.PropertyImpl;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDF;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Manipilation des entités RDF
 */
@Repository
public class EntityDaoImpl extends AbstractRdfDao implements EntityDao {

    public boolean isModelExists(String modelName) {
        return super.isDatasetExists(modelName);
    }

    @Override
    public Model getModelForTest(String name) {
        return getModel(name);
    }

    public boolean isEntityExists(String modelName, String entityIri) {
        Resource res = ResourceFactory.createResource(entityIri);
        return getModel(modelName).contains(res, null, (RDFNode) null);
    }

    public List<String> listModel() {
        List<String> list = new ArrayList<>();

        for (File file : new File(conf.JENA_TDB_PATH).listFiles()) {
            if (file.isDirectory()) {
                list.add(file.getName());
            }
        }

        return list;
    }

    @Override
    public Entity getByIri(String modelName, String iri, boolean loadEntityOfRelation) {
        Model model = getModel(modelName);

        if (model == null) {
            throw new ResourceNotFoundException();
        }

        if (!isEntityExists(modelName, iri)) {
            return null;
        }

        Resource res = model.getResource(iri);
        Entity entity = resourceToEntity(res);

        if (loadEntityOfRelation) {
            for (RelationProperty r : entity.getRelations()) {
                Resource relationResource = model.getResource(r.getIri());
                r.setEntity(resourceToEntity(relationResource));
            }
        }

        return entity;
    }

    @Override
    public Entity getByEmail(String modelName, String email) {
        Model model = getModel(modelName);

        if (model == null) {
            throw new ResourceNotFoundException();
        }

        StmtIterator iter = model.listStatements();
        List<Resource> subjects = new ArrayList<>();
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();

            Property predicate = stmt.getPredicate();
            Resource subject = stmt.getSubject();
            RDFNode object = stmt.getObject();

            if (predicate.equals(FOAF.mbox) && (object.toString()).equals(email)) {
                subjects.add(subject);
            }
        }

        if (subjects.isEmpty()) {
            return null;
        }

        return resourceToEntity(subjects.get(0));
    }

    @Override
    public void persist(String modelName, Entity entity) {

        Model model = getModel(modelName);
        if (model == null) {
            throw new ResourceNotFoundException();
        }

        // L'objet n'existe pas, on attribut une iri unique
        if (entity.getIdIri() == null) {

            String iri;
            if (entity.getTypeIri().equals(FOAF.Person.getURI())) {
                iri = SEM.person + entity.getLabel() + "-" + UUID.randomUUID();
            } else {
                iri = generateUniqueIri();
            }

            entity.setIdIri(iri);
        }

        // Enregistre les nouvelles données
        Resource res = writeInModel(model, entity);
    }

    @Override
    public void createDataset(String modelName) {
        getDataset(modelName, true);
    }

    @Override
    public String getModelDump(String modelName, RDFFormat format) {

        Model model = getModel(modelName);

        if (model == null) {
            throw new ResourceNotFoundException();
        }

        /*String lang = format.getLang().toString().split(":")[1];
        RDFWriter rdfWriter = model.getWriter(lang);
        if(lang.equals("XML/RDF")){
            rdfWriter.setProperty("showXmlDeclaration","true");
        }
        rdfWriter.setProperty("allowBadURIs","true");
        rdfWriter.setProperty("relativeURIs","");
        rdfWriter.setProperty("tab","8");
        rdfWriter.write(model, writer,"");*/

        StringWriter writer = new StringWriter();
        RDFDataMgr.write(writer, model, format.getLang());

        commitModel(model);
        String towrite = writer.toString();
        Charset.forName("UTF-8").encode(towrite);
        return towrite;
    }

    @Override
    public boolean isChairConf(String modelName, String userMail)
    {
        /*************/
        //return true;
        /*************/
        Entity user = getByEmail(modelName,userMail);
        Entity conference = getByIri(modelName, SEM.getConferenceIri(modelName), false);
        if(user == null || conference == null) {
            return false;
        }


        for (RelationProperty rp : user.getRelations()) {
            if (rp.getIri().equals(conference.getIdIri()) && rp.getPredicate().equals(SWRC.organizerOrChairOf.getURI())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String generateUniqueIri() {
        return SEM.getURI() + "entity/" + UUID.randomUUID();
    }

    @Override
    public boolean isAuthor(String modelName, String userMail, String resIri)
    {
        Entity user = getByEmail(modelName,userMail);
        Entity resource = getByIri(modelName, resIri, false);
        if(user == null || resource == null) {
            return false;
        }

        for (RelationProperty rp : user.getRelations()) {
            if (rp.getIri().equals(resource.getIdIri()) && rp.getPredicate().equals(SWRC.author.getURI())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isChairEntity(String modelName, String userMail, String resIri)
    {
        if (isChairConf(modelName, userMail)){
            return true;
        }
        boolean hasSuperEvent = true;
        Entity user = getByEmail(modelName, userMail);
        Entity resource = getByIri(modelName, resIri, false);
        if(user == null || resource == null) {
            return false;
        }

        if (resource.getTypeIri().equals(FOAF.Person.getURI())) { // Dans le cas d'une Publication

            //conctruction d'un ensemble d'entity event qui sont en relation avec la publication

            /*Model model = getModel(modelName);
            Resource res = resourceToEntity(model, resource);
            StmtIterator iter = model.listStatements();
            ArrayList<String> eventsIris = new ArrayList<>();
            while (iter.hasNext()) {
                Statement stmt = iter.nextStatement();

                Property predicate = stmt.getPredicate();
                Resource subject = stmt.getSubject();
                RDFNode object = stmt.getObject();

                if (subject.equals(res) && (object.isResource())) {

                    Resource r = object.asResource();
                    Entity tmp = resourceToEntity(r);
                    eventsIris.add(tmp.getIdIri());

                }
            }*/

           ArrayList<String> eventsIris = new ArrayList<>();
            for (RelationProperty rp : resource.getRelations())
            {
                if(rp.getPredicate().equals(SWRC.organizerOrChairOf.getURI()) || rp.getPredicate().equals(SWRC.participant.getURI())
                        || rp.getPredicate().equals(SWRC.author.getURI()) || rp.getPredicate().equals(SWC.relatedToEvent.getURI()))
                {
                    eventsIris.add(rp.getIri());
                }
            }

            //parcours des events pour verifier si l'user est chair d'un des events
            for(String iri : eventsIris)
            {
                if(isChairEntity(modelName,userMail,iri))
                {
                    return true;
                }
            }

        } else if (resource.getTypeIri().equals(SWC.Paper.getURI())) { //Dans le cas d'une Personne

            ArrayList<String> eventsIris = new ArrayList<>();

            for (RelationProperty rp : resource.getRelations())
            {
                if(rp.getPredicate().equals(SWRC.publication.getURI()))
                {
                    eventsIris.add(rp.getIri());
                }
            }

            //parcours des events pour verifier si l'user est chair d'un des events
            for(String iri : eventsIris)
            {
                if(isChairEntity(modelName,userMail,iri))
                {
                    return true;
                }
            }

        } else { // la ressource est de type Event

            while (hasSuperEvent) {
                for (RelationProperty rp : user.getRelations()) {
                    if (rp.getIri().equals(resource.getIdIri()) && rp.getPredicate().equals(SWRC.organizerOrChairOf.getURI())) {
                        return true;
                    }
                }

                // on cherche le super Event, si il n'existe pas, on sort de la boucle
                hasSuperEvent = false;
                for (RelationProperty relProp : resource.getRelations()) {
                    if (relProp.getPredicate().equals(SWC.isSubEventOf.getURI())) {
                        resource = getByIri(modelName, relProp.getIri(), false);
                        hasSuperEvent = true;
                        break;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean hasFieldMoreInfo(Entity entity) {
        String typeIri  = entity.getTypeIri();
        if (typeIri.equals(FOAF.Person.getURI()))
        {
           return true;
        }
        else if (typeIri.equals(SWC.Paper.getURI()))
        {
            return true;
        }
        else if(typeIri.equals(SWRC.location.getURI()))
        {
            return true;
        }
       return false;
    }

    public boolean isSamePerson(String modelName, String userMail, String resIri)
    {
        Entity entity = getByIri(modelName, resIri, false);


        if(entity == null) {
            return false;
        }

        for (LiteralProperty relProp : entity.getProperties()) {
            if (relProp.getPredicate().equals(FOAF.mbox.getURI())) {
                if(relProp.getValue().equals(userMail)){
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void deleteModel(String modelName) {
        Model model = getModel(modelName);
        if (model == null) {
            throw new ResourceNotFoundException();
        }

        try {
            deleteTDBDirectory(modelName);
        } catch (IOException e) {
            throw new RuntimeException("Can't delete dataset");
        }
    }

    @Override
    public void deleteEntity(String modelName, String iri) {

        Model model = getModel(modelName);

        if (model == null) {
            throw new ResourceNotFoundException();
        }

        removeResource(model, iri, true);
    }

    @Override
    public void importInModel(String modelName, Model modelToImport) {
        Model model = getModel(modelName);

        if (model == null) {
            throw new ResourceNotFoundException();
        }

        StmtIterator iter = modelToImport.listStatements();
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            model.add(stmt);
        }
        commitModel(model);
    }

    @Override
    public List<Entity> getResourceByType(String datasetName, String typeIri) {

        Model model = getModel(datasetName);

        if (model == null) {
            throw new ResourceNotFoundException();
        }

        List<Entity> entities = new ArrayList<>();
        ResIterator iter = model.listResourcesWithProperty(RDF.type, new PropertyImpl(typeIri));

        while (iter.hasNext()) {
            Resource res = iter.nextResource();
            Entity entity = resourceToEntity(res);
            entities.add(entity);
        }

        return entities;
    }

    @Override
    public List<RelationProperty> getRelationByType(String datasetName, String entityTypeIri) {
        List<RelationProperty> relationProperties = new ArrayList<>();

        Model model = getModel(datasetName);


        if (model == null) {
            throw new ResourceNotFoundException();
        }

        // TODO pour l'instant on retourne les types déjà présent
        // dans la conf, pas de contrôle de coherence
        return getExistingTypesInModel(datasetName);
    }

    @Override
    public List<RelationProperty> getExistingTypesInModel(String modelName) {

        Model model = getModel(modelName);

        if (model == null) {
            throw new ResourceNotFoundException();
        }

        List<RelationProperty> types = new ArrayList<>();
        NodeIterator it = model.listObjectsOfProperty(RDF.type);
        while (it.hasNext()) {
            RDFNode object = it.nextNode();
            types.add(new RelationProperty(RDF.type.getURI(), ((Resource) object).getURI()));
        }

        return types;
    }
}
