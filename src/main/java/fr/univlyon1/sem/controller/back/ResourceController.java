package fr.univlyon1.sem.controller.back;

import com.sun.xml.internal.stream.writers.UTF8OutputStreamWriter;
import fr.univlyon1.sem.bean.ConfigBean;
import fr.univlyon1.sem.bean.SessionBean;
import fr.univlyon1.sem.dao.EntityDao;
import fr.univlyon1.sem.dao.RdfConfigurationDao;
import fr.univlyon1.sem.dao.UserDao;
import fr.univlyon1.sem.exception.NotAllowedException;
import fr.univlyon1.sem.exception.ResourceNotFoundException;
import fr.univlyon1.sem.model.rdf.Entity;
import fr.univlyon1.sem.model.rdf.Pair;
import fr.univlyon1.sem.model.rdf.RelationProperty;
import fr.univlyon1.sem.model.rdf.vocabulary.SEM;
import fr.univlyon1.sem.model.relationnal.Ontology;
import fr.univlyon1.sem.utils.UrlUtils;
import fr.univlyon1.sem.utils.json.Json;
import fr.univlyon1.sem.utils.json.JsonArray;
import fr.univlyon1.sem.utils.json.JsonObject;
import fr.univlyon1.sem.validator.EntityValidator;
import org.apache.jena.riot.RDFFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/ressource/{confName}")
public class ResourceController {

    @Autowired
    UserDao userDao;
    @Autowired
    SessionBean session;
    @Autowired
    ConfigBean conf;
    @Autowired
    EntityDao entityDao;
    @Autowired
    RdfConfigurationDao rdfConfigurationDao;
    @Autowired
    EntityValidator entityValidator;

    /**
     * Liste toutes les ontologies possibles
     * TODO... Faire des filtres ?is_type=1, is_relation=0, ...
     *
     * @param response
     * @param confName
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/ontologies", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Ontology> listOntologies(HttpServletResponse response,
                                         @PathVariable String confName) throws IOException {

        // TODO... utiliser un cache pour cette requête
        Map<String, Ontology> ontologies = new HashMap<>();
        for (Ontology ontology : rdfConfigurationDao.listRdfType()) {
            ontologies.put(ontology.getIri(), ontology);
        }

        return ontologies;
    }


    /**
     * Retourne toutes les ressources d'un type particulier
     * de manière alégé (pour optimisatation de requetes Ajax)
     *
     * @param response
     * @param confName Nom de la conférence
     * @param typeIri  IRI du type
     * @throws IOException
     */
    @RequestMapping(value = "/listResource", method = RequestMethod.GET)
    public void getResourceByType(HttpServletResponse response,
                                  @PathVariable String confName,
                                  @RequestParam(required = false) String typeIri) throws IOException {


        JsonArray arr = Json.array();

        if (typeIri != null && !typeIri.isEmpty()) {

            List<Entity> entities = entityDao.getResourceByType(confName, typeIri);
            for (Entity entity : entities) {
                arr.add(Json.object(
                        Json.pair("iri", entity.getIdIri()),
                        Json.pair("label", entity.getLabel())
                ));
            }
        }

        response.setContentType("application/json");
        response.getWriter().print(arr.toString());
    }

    /**
     * Retourne tous les prédicats (relations et propriétés) possible
     * pour un type de ressoruce donné (optimisé pour requête Ajax)
     *
     * @param response
     * @param confName Nom de la conférence
     * @param typeIri  IRI du type de la ressource
     * @throws IOException
     */
    @RequestMapping(value = "/listPredicat", method = RequestMethod.GET)
    public void getPredicatForType(HttpServletResponse response,
                                   @PathVariable String confName,
                                   @RequestParam(required = false) String typeIri) throws IOException {


        JsonObject res = Json.object();
        JsonArray relationsArr = Json.array();
        JsonArray propertiesArr = Json.array();

        if (typeIri != null && !typeIri.isEmpty()) {

            List<Ontology> predicats = rdfConfigurationDao.listRdfType();

            for (Ontology predicat : predicats) {
                if (predicat.isRelation()) {
                    relationsArr.add(Json.object(
                            Json.pair("label", predicat.getName()),
                            Json.pair("predicat", predicat.getIri())
                    ));
                }

                if (predicat.isLiteral()) {
                    propertiesArr.add(Json.object(
                            Json.pair("label", predicat.getName()),
                            Json.pair("predicat", predicat.getIri())
                    ));
                }
            }
        }

        res.add("relations", relationsArr)
                .add("properties", propertiesArr);

        response.setContentType("application/json");
        response.getWriter().print(res.toString());
    }

