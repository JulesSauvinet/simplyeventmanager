package fr.univlyon1.sem.dao;

import fr.univlyon1.sem.model.rdf.Entity;
import fr.univlyon1.sem.model.rdf.RelationProperty;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFFormat;

import java.util.List;

public interface EntityDao {

    public Model getModelForTest(String name);

    /**
     * Liste les modèles existant (ie: un dossier TDB)
     *
     * @return
     */
    List<String> listModel();

    /**
     * Retourne l'entity d'un dataset en fonction
     * de son iri
     *
     * @param modelName Nom du modèle
     * @param iri Iri de la ressource
     * @param loadEntityOfRelation
     * @return
     */
    Entity getByIri(String modelName, String iri, boolean loadEntityOfRelation);

    public Entity getByEmail(String dataSet, String email);

    /**
     * Détermine si un dataset est existe
     *
     * @param modelName Nom du modèle
     * @return
     */
    boolean isModelExists(String modelName);

    /**
     * Détermine si une ressource existe
     *
     * @param entityIri Iri de l'entité
     * @return
     */
    boolean isEntityExists(String modelName, String entityIri);

    /**
     * Sauvegarde une entity dans la base TDB
     *
     * @param modelName Iri de l'entité
     * @param entity Entité à enregistrer
     */
    void persist(String modelName, Entity entity);

    /**
     * Créer un nouveau modèle
     *
     * @param modelName Nom du nouveau modèle
     */
    void createDataset(String modelName);

    /**
     * Récupère un dump du dataset d'un modèle dans
     * un format paritculier
     *
     * @param modelName Nom du modèle
     * @return
     */
    String getModelDump(String modelName, RDFFormat format);

    /**
     * Supprime un modèle
     *
     * @param modelName Nom du modèle
     */
    void deleteModel(String modelName);

    /**
     * Supprime une entité d'un modèle
     *
     * @param modelName Nom du modèle
     * @param iri Iri de la ressource à supprimer
     */
    void deleteEntity(String modelName, String iri);

    /**
     * Ajoute un modèle dans un modèle existant
     *
     * @param modelName Nom du modèle de destination
     * @param modelToImport Modèle à importer
     */
    void importInModel(String modelName, Model modelToImport);

    /**
     * Récupère les ressources d'un type spécifique
     *
     * @param typeIri
     * @return
     */
    List<Entity> getResourceByType(String datasetName, String typeIri);

    /**
     * Retourne la liste des relations possibles pour un
     * type de ressource spéficique
     * <p>
     * TODO rajouter des params pour filtrer (is_type, is_relation, ...)
     *
     * @param entityTypeIri
     * @return
     */
    List<RelationProperty> getRelationByType(String datasetName, String entityTypeIri);

    /**
     * Returne une liste des différents types d'entité présentes
     * dans un modèle
     *
     * @param modelName Nom du modèle
     * @return
     */
    List<RelationProperty> getExistingTypesInModel(String modelName);

    /**
     * Dis si l'utilisateur est chair de l'event ou d'un event père
     *
     * @param modelName le nom de la conférence concernée
     * @param userMail  l'email de l'utilisateur concerné
     * @param resIri    l'iri de la ressource concernée
     * @return un booléen
     */
    boolean isChairEntity(String modelName, String userMail, String resIri);

    /**
     * Dis si l'utilisateur possède ou non un certain droit sur une conference
     *
     * @param modelName le nom de la conférence concernée
     * @param userMail  l'email de l'utilisateur concerné
     * @return un booléen
     */
    boolean isChairConf(String modelName, String userMail);

    /**
     * Dis si l'utilisateur est l'auteur d'une publication
     *
     * @param modelName le nom de la conférence concernée
     * @param userMail  l'email de l'utilisateur concerné
     * @param resIri    l'iri de la ressource concernée
     * @return un booléen
     */
    boolean isAuthor(String modelName, String userMail, String resIri);

    /**
     * Détermine si une personne du modèle RDF
     * correspond à un utilisateur
     *
     * @param modelName Nom du modèle
     * @param userMail Email de l'utilisateur
     * @param resIri Iri de la personne
     * @return
     */
    boolean isSamePerson(String modelName, String userMail, String resIri);

    /**
     * Génère un identifiant unique pour une ressouroce
     *
     * @return
     */
    String generateUniqueIri();

    /**
     * Retourne vrai si le type de l'entité correspond à un type de ressource qui peut posséder un champ plus d'info
     *
     * @param entity l'entité concernée
     * @return un booléen
     */
    boolean hasFieldMoreInfo(Entity entity);
}
