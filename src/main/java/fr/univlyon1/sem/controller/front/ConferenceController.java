package fr.univlyon1.sem.controller.front;

import fr.univlyon1.sem.bean.ConferenceEvent;
import fr.univlyon1.sem.bean.ConfigBean;
import fr.univlyon1.sem.bean.SessionBean;
import fr.univlyon1.sem.dao.EntityDao;
import fr.univlyon1.sem.dao.RdfConfigurationDao;
import fr.univlyon1.sem.dao.UserDao;
import fr.univlyon1.sem.exception.NotAllowedException;
import fr.univlyon1.sem.exception.ResourceNotFoundException;
import fr.univlyon1.sem.model.rdf.Entity;
import fr.univlyon1.sem.model.rdf.vocabulary.SEM;
import fr.univlyon1.sem.model.rdf.vocabulary.SWC;
import fr.univlyon1.sem.model.rdf.vocabulary.SWRC;
import fr.univlyon1.sem.validator.ConfValidator;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controlleur pour la gestion des conférences
 */
@Controller
@RequestMapping(value = {"/website/conference/*"})
public class ConferenceController {

    @Autowired
    UserDao userDao;
    @Autowired
    SessionBean session;
    @Autowired
    ConfValidator confValidator;
    @Autowired
    ConfigBean conf;
    @Autowired
    EntityDao entityDao;
    @Autowired
    RdfConfigurationDao rdfConfigurationDao;

    @RequestMapping(path = "creer", method = RequestMethod.GET)
    public ModelAndView formCreateConf() {

        if(!session.isUserLogged())
        {
            throw new NotAllowedException();
        }

        return new ModelAndView("conference/create-conference");
    }

    @RequestMapping(path = "creer.do", method = RequestMethod.POST, params = {"name"})
    public String createConf(@RequestParam String name,
                             @RequestParam(required = false) Boolean import_dataset) {

        if(!session.isUserLogged())
        {
            throw new NotAllowedException();
        }

        if (name.isEmpty()) {
            session.getMessages().add("create_conf.error", "Veuillez renseigner un nom pour pour la conférence");
            return "redirect:/website/conference/creer";
        } else if (entityDao.isModelExists(name)) {
            session.getMessages().add("create_conf.error", "Une conférence portant le même nom existe déjà");
            return "redirect:/website/conference/creer";
        }

        entityDao.createDataset(name);

        Entity confEntity = new Entity();
        confEntity.setIdIri(SEM.getConferenceIri(name));
        confEntity.setTypeIri(SWC.ConferenceEvent.getURI());
        confEntity.setLabel(name);

        entityDao.persist(name, confEntity);

        // Création du chair et de son role
        Entity chairEntity = new Entity();
        chairEntity.setTypeIri(FOAF.Person.getURI());
        chairEntity.setLabel(session.getUser().getFullName());
        chairEntity.addProperty(FOAF.mbox.getURI(),session.getUser().getEmail());
        chairEntity.addRelation(SWRC.organizerOrChairOf.getURI(), confEntity.getIdIri());

        entityDao.persist(name,chairEntity);

        if(import_dataset != null && import_dataset){
            return "redirect:/website/conference/" + name + "/upload";
        }
        session.getMessages().add("global.success","Conference créée");
        return "redirect:/website/conference/" + name + "/editer";
    }

    @RequestMapping(path = "{name}/editer")
    public ModelAndView fromEditConf(@PathVariable String name) {

        if(!session.isChairConf(name))
        {
            throw new NotAllowedException();
        }

        ModelAndView mv = new ModelAndView("conference/edit-conference");

        Entity conference = entityDao.getByIri(name, SEM.getConferenceIri(name), true);

        if(conference == null){
            throw new ResourceNotFoundException();
        }
        ConferenceEvent conferenceEvent = new ConferenceEvent(conference);
        mv.addObject("conference_name", name);
        mv.addObject("conference", conferenceEvent);
        return mv;
    }

    @RequestMapping(path = "{name}/editer.do", method = RequestMethod.POST)
    public String editConf(@ModelAttribute ConferenceEvent conference,
                           BindingResult result,
                           @PathVariable String name) {

        if(!session.isChairConf(name))
        {
            throw new NotAllowedException();
        }

        confValidator.validate(conference, result);
        if (result.hasErrors()) {
            for (ObjectError error:result.getAllErrors()) {
                session.getMessages().add("edit_conf.error", error.getDefaultMessage());
            }
            return "redirect:/ressource/" + name + "/editer";
        }

        String iri = SEM.getConferenceIri(name);

        if(!entityDao.isEntityExists(name, iri)){
            throw new ResourceNotFoundException();
        }

        Entity entity = new Entity();
        entity.setIdIri(SEM.getConferenceIri(name));
        entity.setTypeIri(SWC.ConferenceEvent.getURI());
        entity.setLabel(name);

        if (conference.getLongitude() != null)
            entity.addProperty(SEM.getURI() + "lieu/longitude", conference.getLongitude());
        if (conference.getLatitude() != null)
            entity.addProperty(SEM.getURI() + "lieu/latitude", conference.getLatitude());
        if (conference.getStart_date() != null)
            entity.addProperty(SEM.getURI() + "date/debut", conference.getStart_date());
        if (conference.getEnd_date() != null)
            entity.addProperty(SEM.getURI() + "date/fin", conference.getEnd_date());
        if (conference.getDescription() != null)
            entity.addProperty(SEM.getURI() + "info/description", conference.getDescription());

        entityDao.persist(name, entity);
        session.getMessages().add("global.success","Modification effectuée");
        return "redirect:/ressource/" + name;
    }
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setConf(ConfigBean conf) {
        this.conf = conf;
    }

    public ConfigBean getConf() {
        return conf;
    }

    public void setSession(SessionBean session) {
        this.session = session;
    }

    public SessionBean getSession() {
        return session;
    }

    public void setConfValidator(ConfValidator confValidator) {
        this.confValidator = confValidator;
    }

    public ConfValidator getConfValidator() {
        return confValidator;
    }

    public EntityDao getEntityDao() {
        return entityDao;
    }

    public void setEntityDao(EntityDao entityDao) {
        this.entityDao = entityDao;
    }

    public RdfConfigurationDao getRdfConfigurationDao() {
        return rdfConfigurationDao;
    }

    public void setRdfConfigurationDao(RdfConfigurationDao rdfConfigurationDao) {
        this.rdfConfigurationDao = rdfConfigurationDao;
    }
}
