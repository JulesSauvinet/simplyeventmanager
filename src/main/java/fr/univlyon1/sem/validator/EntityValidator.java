package fr.univlyon1.sem.validator;

import fr.univlyon1.sem.model.rdf.Entity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EntityValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Entity.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "typeIri", "type.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "label", "label.empty");
        Entity e = (Entity) o;

        // Il faut que le type soit présent
//        if(!e.getRelations().stream()
//                .anyMatch(r -> r.getIri().equals(RDF.type.getURI()) && !r.getPredicate().isEmpty()  )){
//
//            errors.pushNestedPath("type.empty");
//        }
    }
}
