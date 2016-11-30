package fr.univlyon1.sem.bean;

import fr.univlyon1.sem.model.rdf.Entity;
import fr.univlyon1.sem.model.rdf.LiteralProperty;

public class ConferenceEvent {
    String name;
    String longitude;
    String latitude;
    String start_date;
    String end_date;
    String description;

    public ConferenceEvent(Entity conference) {
        this.name = conference.getLabel();
        for (LiteralProperty p : conference.getProperties()) {
            if (p.getPredicate().endsWith("lieu/longitude")) {
                longitude = p.getValue();
            } else if (p.getPredicate().endsWith("lieu/latitude")) {
                latitude = p.getValue();
            } else if (p.getPredicate().endsWith("date/debut")) {
                start_date = p.getValue();
            } else if (p.getPredicate().endsWith("date/fin")) {
                end_date = p.getValue();
            } else if (p.getPredicate().endsWith("info/description")) {
                description = p.getValue();
            }
        }
    }

    public ConferenceEvent() {
        super();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
