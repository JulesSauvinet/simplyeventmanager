package fr.univlyon1.sem.parser;

import fr.univlyon1.sem.model.rdf.Entity;
import fr.univlyon1.sem.model.rdf.vocabulary.SEM;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.util.CompatibilityHints;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

@org.springframework.stereotype.Component
public class ICSParser extends AbstractParser  {
    public ArrayList<Entity> parse(File file) {
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING, true);
        Entity entity = null;
        ArrayList<Entity> entities = new ArrayList();
        //PropertyConfigurator.configure("log4j.properties");
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        CalendarBuilder builder = new CalendarBuilder();

        Calendar calendar = null;
        try {
            calendar = builder.build(fin);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }

        for (Iterator it = calendar.getComponents().iterator(); it.hasNext();) {
            Component component = (Component) it.next();

            if(!component.getName().equals("VEVENT")) {
                continue;
            }

            entity = new Entity();
            entities.add(entity);
            entity.setTypeIri("sem:type/event");

            for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
                Property property = (Property) j.next();
                if(property.getName().equals("DTSTART")) {
                    String datestart = SEM.getDtStartIri();
                    entity.addProperty(datestart, property.getValue());
                }
                else if(property.getName().equals("DTEND")) {
                    String dateend = SEM.getDtEndIri();
                    entity.addProperty(dateend, property.getValue());
                }
                else if(property.getName().equals("SUMMARY"))
                {
                    entity.setLabel(property.getValue());
                }
                else if(property.getName().equals("LOCATION"))
                {
                    //Le lieu
                    String location = SEM.getLocationIri();
                    entity.addProperty(location, property.getValue());
                }
                else if(property.getName().equals("DESCRIPTION")) {
                    //La description
                    String description = SEM.getDescriptionIri();
                    entity.addProperty(description, property.getValue());

                }
                else if(property.getName().equals("CATEGORIES"))
                {
                    //La categorie
                    String categorie = SEM.getCategorieIri();
                    entity.addProperty(categorie, property.getValue());

                }
            }
        }

        return entities;
    }
}
