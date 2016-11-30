package fr.univlyon1.sem.enumeration;

import fr.univlyon1.sem.model.rdf.vocabulary.SWRC;

public enum RoleEnum {

    CONFERENCECHAIR("Conference Chair", SWRC.organizerOrChairOf.getURI()),
    CHAIR("Chair", SWRC.organizerOrChairOf.getURI()),
    AUTHOR("Author","http://swrc.ontoware.org/ontology#author");

    private String name;
    private String iri;

    RoleEnum(String name, String iri)
    {
        this.name = name;
        this.iri = iri;
    }

    public String getIri() {
        return iri;
    }

    public String getName() {
        return name;
    }

    public void setIri(String iri) {
        this.iri = iri;
    }

    public void setName(String name) {
        this.name = name;
    }
}
