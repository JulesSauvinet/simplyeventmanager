package fr.univlyon1.sem.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractDao {

    @Autowired
    protected SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public <T> List<T> listAll(Class entityClass) {
        return (List<T>) getSession()
                .createCriteria(entityClass)
                .list();
    }

    public void merge(Object entity) {
        getSession().merge(entity);
    }

    public void persist(Object entity) {
        getSession().persist(entity);
    }

    public void delete(Object entity) {
        getSession().delete(entity);
    }

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
