package fr.univlyon1.sem.dao;

import fr.univlyon1.sem.bean.ConfigBean;
import fr.univlyon1.sem.model.rdf.Entity;
import fr.univlyon1.sem.model.rdf.Pair;
import fr.univlyon1.sem.model.rdf.vocabulary.SEM;
import fr.univlyon1.sem.model.relationnal.PrefixRDF;
import org.apache.commons.io.FileUtils;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdf.model.impl.PropertyImpl;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AbstractRdfDao {

    private static Map<String, Dataset> datasetInstances = new HashMap<>();

    @Autowired
    protected ConfigBean conf;

    @Autowired
    protected RdfConfigurationDao rdfConfigurationDao;

    /**
     * Test l'existence d'un dataset
     *
     * @param name
     * @return
     */
    protected boolean isDatasetExists(String name) {
        String path = conf.JENA_TDB_PATH + "/" + name;
        File folder = new File(path);
        return folder.exists() && folder.canWrite();
    }

    /**
     * Retourne un modèle avec un transaction démaré
     *
     * @param modelName
     * @return null si le modèle n'existe pas
     */
    protected Model getModel(String modelName) {
        Dataset dataset = getDataset(modelName);

        if (dataset == null) {
            return null;
        }

        Model model = dataset.getDefaultModel();
        model.begin();

        return model;
    }

    /**
     * Persist les modification d'un modèle
     * sur le disque
     *
     * @param m
     */
    protected void commitModel(Model m) {
        m.commit();
    }

    /**
     * Récupère un dataset via son nom
     *
     * @param datasetName
     * @return null si le dataset n'existe pas
     */
    protected Dataset getDataset(String datasetName) {
        return getDataset(datasetName, false);
    }

    /**
     * Récupère une une instance de dataset via son nom
     *
     * @param datasetName
     * @param createIfNotExist
     * @return null si le datast n'existe pas et createIfNotExists = false
     */
    protected Dataset getDataset(String datasetName, boolean createIfNotExist) {

        if (!createIfNotExist && !isDatasetExists(datasetName)) {
            return null;
        }

        Dataset instance = datasetInstances.get(datasetName);


        if (instance == null) {
            // Chargement ou création de la base TDB
            // On utilise un dossier par conférence
            instance = TDBFactory.createDataset(conf.JENA_TDB_PATH + "/" + datasetName);
            datasetInstances.put(datasetName, instance);

            for (PrefixRDF p : rdfConfigurationDao.listPrefix()) {

                // Dirty fix : NullPointer exception des fois. Surement un prblème
                // dans l'api Jena
                try {
                    instance.getDefaultModel().setNsPrefix(p.getPrefix(), p.getUrl());
                }catch(Exception e){

                }
            }
        }

        return instance;
    }

    /**
     * Converti une resource en une entity
     *
     * @param res
     * @return
     */
    protected Entity resourceToEntity(Resource res) {

        Entity entity = new Entity();
        entity.setIdIri(res.getURI());

        Pair cpi = new Pair();

        StmtIterator it = res.listProperties();
        while (it.hasNext()) {
            Statement stmt = it.nextStatement();
            String predicate = stmt.getPredicate().getURI();
            RDFNode object = stmt.getObject();

            // L'objet est une resource => relation
            if (object instanceof Resource) {

                // Type de la ressource
                if(predicate.equals(RDF.type.getURI())){
                    entity.setTypeIri(object.toString());
                }
                else{
                    entity.addRelation(predicate, object.toString());
                }
            }
            // L'objet est un litéral
            else {

                // Label de la ressource
                if(predicate.equals(RDFS.label.getURI())){
                    entity.setLabel(object.toString());
                }
                else if(predicate.equals(SEM.cpi_label.getURI())){
                    cpi.setFirst(object.toString());
                }
                else if(predicate.equals(SEM.cpi_value.getURI())){
                    cpi.setSecond(object.toString());
                }
                else{
                    entity.addProperty(predicate, object.toString());
                }
            }

            if(cpi.getFirst() != null  && cpi.getSecond() != null){
                entity.setCpi(cpi);
            }
        }

        return entity;
    }

    /**
     * Supprime une ressource d'un modèle
     *
     * @param model Le modèle
     * @param iriResource L'iri de la ressource
     * @param removeRelationsToIt Si true, supprime également tous les triplets ayant
     *                            comme objet la ressource à supprimer.
     */
    protected void removeResource(Model model, String iriResource, boolean removeRelationsToIt){
        Resource res = model.createResource(iriResource);
        model.removeAll(res, null, (RDFNode) null);

        if(removeRelationsToIt){
            model.removeAll(null, null, res);
        }
    }

    /**
     * Converti une entity en une ressource
     *
     * @param entity
     * @return
     */
    protected Resource writeInModel(Model model, Entity entity) {

        Resource res = model.createResource(entity.getIdIri());

        // Supprime les données existantes pour ce modèle
        removeResource(model, entity.getIdIri(), false);

        // Type de la ressource
        Resource resType = model.createResource(entity.getTypeIri());
        Literal label = model.createLiteral(entity.getLabel());

        res.addProperty(RDF.type, resType);
        res.addProperty(RDFS.label, label);

        if(entity.getCpi() != null){
            res.addProperty(SEM.cpi_label, model.createLiteral(entity.getCpi().getFirst()));
            res.addProperty(SEM.cpi_value, model.createLiteral(entity.getCpi().getSecond()));
        }

        entity.getProperties().forEach(p -> {
            if (p.getPredicate() != null && p.getValue() != null) {
                res.addProperty(new PropertyImpl(p.getPredicate()), p.getValue());
            }
        });

        entity.getRelations().forEach(p -> {
            if(p.getPredicate() != null && p.getIri() != null){
                Resource relatedResource = model.createResource(p.getIri());
                res.addProperty(new PropertyImpl(p.getPredicate()), relatedResource);
            }

        });

        commitModel(model);

        return res;
    }    /**
     * Supprime le dossier d'un dataset TDB
     *
     * @param modelName
     */
    protected void deleteTDBDirectory(String modelName) throws IOException {

        try{
            getDataset(modelName, false).close();
        }catch(Exception e){
            e.printStackTrace();
        }

        String path = conf.JENA_TDB_PATH + "/" + modelName;
        FileUtils.deleteDirectory(new File(path));

        // Supprime l'instance du cache
        datasetInstances.remove(modelName);
    }

    public static Map<String, Dataset> getDatasetInstances() {
        return datasetInstances;
    }

    public static void setDatasetInstances(Map<String, Dataset> datasetInstances) {
        AbstractRdfDao.datasetInstances = datasetInstances;
    }

    public ConfigBean getConf() {
        return conf;
    }

    public void setConf(ConfigBean conf) {
        this.conf = conf;
    }

    public RdfConfigurationDao getRdfConfigurationDao() {
        return rdfConfigurationDao;
    }

    public void setRdfConfigurationDao(RdfConfigurationDao rdfConfigurationDao) {
        this.rdfConfigurationDao = rdfConfigurationDao;
    }
}
