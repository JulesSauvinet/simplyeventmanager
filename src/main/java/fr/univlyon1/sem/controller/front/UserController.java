/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univlyon1.sem.controller.front;

import fr.univlyon1.sem.bean.ConfigBean;
import fr.univlyon1.sem.bean.SessionBean;
import fr.univlyon1.sem.dao.UserDao;
import fr.univlyon1.sem.model.relationnal.ConfirmationToken;
import fr.univlyon1.sem.model.relationnal.User;
import fr.univlyon1.sem.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;


@Controller
@RequestMapping(value = "/website/user/")
public class UserController {

    @Autowired
    UserDao userDao;
    @Autowired
    SessionBean session;
    @Autowired
    ConfigBean conf;
    private String email;
    private String tok;

    @RequestMapping(path = "validate", method = RequestMethod.GET, params = {"id_user", "token"})
    public String validateAccount(@RequestParam int id_user,
                                  @RequestParam String token) {

        User u = userDao.getById(id_user);

        if (u == null) {
            getSession().getMessages().add("global.error", "L'utilisateur n'existe pas");
        } else {
            if (!u.isActivated()) {
                ConfirmationToken tokenObj = u.getToken(0);
                if (tokenObj.getToken().equals(token)) {
                    u.setActivated(true);
                    u.getConfirmationsToken().remove(tokenObj);
                    userDao.updateUser(u);
                    session.setUser(u);
                    session.getMessages().add("global.success", "Votre compte est maintenant activé");
                } else {
                    getSession().getMessages().add("global.error", "Le clé de validation est incorrecte");
                }
            } else {
                getSession().getMessages().add("global.error", "Ce compte a déjà été activé");
            }
        }

        return "redirect:/website/homepage";
    }

    public void envoyerMailValidation(User u) {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.port", "587");
        Session sess = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("sympleventmanager@gmail.com", "multimif");
            }
        });

        MimeMessage message = new MimeMessage(sess);
        try {

            message.setText(
                    "Bonjour " + u.getFirstName() + " " + u.getLastName()
                            + ",\n\nVous etes bien inscrit a Symple Event Manager. "
                            + "Pour valider votre inscription et pouvoir vous connecter, veuillez cliquer sur le lien suivant:"
                            + "\n\n" + conf.HOSTNAME + conf.CTX_PATH + "/website/user/validate?id_user=" + u.getId() + "&token" + "=" + tok);

            message.setSubject("Validation de l'inscription a Symple Event Manager");
            InternetAddress rec = new InternetAddress(u.getEmail());
            message.setFrom(new InternetAddress("sympleventmanager@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, rec);
            Transport.send(message);
        } catch (NoSuchProviderException e) {
            System.err.println("Pas de transport disponible pour ce protocole");
            System.err.println(e);
        } catch (AddressException e) {
            System.err.println("Adresse invalide");
            System.err.println(e);
        } catch (MessagingException e) {
            System.err.println("Erreur dans le message");
            System.err.println(e);
        }
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

        if (!pwd.equals(pwdCheck)) {
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

            tok = UUID.randomUUID().toString();
            u.addConfirmationToken(0, tok);
            userDao.updateUser(u);

            envoyerMailValidation(u);

            session.getMessages().add("global.success", "Votre compte a bien été créé</br>Un email de confirmation à était envoyé à " + u.getEmail());
            return "redirect:/website/homepage";
        }
    }

    @RequestMapping(path = "connexion.do", method = RequestMethod.POST, params = {"id", "mdp"})
    public String connexion(@RequestParam String id,
                            @RequestParam String mdp) {

        User u = userDao.checkUser(id, mdp);

        if (u != null) {
            if (u.isActivated()) {

                session.setUser(u);
                session.getMessages().add("global.success", "Vous êtes connecté");
                return "redirect:/website/homepage";
            } else {
                envoyerMailValidation(u);
                session.getMessages().add("connection.error", "Vous ne pouvez pas vous connecter a ce compte car il n'est pas actif </br> Un email à était envoyé à " + u.getEmail() + "</br>Il contient un lien permettant d'activer votre compte");
                return "redirect:/website/connexion";
            }
        } else {
            if (userDao.getByMail(id) != null) {
                session.getMessages().add("connection.error", "Mauvais mot de passe");
            } else {
                session.getMessages().add("connection.error", "Aucun compte connu pour cette adresse mail");
            }
            return "redirect:/website/connexion";
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