    /**
     * Renvoie le dataset d'une conférence au format spécifié
     *
     * @param response
     * @param confName
     * @param ext
     */
    @RequestMapping(value = "/dataset.{ext}", method = RequestMethod.GET)
    public void dataset(HttpServletResponse response,
                        @PathVariable String confName,
                        @PathVariable String ext) {

        if (!entityDao.isModelExists(confName)) {
            throw new ResourceNotFoundException();
        }

        if (!session.isChairConf(confName)) {
            throw new NotAllowedException();
        }

        RDFFormat format;

        switch (ext) {
            case "json":
                format = RDFFormat.RDFJSON;
                break;
            case "jsonld":
                format = RDFFormat.JSONLD_PRETTY;
                break;

            case "rdf":
                format = RDFFormat.RDFXML_PRETTY;
                break;

            default:
                throw new ResourceNotFoundException();
        }

        response.setContentType(format.getLang().getContentType().getContentType());
        response.setHeader("Content-Disposition", "attachment; filename=\"dataset." + ext + "\"");

        String dump = entityDao.getModelDump(confName, format);
        try {
            OutputStream out = response.getOutputStream();
            UTF8OutputStreamWriter utf8out = new UTF8OutputStreamWriter(out);
            utf8out.write(dump);
        } catch (IOException e) {
            e.printStackTrace();
            // TODO gérer correctement l'erreur avec une page d'erruer et un message
        } finally {
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * Retourne une ressource au format JSON
     *
     * @param response
     * @param confName
     * @param iri
     * @return
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET, produces = ("!" + MediaType.TEXT_HTML_VALUE))
    public
    @ResponseBody
    Entity getResource(HttpServletResponse response,
                       @PathVariable String confName,
                       @RequestParam(required = false) String iri) throws IOException {

        Entity entity = entityDao.getByIri(confName, iri, true);
        if (entity == null) {
            throw new ResourceNotFoundException();
        }

        return entity;
    }

    /**
     * Consultation d'une ressource au format HTML
     *
     * @param mv
     * @param confName
     * @param iri
     * @param type
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView entityVewer(ModelAndView mv,
                                    @PathVariable String confName,
                                    @RequestParam(required = false) String iri,
                                    @RequestParam(required = false) String type) {

        mv.addObject("conference_name", confName);

        // Visualisation de toutes les entités d'un type
        if (type != null && !type.isEmpty()) {

            mv.setViewName("entity/list_entity_by_type");
            List<Entity> listEntity = entityDao.getResourceByType(confName, type);
            mv.addObject("list_entity", listEntity);
            mv.addObject("type_entities", type);
        }
        // Visualisation d'une entité
        else {
            // IRI non spécifié => on affiche la
            // ressource de la conférence elle-même
            if (iri == null || iri.isEmpty()) {
                iri = SEM.getConferenceIri(confName);
            }

            Entity entity = entityDao.getByIri(confName, iri, true);

            if (entity == null) {
                throw new ResourceNotFoundException();
            }

            List<RelationProperty> listTypeEntity = entityDao.getExistingTypesInModel(confName);
            mv.addObject("list_type_entity", listTypeEntity);
            mv.setViewName("conference/conference-view");
            mv.addObject("conference", entity);
        }

        return mv;
    }

    @RequestMapping(path = "supprimer.do", method = RequestMethod.GET)
    public String doDelete(@PathVariable String confName) {

        if (!session.isChairConf(confName)) {
            throw new NotAllowedException();
        }

        entityDao.deleteModel(confName);
        session.getMessages().add("global.success", "Conference " + confName + " supprimée");
        return "redirect:/website/homepage";
    }

    @RequestMapping(path = "editeur-entite.do", method = RequestMethod.POST)
    public String doCreateEntity(@PathVariable String confName,
                                 @RequestParam String label,
                                 @ModelAttribute("entity") Entity entity,
                                 BindingResult result) {

        if (!session.isChairEntity(confName, entity.getIdIri())) {
            throw new NotAllowedException();
        }

        entityValidator.validate(entity, result);
        if (result.hasErrors()) {
            session.getMessages().add("entity.error", "Données incorrect ou manquantes");
            return "redirect:/ressource/" + confName + "/editeur-entite";
        }


        entityDao.persist(confName, entity);

        session.getMessages().add("global.success", "L'entité '" + entity.getLabel() + "' a été ajouté avec succes");
        return "redirect:/ressource/" + confName + "?iri=" + UrlUtils.encode(entity.getIdIri());
    }

    @RequestMapping(path = "supprimer-entite", method = RequestMethod.GET, params = "iri")
    public String doDeleteEntity(@PathVariable String confName,
                                 @RequestParam String iri) {

        if (!session.isChairEntity(confName, iri)) {
            throw new NotAllowedException();
        }

        Entity entity = entityDao.getByIri(confName, iri, false);

        if (entity == null) {
            throw new ResourceNotFoundException();
        }

        entityDao.deleteEntity(confName, iri);

        session.getMessages().add("global.success", "L'entité '" + entity.getLabel() + "' a bien été supprimée");
        return "redirect:/ressource/" + confName;
    }

    @RequestMapping(path = "editeur-entite", method = RequestMethod.GET)
    public ModelAndView fromCreateEntity(@PathVariable String confName,
                                         @RequestParam(required = false) String iri) {

        if (!session.isChairEntity(confName, iri)) {
            throw new NotAllowedException();
        }

        List<Ontology> types = rdfConfigurationDao.listRdfType()
                .stream().filter(Ontology::isType)
                .collect(Collectors.toList());

        ModelAndView mv = new ModelAndView("entity/entity_editor");
        mv.addObject("listTypes", types);
        mv.addObject("conf", confName);
        mv.addObject("entity", null);

        // Modification d'un entité
        if (iri != null) {
            Entity entity = entityDao.getByIri(confName, iri, true);

            if (entity == null) {
                throw new ResourceNotFoundException();
            }

            mv.addObject("entity", entity);
        }
        mv.addObject("conference_name", confName);
        return mv;
    }

    @RequestMapping(path = "editer-cpi", method = RequestMethod.GET)
    public ModelAndView formEditCpi(@PathVariable String confName,
                                    @RequestParam String iri) {

        if (!session.isChairEntity(confName, iri) && !session.isAuthor(confName, iri) && !session.isSamePerson(confName, iri)) {
            throw new NotAllowedException();
        }

        ModelAndView mv = new ModelAndView("entity/entity-update-cpi");
        mv.addObject("conference_name", confName);
        mv.addObject("iriEntity", iri);


        //mv.addObject("iriCpi", "http://univ-lyon1.fr/sem#cpi/");

        return mv;

    }

    @RequestMapping(path = "editer-cpi.do", method = RequestMethod.POST)
    public String doEditCpi(@PathVariable String confName,
                            /*@RequestParam String iriCpi,*/
                            @RequestParam String iriEntity,
                            @RequestParam String nameLink,
                            @RequestParam String nameField) {

        if (!session.isChairEntity(confName, iriEntity) && !session.isAuthor(confName, iriEntity) && !session.isSamePerson(confName, iriEntity)) {
            throw new NotAllowedException();
        }

        //remplir le champ
        Entity entity = entityDao.getByIri(confName, iriEntity, true);
        if (nameLink.equals("") || nameField.equals("")) {
            session.getMessages().add("update_cpi.error", "Données incorrect ou manquantes");
            return "redirect:/ressource/" + confName + "/editer-cpi?iri=" + UrlUtils.encode(iriEntity);
        } else {
            Pair pair = new Pair();
            pair.setFirst(nameField);
            pair.setSecond(nameLink);
            entity.setCpi(pair);

            entityDao.persist(confName, entity);

            return "redirect:/ressource/" + confName + "?iri=" + UrlUtils.encode(entity.getIdIri());
        }
    }


    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public SessionBean getSession() {
        return session;
    }

    public void setSession(SessionBean session) {
        this.session = session;
    }

    public ConfigBean getConf() {
        return conf;
    }

    public void setConf(ConfigBean conf) {
        this.conf = conf;
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

    public EntityValidator getEntityValidator() {
        return entityValidator;
    }

    public void setEntityValidator(EntityValidator entityValidator) {
        this.entityValidator = entityValidator;
    }
}
