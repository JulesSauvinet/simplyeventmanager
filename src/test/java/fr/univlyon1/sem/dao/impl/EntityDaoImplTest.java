package fr.univlyon1.sem.dao.impl;

import fr.univlyon1.sem.model.rdf.Entity;
import fr.univlyon1.sem.model.rdf.vocabulary.SEM;
import fr.univlyon1.sem.model.rdf.vocabulary.SWC;
import fr.univlyon1.sem.model.rdf.vocabulary.SWRC;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.*;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDF;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class EntityDaoImplTest extends fr.univlyon1.sem.AbstractDaoTest{
    @Test
    public void testGetByEmail() throws Exception {

        String name = "test2";
        Dataset ds = TDBFactory.createDataset(config.JENA_TDB_PATH + "/" + name);
        Model m = ds.getDefaultModel();

        //créer une personne
        Entity person = new Entity();
        person.setTypeIri(FOAF.Person.getURI());
        person.setLabel("Alix");
        person.addProperty(FOAF.name.getURI(),"Alix");
        person.addProperty(FOAF.mbox.getURI(),"alix.gonnot@mydom.com");
        entityDao.persist(name, person);

        Entity p = entityDao.getByEmail(name,"alix.gonnot@mydom.com");

        if(p == null)
        {
            System.out.println("p is null");
        }
        else
        {
            System.out.println("p is : " + p.getIdIri());
        }

        assert (p.getIdIri().equals(person.getIdIri()));

        entityDao.deleteModel(name);

    }



    @Test
    public void testGetResourceByType() throws Exception {

        Entity confEntity = new Entity();
        String name = "test";
        Dataset ds = TDBFactory.createDataset(config.JENA_TDB_PATH + "/" + name);
        Model m = ds.getDefaultModel();
        //Model m = entityDao.getModelForTest(name);
        m = FileManager.get().loadModel("./eswc-2015-complete.rdf");


        StmtIterator iter = m.listStatements();

        List<Resource> subjects = new ArrayList<>();
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();

            Property predicate = stmt.getPredicate();
            Resource subject = stmt.getSubject();
            RDFNode object = stmt.getObject();

            if (predicate.equals(RDF.type) && ((Resource) object).getURI().equals("http://xmlns.com/foaf/0.1/Person")) {
                System.out.println(subject);
                subjects.add(subject);
            }
        }

        entityDao.deleteModel(name);
    }

    @Test
    public void testSetRoleOrRelation() throws Exception
    {
        String name = "test2";
        Dataset ds = TDBFactory.createDataset(config.JENA_TDB_PATH + "/" + name);

        Entity conf = new Entity();
        conf.setIdIri(SEM.getConferenceIri(name));
        conf.setTypeIri(SWC.ConferenceEvent.getURI());
        conf.setLabel(name);
        entityDao.persist(name, conf);

        Entity person = new Entity();
        person.setTypeIri(FOAF.Person.getURI());
        person.setLabel("Alix");
        person.addProperty(FOAF.name.getURI(),"Alix");
        person.addProperty(FOAF.mbox.getURI(),"alix.gonnot@mydom.com");
        person.addRelation(SWRC.organizerOrChairOf.getURI(),conf.getIdIri());
        entityDao.persist(name, person);

        Entity event = new Entity();
        event.setIdIri(SEM.getEventIri("Event1"));
        event.setTypeIri(SWC.SessionEvent.getURI());
        event.setLabel("Event1");
        event.addRelation(SWC.isSubEventOf.getURI(),conf.getIdIri());

        entityDao.persist(name, event);

        Entity publication = new Entity();
        publication.setLabel("Paper 1");
        publication.setTypeIri(SWC.Paper.getURI());
        publication.addRelation(SWRC.publication.getURI(),event.getIdIri());

        entityDao.persist(name, publication);

        Entity author = new Entity();
        author.setLabel("Jena");
        author.setTypeIri(FOAF.Person.getURI());
        author.addProperty(FOAF.name.getURI(),"Jena");
        author.addProperty(FOAF.mbox.getURI(),"jena@test.fr");
        author.addRelation(SWRC.author.getURI(), publication.getIdIri());

        entityDao.persist(name, author);

        entityDao.deleteModel(name);
    }
}