package fr.univlyon1.sem.validator;

import fr.univlyon1.sem.bean.ConferenceEvent;
import org.springframework.stereotype.Repository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Repository("confValidator")
public class ConfValidator implements Validator {
    @Override
    public boolean supports(Class classe) {
        return ConferenceEvent.class.isAssignableFrom(classe);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ConferenceEvent conf = (ConferenceEvent) target;


        if(conf.getStart_date().isEmpty()){
            errors.rejectValue("start_date", "conference.selectStartDate", "La date de début de la conférence est vide"  );
        }
        if(conf.getEnd_date().isEmpty()){
            errors.rejectValue("end_date", "conference.selectStartDate", "La date de fin de la conférence est vide"  );
        }
        if(conf.getLatitude().isEmpty()){
            errors.rejectValue("latitude", "conference.selectStartDate", "La latitude du lieu où se déroule la conférence est vide"  );
        }
        if(conf.getLongitude().isEmpty()){
            errors.rejectValue("longitude", "conference.selectStartDate", "La latitude du lieu où se déroule la conférence est vide"  );
        }
    }
}
