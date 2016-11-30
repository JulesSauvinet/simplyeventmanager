package fr.univlyon1.sem.bean;

import fr.univlyon1.sem.dao.EntityDao;
import fr.univlyon1.sem.dao.RdfConfigurationDao;
import fr.univlyon1.sem.model.csv.CSVData;
import fr.univlyon1.sem.model.rdf.Entity;
import fr.univlyon1.sem.model.relationnal.Ontology;
import fr.univlyon1.sem.model.relationnal.User;
import fr.univlyon1.sem.utils.JspMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionBean implements Serializable {

    @Autowired
    EntityDao entityDao;

    @Autowired
    RdfConfigurationDao rdfDao;

    private User user;
    private JspMessage messages = new JspMessage();
    protected List<Entity> entities;
    protected List<Ontology> typesRdf;
    protected CSVData csvData;
    protected HashMap<String,String> mapPredicatesName;

    public boolean isUserLogged(){
        return user != null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JspMessage getMessages() {
        return messages;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public void setTypes(List<Ontology> typesRdf) {
        this.typesRdf = typesRdf;
    }
    public void destroysEntities(){
        this.entities=null;
    }

    public void destroyTypes(){
        this.typesRdf=null;
    }

    public boolean isChairEntity(String modelName, String resIri) {return isUserLogged() && entityDao.isChairEntity(modelName,user.getEmail(),resIri);}

    public boolean isChairConf(String modelName) {return isUserLogged() && entityDao.isChairConf(modelName, user.getEmail());}

    public boolean isAuthor(String modelName, String resIri) {return isUserLogged() && entityDao.isAuthor(modelName, user.getEmail(), resIri); }

    public boolean hasFieldMoreInfo(Entity entity) {return entityDao.hasFieldMoreInfo(entity);}

    public boolean isSamePerson(String modelName, String resIri) {return isUserLogged() && entityDao.isSamePerson(modelName,user.getEmail(),resIri);}

    public EntityDao getEntityDao() {
        return entityDao;
    }

    public void setEntityDao(EntityDao entityDao) {
        this.entityDao = entityDao;
    }

    public void setMessages(JspMessage messages) {
        this.messages = messages;
    }

    public List<Ontology> getTypesRdf() {
        return typesRdf;
    }

    public void setTypesRdf(List<Ontology> typesRdf) {
        this.typesRdf = typesRdf;
    }

    public void setCsvData(CSVData csvData) {
        this.csvData = csvData;
    }

    public CSVData getCsvData() {
        return csvData;
    }

    public void destroysCsvDatas() {
        this.csvData=null;
    }

    public HashMap<String, String> getMapPredicatesName() {
        if(this.mapPredicatesName == null){
            this.mapPredicatesName = new HashMap<>();
            }
        return mapPredicatesName;
    }

    public void setMapPredicatesName(HashMap<String, String> mapPredicatesName) {
        this.mapPredicatesName = mapPredicatesName;
    }

    public String getPredicatLabel(String iri)
    {
        if(this.mapPredicatesName == null){
            this.mapPredicatesName = new HashMap<>();
            List<Ontology> types = rdfDao.listRdfType();
            for(Ontology t : types){
                this.mapPredicatesName.put(t.getIri(), t.getName());
            }
        }

        String name = this.mapPredicatesName.get(iri);

        return name != null ? name : iri;
    }
}
