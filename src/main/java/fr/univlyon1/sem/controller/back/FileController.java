package fr.univlyon1.sem.controller.back;

import fr.univlyon1.sem.bean.ConfigBean;
import fr.univlyon1.sem.bean.FileUpload;
import fr.univlyon1.sem.bean.SessionBean;
import fr.univlyon1.sem.dao.EntityDao;
import fr.univlyon1.sem.dao.RdfConfigurationDao;
import fr.univlyon1.sem.enumeration.FileType;
import fr.univlyon1.sem.exception.NotAllowedException;
import fr.univlyon1.sem.model.csv.CSVData;
import fr.univlyon1.sem.model.rdf.Entity;
import fr.univlyon1.sem.model.relationnal.Ontology;
import fr.univlyon1.sem.parser.*;
import fr.univlyon1.sem.validator.FileValidator;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Controleur d'upload de fichier
 */
@Controller
@RequestMapping(path = "/website/conference/{conference}")
public class FileController {

    @Autowired
    FileValidator fileValidator;
    @Autowired
    ConfigBean conf;
    @Autowired
    EntityDao entityDao;
    @Autowired
    SessionBean session;
    @Autowired
    RdfConfigurationDao rdfConfigurationDao;
    @Autowired
    CSVParser csvParser;
    @Autowired
    XMLParser xmlParser;
    @Autowired
    ICSParser icsParser;
    @Autowired
    RDFParser rdfParser;
    @Autowired
    JSONParser jsonParser;

    @RequestMapping(path = "upload", method = RequestMethod.GET)
    public ModelAndView formUploadFile(ModelAndView mv,
                                       @PathVariable String conference) {

        if(!session.isChairConf(conference)){
            throw new NotAllowedException();
        }

        mv.setViewName("conference/file/uploadFile");
        mv.addObject("listOfTypes", FileType.values());
        mv.addObject("conference_name", conference);

        return mv;
    }

