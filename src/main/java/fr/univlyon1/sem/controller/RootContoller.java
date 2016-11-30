package fr.univlyon1.sem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Redirige toute requête qui n'est pas destiné
 * au front controller (/website/*) ou ressource controller
 * (/ressource/*) vers la page d'accueil
 */
@Controller
public class RootContoller {

    @RequestMapping("/*")
    public String redirectToWebsite(){
        return "redirect:/website/homepage";
    }
}