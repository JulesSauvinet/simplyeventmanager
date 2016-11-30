package fr.univlyon1.sem.dao;

import fr.univlyon1.sem.model.relationnal.Ontology;
import fr.univlyon1.sem.model.relationnal.PrefixRDF;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RdfConfigurationDao {
    @Transactional
    List<PrefixRDF> listPrefix();

    @Transactional
    List<Ontology> listRdfType();

    @Transactional
    Ontology createRdfType(Ontology ontology);

    @Transactional
    Ontology checkRdfType(String iri);

    @Transactional
    Ontology getByIri(String iri);
}
