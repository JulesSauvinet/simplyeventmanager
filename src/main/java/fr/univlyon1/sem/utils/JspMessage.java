package fr.univlyon1.sem.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe pour la gestion de message sur une
 * page JSP
 */
public class JspMessage {

    Map<String, List<String>> messagesMap;

    public JspMessage() {
        messagesMap = new HashMap<>();
    }

    /**
     * Ajoute un message
     * @param key La clé associée au message
     * @param message Le message
     */
    public void add(String key, String message) {
        List<String> listMessages = messagesMap.get(key);

        if (listMessages == null) {
            listMessages = new ArrayList<>();
        }

        listMessages.add(message);
        messagesMap.put(key, listMessages);
    }


    /**
     * Récupère un message en fonction de
     * sa clé
     * @param key La clé du message
     * @return Le message ou null s'il n'existe pas
     */
    public List<String> get(String key) {
        return messagesMap.get(key);
    }

    /**
     * Récupère un message en fonction de
     * sa clé et le supprime.
     * @param key La clé du message
     * @return Le message ou null s'il n'existe pas
     */
    public List<String> pop(String key) {
        List<String> listMessages = messagesMap.get(key);
        messagesMap.remove(key);
        return listMessages;
    }
}
