package fr.univlyon1.sem.model.relationnal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rdf_ontology")
public class Ontology {
    @Id
    private int id;
    @Column
    private String name;
    @Column
    private String iri;
    @Column(name = "is_type")
    boolean isType;
    @Column(name = "is_literal")
    boolean isLiteral;
    @Column(name = "is_relation")
    boolean isRelation;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIri() {
        return iri;
    }

    public void setIri(String iri) {
        this.iri = iri;
    }

    public boolean isLiteral() {
        return isLiteral;
    }

    public void setIsLiteral(boolean isLiteral) {
        this.isLiteral = isLiteral;
    }

    public boolean isRelation() {
        return isRelation;
    }

    public void setIsRelation(boolean isRelation) {
        this.isRelation = isRelation;
    }

    public boolean isType() {
        return isType;
    }

    public void setIsType(boolean isType) {
        this.isType = isType;
    }

    public boolean isTypeEvent() {
        CharSequence cs = "event";
        return isType && (iri.toLowerCase().contains(cs));
    }

}
