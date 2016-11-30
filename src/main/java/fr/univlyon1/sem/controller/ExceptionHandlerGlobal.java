package fr.univlyon1.sem.controller;

import fr.univlyon1.sem.bean.SessionBean;
import fr.univlyon1.sem.exception.NotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by dusseaux on 03/12/15.
 */

@ControllerAdvice
public class ExceptionHandlerGlobal {
    @Autowired
    SessionBean session;

    @ExceptionHandler(NotAllowedException.class)
    public ModelAndView handleConflict() {
        if (session.isUserLogged()) {
            session.getMessages().add("global.error", "Vous ne possédez pas les droits permettant d'effectuer cette action");
            return new ModelAndView("redirect:/website");
        } else {
            session.getMessages().add("global.error", "Vous devez être connecté afin d'effectuer cette action");
            return new ModelAndView("redirect:/website/connexion");
        }
    }
}
