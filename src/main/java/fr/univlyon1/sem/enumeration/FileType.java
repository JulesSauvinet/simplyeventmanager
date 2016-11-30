package fr.univlyon1.sem.enumeration;

public enum FileType {
    XML_RDF("XML/RDF"),
    JSON_LD("JSON/LD"),
    ICS("ICS/ICAL"),
    CSV("CSV"),
    XML_XSLT("XML & XSLT");

    private String name;

    FileType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}