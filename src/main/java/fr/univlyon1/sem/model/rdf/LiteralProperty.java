package fr.univlyon1.sem.model.rdf;

/**
 * Représente une propriété d'une
 * ressource RDF
 */
public class LiteralProperty {

    private String value;
    private String predicate;

    public LiteralProperty() {
    }

    public LiteralProperty(String predicate, String value) {
        this.value = value;
        this.predicate = predicate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }
}