    @RequestMapping(path = "upload.do",method = RequestMethod.POST, params = "type")
    public ModelAndView handleFileUpload(@ModelAttribute("uploadedFile") FileUpload uploadFiles,
                                         Model map,
                                         BindingResult result,
                                         @RequestParam String type,
                                         @RequestParam String separateur,
                                         @PathVariable String conference) {

        if(!session.isChairConf(conference)){
            throw new NotAllowedException();
        }

        List<File> newFiles = new ArrayList<>();

        InputStream inputStream = null;
        OutputStream outputStream = null;

        List<MultipartFile> files = uploadFiles.getFiles()
                .stream().filter(f -> !f.isEmpty()).collect(Collectors.toList());

        List<String> fileNames = new ArrayList<String>();

        if (null != files && files.size() > 0) {
            for (MultipartFile multipartFile : files) {
                String fileName = multipartFile.getOriginalFilename();
                fileNames.add(fileName);

                if (!multipartFile.isEmpty()) {
                    try {
                        inputStream = multipartFile.getInputStream();

                        File newFile = new File(conf.UPLOAD_PATH + "/" + fileName);
                        if (!newFile.exists()) {
                            newFile.createNewFile();
                        }
                        outputStream = new FileOutputStream(newFile);
                        int read = 0;
                        //A revoir
                        byte[] bytes = new byte[10240];

                        while ((read = inputStream.read(bytes)) != -1) {
                            outputStream.write(bytes, 0, read);
                        }
                        newFiles.add(newFile);
                    } catch (IOException e) {
                        return failParseFile("Un des fichiers est vide", conference);
                    }
                }
            }
        } else {
            return failParseFile("Un des fichiers est vide", conference);
        }

        if (null != files) { //ARRAY OUT OF BOUNDS EXCEPTION quand NewFiles est vide
            File newFile = newFiles.get(0);
            File xsltFile = null;
            if (files.size() > 1) {
                xsltFile = newFiles.get(1);
            }
            if (newFile != null) {
                FileType fileType;
                fileType = FileType.valueOf(type);
                if (FileType.ICS.equals(fileType)) {
                    List<Ontology> listTypes = new ArrayList<>();

                    List<Ontology> types = rdfConfigurationDao.listRdfType();

                    // Création des listes des types de relations et de propriétés
                    for (Ontology typeICS : types) {
                        if(typeICS.isTypeEvent()){
                            listTypes.add(typeICS);
                        }
                    }
                    try {
                        List<Entity> entities = icsParser.parse(newFile);
                        session.setEntities(entities);
                    } catch (Exception e) {
                        return failParseFile("L'importation des données à échoué", conference);
                    }
                    session.setTypes(listTypes);

                    map.addAttribute("files", fileNames);
                    map.addAttribute("listTypes", listTypes);
                    ModelAndView mv = new ModelAndView("conference/file/showFileICS");
                    mv.addObject("conference_name", conference);
                    return mv;
                } else if (FileType.CSV.equals(fileType)) {
                    try {
                        List<Ontology> types = rdfConfigurationDao.listRdfType();

                        List<Ontology> listTypes = new ArrayList<>();
                        List<Ontology> listProperties = new ArrayList<>();
                        List<Ontology> listRelations = new ArrayList<>();

                        for (Ontology ontology : types) {
                            if(ontology.isType()){
                                listTypes.add(ontology);
                            }
                            if(ontology.isLiteral()){
                                listProperties.add(ontology);
                            }
                            if(ontology.isRelation()){
                                listRelations.add(ontology);
                            }
                        }
                        CSVData csvData = csvParser.parse(newFile, separateur);

                        session.setCsvData(csvData);

                        map.addAttribute("listTypes", listTypes);
                        map.addAttribute("listProperties", listProperties);
                        map.addAttribute("listRelations", listProperties);
                        map.addAttribute("csvData", csvData);
                        ModelAndView mv = new ModelAndView("conference/file/csv_mapping");
                        mv.addObject("conference_name", conference);
                        return mv;
                    } catch (Exception e) {
                        return failParseFile("L'importation des données à échoué", conference);
                    }
                } else if (FileType.XML_XSLT.equals(fileType)) {
                    List<Entity> entities = null;
                    if (xsltFile != null) {
                        try {
                            org.apache.jena.rdf.model.Model model = ModelFactory.createDefaultModel();
                            entities = xmlParser.parse(newFile, xsltFile, conference);
                            session.setEntities(entities);
                            //model.write(System.out);
                        } catch (TransformerException | IOException e) {
                            return failParseFile("L'importation des données à échoué", conference);
                        }
                        xsltFile.delete();
                        map.addAttribute("files", fileNames);
                        ModelAndView mv = new ModelAndView("conference/file/showFileXML");
                        mv.addObject("conference_name", conference);
                        return mv;
                    } else {
                        return failParseFile("Veuillez fournir une feuille XSLT", conference);

                    }
                } else if (FileType.XML_RDF.equals(fileType)) {
                    org.apache.jena.rdf.model.Model model = ModelFactory.createDefaultModel();
                    try {
                        model = rdfParser.parse(newFile, conference);
                    } catch (Exception e) {
                        return failParseFile("L'importation des données à échoué", conference);
                    }
                } else if (FileType.JSON_LD.equals(fileType)) {
                    try {
                        org.apache.jena.rdf.model.Model model = ModelFactory.createDefaultModel();
                        model = jsonParser.parse(newFile, conference);
                    } catch (Exception e) {
                        return failParseFile("L'importation des données à échoué", conference);
                    }
                } else {
                    return failParseFile("Le format n'est pas reconnu", conference);
                }

                newFile.delete();
            } else {
                if (conference != null)
                    return failParseFile("Le format n'est pas reconnu", conference);
            }
        }
        map.addAttribute("files", fileNames);
        ModelAndView mv = new ModelAndView("conference/file/show-file");
        if (conference != null)
            mv.addObject("conference_name", conference);
        return mv;
    }

    protected ModelAndView failParseFile(String s, String conference) {
        session.getMessages().add("file_import.error", s);
        return new ModelAndView("redirect:/website/conference/" + conference + "/upload");
    }

    protected void failValidateFile(String s) {
        session.getMessages().add("file_validate.error", s);
    }

    @RequestMapping(path = "upload/validate")
    public String validationFile(@PathVariable String conference) {

        if(!session.isChairConf(conference)){
            throw new NotAllowedException();
        }

        /*
        for (Entity entity : session.getEntities()) {
            entityDao.persist(conference, entity);
        }
        session.destroysEntities();
        */

        session.getMessages().add("global.success","Vos information on bien été ajoutées");
        return "redirect:/ressource/" + conference;

    }

