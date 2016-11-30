package fr.univlyon1.sem.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileNotFoundException;

@Component
@PropertySource("classpath:/config/global.properties")
public class ConfigBean {

    @Value("${jena.tdb.path}")
    public String JENA_TDB_PATH;
    @Value("${upload.path}")
    public String UPLOAD_PATH;
    public String USER_HOME_PATH;
    @Value("${hostname}")
    public String HOSTNAME;
    @Value("${mail}")
    public String MAIL;
    @Value("${pwd}")
    public String PASSWORD;
    public String CTX_PATH;
    @Autowired
    private ServletContext servletContext;

    public ConfigBean() {

    }

    @PostConstruct
    public void initIt() throws Exception {

        CTX_PATH = servletContext.getContextPath();

        // TODO Bug avec le path pour windows, surement la regex qui pose problème
        USER_HOME_PATH = System.getProperty("user.home");
        UPLOAD_PATH = UPLOAD_PATH.replaceAll("^~(.*)", System.getProperty("user.home") + "$1");
        JENA_TDB_PATH = JENA_TDB_PATH.replaceAll("^~(.*)", System.getProperty("user.home") + "$1");

        String dirs[] = {JENA_TDB_PATH, UPLOAD_PATH};

        // Création des dossiers s'il n'existent pas
        for (String dir : dirs) {
            System.out.printf("Création du dossier: %s ... ", dir);
            File f = new File(dir);
            f.mkdirs();

            if (f.exists() && f.canWrite()) {
                System.out.println("ok");
            } else {
                System.out.println("echec");
                throw new FileNotFoundException("Impossible de créer le dossier '" + f.getAbsoluteFile() + "' ou droit en écriture manquants.");
            }
        }
    }
}
