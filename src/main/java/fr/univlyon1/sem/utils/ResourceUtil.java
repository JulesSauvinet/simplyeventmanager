package fr.univlyon1.sem.utils;

import java.net.URISyntaxException;
import java.net.URL;

public class ResourceUtil {
    /**
     * Récupère le chemin absolu d'une ressource présente
     * dans le dossier "resources"
     * @param resource
     * @return
     */
    public static String getPath(String resource) {
        URL res = ResourceUtil.class.getClassLoader().getResource(resource);
        if (res == null) {
            return null;
        }

        try {
            return res.toURI().getPath();
        } catch (URISyntaxException e) {
            return null;
        }
    }
}
