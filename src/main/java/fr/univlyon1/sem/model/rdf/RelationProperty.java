package fr.univlyon1.sem.model.rdf;

/**
 * Représente une relation d'une
 * ressource RDF
 */
public class RelationProperty {
    private String predicate;
    private String iri;

    /**
     * Champ utilisé et remplit uniquement pour certaines
     * vues pour optimisation
     */
    private Entity entity;

    public RelationProperty() {
    }

    public RelationProperty(String predicate, String iri) {
        this.predicate = predicate;
        this.iri = iri;
    }

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    public String getIri() {
        return iri;
    }

    public void setIri(String iri) {
        this.iri = iri;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RelationProperty that = (RelationProperty) o;

        if (!predicate.equals(that.predicate)) return false;
        return iri.equals(that.iri);

    }

    @Override
    public int hashCode() {
        int result = predicate.hashCode();
        result = 31 * result + iri.hashCode();
        return result;
    }
}
