package fr.univlyon1.sem.controller.front;

import fr.univlyon1.sem.bean.SessionBean;
import fr.univlyon1.sem.dao.UserDao;
import fr.univlyon1.sem.model.relationnal.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = {"/website/connexion", "/website/connexion/*"})
public class ConnexionController {
    @Autowired
    UserDao userDao;
    @Autowired
    SessionBean session;
    private String email;

    @RequestMapping(method = RequestMethod.GET)
    public String formulaireConnexion() {
        return "user/connection";
    }




    @RequestMapping(path = "facebook", method = RequestMethod.POST, params = {"accessToken"})
    public
    @ResponseBody
    String connexionFacebook(HttpServletRequest req,
                             @RequestParam(value = "accessToken") String token) {

        Facebook facebookTemplate = new FacebookTemplate(token, "semapp", "1654332561514237");
        com.restfb.types.User user = facebookTemplate.fetchObject("me", com.restfb.types.User.class);

        String userId = user.getId();

        User u = userDao.getByFbId(userId);

        if (u != null) {
            session.setUser(u);
            return req.getContextPath() + "/website/homepage";
        } else {
            //session.getMessages().add("connection.error", "Aucun comptes lié à votre compte facebook");
            return req.getContextPath() + "/website/connexion";
        }
    }


    @RequestMapping(path = "facebook/link", method = RequestMethod.POST, params = {"accessToken"})
    public
    @ResponseBody
    String connexion_link(HttpServletRequest req,
                          @RequestParam(value = "accessToken") String token) {

        Facebook facebookTemplate = new FacebookTemplate(token, "semapp", "1654332561514237");
        com.restfb.types.User user = facebookTemplate.fetchObject("me", com.restfb.types.User.class);

        String userId = user.getId();

        User u = null;

        if (userId != null && session.getUser() != null) {
            u = session.getUser();
            u.setFb_id(userId);
            System.out.println(userId);
            userDao.updateUser(u);
        }

        if (u != null) {
            session.getMessages().add("global.success", "Vous êtes connecté");
            return req.getContextPath() + "/website/myaccount";
        } else {
            return req.getContextPath() + "/website/connexion";
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
