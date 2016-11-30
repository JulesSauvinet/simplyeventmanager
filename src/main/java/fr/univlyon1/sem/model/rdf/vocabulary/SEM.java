package fr.univlyon1.sem.model.rdf.vocabulary;


import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;

public class SEM {

    protected static final String NS = "http://univ-lyon1.fr/sem#";
    protected static final String PREFIX = "sem";

    public static final String person = NS + "person/";

    public static final Property cpi_label = ResourceFactory.createProperty(NS + "cpi/label");
    public static final Property cpi_value = ResourceFactory.createProperty(NS + "cpi/value");


    public static final String getURI() {
        return NS;
    }

    public static String getPrefix() {
        return PREFIX;
    }

    public static String getConferenceIri(String conferenceName){
        return NS + "conference/" + conferenceName;
    }

    public static String getLatituteIri(String latitude){
        return NS + "latitude/" + latitude;
    }

    public static String getLongitudeIri(String longitude){
        return NS + "longitude/" + longitude;
    }

    public static String getLocationIri(){
        return NS + "location";
    }


    public static String getDtEndIri(){
        return NS + "dtend";
    }

    public static String getDtStartIri(){
        return NS + "dtstart";
    }

    public static String getEventIri(String typeEvent) {
        String[] parts = typeEvent.split("#");
        if (!(parts.length < 2)) {
            String event = parts[1].toLowerCase().replace("event", "").replace(" ", "-");
            return NS + event + "/" + typeEvent;
        }
        return typeEvent;
    }

    public static String getCategorieIri() {
        return NS + "categorie";
    }

    public static String getDescriptionIri() {
        return NS + "description";
    }

    public static String getURI(String iri) {
        String parts[] = iri.split("#");
        String castedIri = parts[parts.length-1];

        String type;
        //TODO TRAITER LES DIFFERENTS TYPES
        type = SWC.getURI(castedIri.toLowerCase().trim());

        return NS + "/" + type + castedIri;
    }
}