package fr.univlyon1.sem.parser;


import fr.univlyon1.sem.bean.ConfigBean;
import fr.univlyon1.sem.bean.SessionBean;
import fr.univlyon1.sem.dao.EntityDao;
import fr.univlyon1.sem.dao.RdfConfigurationDao;
import org.springframework.beans.factory.annotation.Autowired;


public class AbstractParser {
    @Autowired
    protected ConfigBean conf;
    @Autowired
    protected EntityDao entityDao;
    @Autowired
    protected SessionBean session;
    @Autowired
    protected RdfConfigurationDao rdfConfigurationDao;

    public ConfigBean getConf() {
        return conf;
    }

    public void setConf(ConfigBean conf) {
        this.conf = conf;
    }

    public EntityDao getEntityDao() {
        return entityDao;
    }

    public void setEntityDao(EntityDao entityDao) {
        this.entityDao = entityDao;
    }

    public SessionBean getSession() {
        return session;
    }

    public void setSession(SessionBean session) {
        this.session = session;
    }

    public RdfConfigurationDao getRdfConfigurationDao() {
        return rdfConfigurationDao;
    }

    public void setRdfConfigurationDao(RdfConfigurationDao rdfConfigurationDao) {
        this.rdfConfigurationDao = rdfConfigurationDao;
    }
}