    /* En fait exclusivement XML & XSLT pour l'instant */
    @RequestMapping(path = "upload/validateXML")
    public String validationFileOthers(@PathVariable String conference) {

        if(!session.isChairConf(conference)){
            throw new NotAllowedException();
        }

        /*try {
            xmlParser.valideModel(conference);
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        for (Entity entity : session.getEntities()) {
            entityDao.persist(conference, entity);
        }
        session.destroysEntities();
        session.getMessages().add("global.success","Vos information on bien été ajoutées");
        return "redirect:/ressource/" + conference;
    }

    @RequestMapping(path = "upload/cancel")
    public String annulationFile(@PathVariable String conference) {

        if(!session.isChairConf(conference)){
            throw new NotAllowedException();
        }

        session.destroysEntities();
        session.getMessages().add("global.success","Opération annulé");
        return "redirect:/website/conference/" + conference + "/upload";
    }

    @RequestMapping(path= "upload/validate_csv", params = {"types", "type"})
    public String validate_csv(@RequestParam("types") List<String> types,
                               @RequestParam("type") String type,
                               @RequestParam(required=false) String btn_validate,
                               @PathVariable String conference
                               ) {

        if(!session.isChairConf(conference)){
            throw new NotAllowedException();
        }

        if (btn_validate != null) {
            if (btn_validate.equals("cancel")) {
                session.destroysCsvDatas();
                return "redirect:/website/conference/" + conference + "/upload";
            } else if (btn_validate.equals("validate")) {
                CSVData csvData = session.getCsvData();
                for (int line = 0; line < csvData.getLignes().size(); line++) {

                    Entity entity = new Entity();

                    if (type == null){
                        failValidateFile("Le type des entités doit être présent");
                        return "redirect:/website/conference/" + conference + "/upload";
                    }

                    entity.setTypeIri(type);

                    List<String> ligne = csvData.getLignes().get(line);

                    //TODO TEST RELATION OU PROPRIETE
                    if (ligne != null) {
                            if (types.size() > 0) {
                                for (int i = 0; i < types.size(); i++) {
                                    if (ligne != null) {
                                        String column = ligne.get(i);
                                        if (column != null) {
                                            if (!column.equals("")) {
                                                if (types.get(i).equals("Libellé")) {
                                                    entity.setLabel(column);
                                                } else {
                                                    //GESTION QUE DES LITERAUX
                                                    entity.addProperty(types.get(i), column);
                                                }/*
                                                else if (types.get(i).isRelation()){
                                                    //GESTION QUE DES LITERAUX
                                                    if (new_relations)
                                                    entity.addRelation(types.get(i).getName(), );
                                                }*/
                                            }
                                        }
                                    }
                                }
                            }
                    }

                    else {
                        failValidateFile("Veuillez remplir tous les champs");
                        return "redirect:/website/conference/" + conference + "/upload";
                    }
                    if (entity.getLabel() == null){
                        failValidateFile("La propriété label doit être présente sur chaque entité");
                        return "redirect:/website/conference/" + conference + "/upload";
                    }
                    entityDao.persist(conference, entity);
                }

                session.destroysCsvDatas();
                session.destroyTypes();
                session.getMessages().add("global.success","Vos information on bien été ajoutées");
                return "redirect:/ressource/" + conference;
            }
        }
        return "redirect:/website/homepage";
    }

    @RequestMapping(path= "upload/validateICS", params = {"eventName", "typeEvent"})
    public String validateICS(@RequestParam("typeEvent") List<String> typeEvent,
                              @RequestParam("eventName") List<String> eventName,
                              @RequestParam(required=false) String btn_validate,
                              @PathVariable String conference) {

        if(!session.isChairConf(conference)){
            throw new NotAllowedException();
        }

        if (btn_validate != null) {
            if (btn_validate.equals("cancel")) {
                session.destroysEntities();
                return "redirect:/website/conference/" + conference + "/upload";
            } else if (btn_validate.equals("validate")) {
                List<Entity> entities = session.getEntities();
                for (int i = 0; i < entities.size(); i++) {
                    Entity entity = entities.get(i);
                    entity.setTypeIri(typeEvent.get(i));
                    if (eventName != null)
                        if (eventName.get(i) != null)
                            if (eventName.get(i).equals(""))
                                entity.setLabel(eventName.get(i));

                    entityDao.persist(conference, entity);
                }
                session.destroysEntities();
                session.destroyTypes();
                session.getMessages().add("global.success","Vos information on bien été ajoutées");
                return "redirect:/ressource/" + conference;
            }
        }
        return "redirect:/website/homepage";
    }

    public FileValidator getFileValidator() {
        return fileValidator;
    }

    public void setFileValidator(FileValidator fileValidator) {
        this.fileValidator = fileValidator;
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

    public SessionBean getSession() {
        return session;
    }

    public void setSession(SessionBean session) {
        this.session = session;
    }

    public RdfConfigurationDao getRdfConfigurationDao() {
        return rdfConfigurationDao;
    }

    public void setRdfConfigurationDao(RdfConfigurationDao rdfConfigurationDao) {
        this.rdfConfigurationDao = rdfConfigurationDao;
    }

    public CSVParser getCsvParser() {
        return csvParser;
    }

    public void setCsvParser(CSVParser csvParser) {
        this.csvParser = csvParser;
    }

    public XMLParser getXmlParser() {
        return xmlParser;
    }

    public void setXmlParser(XMLParser xmlParser) {
        this.xmlParser = xmlParser;
    }

    public ICSParser getIcsParser() {
        return icsParser;
    }

    public void setIcsParser(ICSParser icsParser) {
        this.icsParser = icsParser;
    }

    public RDFParser getRdfParser() {
        return rdfParser;
    }

    public void setRdfParser(RDFParser rdfParser) {
        this.rdfParser = rdfParser;
    }
}

