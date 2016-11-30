package fr.univlyon1.sem.model.rdf;

import java.util.ArrayList;
import java.util.List;

/**
 * Représentation d'une ressource RDF
 */
public class Entity {

    protected String idIri;
    protected String typeIri;
    protected String label;
    protected Pair cpi; //champ Plus d'Infos -> utile seulement pour certains type d'entities

    protected List<RelationProperty> relations = new ArrayList<>();
    protected List<LiteralProperty> properties = new ArrayList<>();

    public String getIdIri() {
        return idIri;
    }

    public void setIdIri(String idIri) {
        this.idIri = idIri;
    }

    public String getTypeIri() {
        return typeIri;
    }

    public void setTypeIri(String typeIri) {
        this.typeIri = typeIri;
    }

    public List<RelationProperty> getRelations() {
        return relations;
    }

    public void setRelations(List<RelationProperty> relations) {
        this.relations = relations;
    }

    public List<LiteralProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<LiteralProperty> properties) {
        this.properties = properties;
    }

    public void addRelation(RelationProperty relationProperty) {
        relations.add(relationProperty);
    }

    public void addRelation(String predicate, String iri) {
        relations.add(new RelationProperty(predicate, iri));
    }

    public void addProperty(LiteralProperty literalProperty) {
        properties.add(literalProperty);
    }

    public void addProperty(String predicate, String value) {
        properties.add(new LiteralProperty(predicate, value));
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Pair getCpi() { return cpi; }

    public void setCpi(Pair cpi) { this.cpi = cpi; }

//    public String getCastedIdIri() {
//        String[] parts = idIri.split("#");
//        String iricast1 = parts[parts.length-1];
//        String[] parts2 = iricast1.split("/");
//        String iricast2 = parts2[parts2.length-1];
//        iricast2.toLowerCase().replace(" ", "-");
//        return iricast2;
//    }
//    public String getCastedTypeIri() {
//        String[] parts = typeIri.split("#");
//        String iricast1 = parts[parts.length-1];
//        String[] parts2 = iricast1.split("/");
//        String iricast2 = parts2[parts2.length-1];
//        iricast2.toLowerCase().replace(" ", "-");
//        return iricast2;
//    }
}
