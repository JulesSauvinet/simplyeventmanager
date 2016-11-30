package fr.univlyon1.sem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HTTPErrorHandler{

    @RequestMapping(value="/404")
    public String error404(){
        return "404";
    }
}