package fr.univlyon1.sem.dao.impl;


import fr.univlyon1.sem.dao.AbstractDao;
import fr.univlyon1.sem.dao.RdfConfigurationDao;
import fr.univlyon1.sem.model.relationnal.Ontology;
import fr.univlyon1.sem.model.relationnal.PrefixRDF;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RdfConfigurationDaoImpl extends AbstractDao implements RdfConfigurationDao {

    @Override
    public List<PrefixRDF> listPrefix() {
        return listAll(PrefixRDF.class);
    }

    @Override
    public List<Ontology> listRdfType() {
        return listAll(Ontology.class);
    }

    @Override
    public Ontology createRdfType(Ontology ontology) {
        Ontology typeTmp = checkRdfType(ontology.getIri());
        if (typeTmp == null)
            persist(ontology);
        else
            return typeTmp;
        return ontology;
    }

    @Override
    public Ontology checkRdfType(String iri) {
        Ontology tr = getByIri(iri);
        if (tr != null && tr.getIri().equals(iri)) {
            return tr;
        } else {
            return null;
        }
    }

    @Override
    public Ontology getByIri(String iri) {

        Criteria cr = getSession().createCriteria(Ontology.class);
        cr.add(Restrictions.eq("iri", iri));
        return (Ontology) cr.uniqueResult();
    }

}
