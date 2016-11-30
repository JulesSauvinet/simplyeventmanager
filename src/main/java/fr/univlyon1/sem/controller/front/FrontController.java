package fr.univlyon1.sem.controller.front;

import fr.univlyon1.sem.bean.SessionBean;
import fr.univlyon1.sem.dao.EntityDao;
import fr.univlyon1.sem.dao.UserDao;
import fr.univlyon1.sem.model.relationnal.User;
import fr.univlyon1.sem.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Controlleur du site web
 */
@Controller
@RequestMapping(path = "/website/*")
public class FrontController {

    @Autowired
    UserDao userDao;
    @Autowired
    EntityDao entityDao;
    @Autowired
    SessionBean session;


    @RequestMapping(path = {"/", "homepage"})
    public ModelAndView homepage(ModelAndView mv) {

        mv.setViewName("homepage");

        List<String> conferences = entityDao.listModel();
        mv.addObject("conferences", conferences);

        return mv;
    }

    @RequestMapping(path = "deconnexion.do")
    public String deconnect(HttpSession session) {
        session.invalidate();
        return "redirect:/website/homepage";
    }


    @RequestMapping(path = "creer-compte")
    public String formCreateAccount() {
        return "user/create-account";
    }

    @RequestMapping(path = "creer-compte.do", method = RequestMethod.POST, params = {"email", "pwd", "firstname", "lastname"})
    public String createAccount(@RequestParam(value = "email") String mail,
                              @RequestParam(value = "firstname") String firstname,
                              @RequestParam(value = "lastname") String lastname,
                              @RequestParam(value = "pwd") String pwd,
                              @RequestParam(value = "pwd-check") String pwdCheck) {

        boolean error = false;

        if (userDao.getByMail(mail) != null) {
            session.getMessages().add("creation_compte.error", "Un utilisateur existe déjà pour cette adresse mail");
            error = true;
        }

        if (mail.isEmpty()) {
            error = true;
            session.getMessages().add("creation_compte.error", "Veuillez remplir le champ email");
        }

        if (firstname.isEmpty()) {
            error = true;
            session.getMessages().add("creation_compte.error", "Veuillez remplir le champ nom de famille");
        }

        if (lastname.isEmpty()) {
            error = true;
            session.getMessages().add("creation_compte.error", "Veuillez remplir le champ prenom");
        }

        if (pwd.isEmpty()) {
            error = true;
            session.getMessages().add("creation_compte.error", "Veuillez remplir le champ mot de passe");
        }

        if (pwdCheck.isEmpty()) {
            error = true;
            session.getMessages().add("creation_compte.error", "Veuillez remplir le champ mot de passe");
        }

        if(!pwd.equals(pwdCheck)){
            error = true;
            session.getMessages().add("creation_compte.error", "La confirmation du mot de passe est incorrecte");
        }

        if (error) {
            return "redirect:/website/creer-compte";
        } else {
            User u = new User();
            u.setEmail(mail);
            u.setFirstName(firstname);
            u.setLastName(lastname);
            u.setPassword(PasswordUtil.sha1(pwd));
            userDao.createUser(u);
            session.setUser(u);
            return "redirect:/website/homepage";
        }
    }

    @RequestMapping(path = "formulaireDesPublication")
    public String formulaireDesPublication() {
        return "formulaireDesPublication";
    }

    @RequestMapping(path = "myaccount")
    public ModelAndView monCompte() {
        ModelAndView mv = new ModelAndView("user/user-account");
        mv.addObject("user", session.getUser());
        return mv;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
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
}