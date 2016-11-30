package fr.univlyon1.sem.daotest;

import fr.univlyon1.sem.model.relationnal.User;
import fr.univlyon1.sem.utils.PasswordUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class UserDaoIntegrationTest extends fr.univlyon1.sem.AbstractDaoTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Transactional
    @Rollback(true)
    //Rollback(back) persistes a notre pour d'autres test!
    public void testCreateUserPositif() throws Exception {

        System.out.println("Test CreateUser");

        //on affiche l'état de la table
        List<User> users = userDao.getAllUsers();
        for (User u : users){
            System.out.println("email  : " + u.getEmail() + " nom : " + u.getLastName() + " prénom : "+ u.getFirstName());
        }

        //création de l'objet user
        User user1 = new User();
        user1.setEmail("julessauvineto@gmail.com");
        user1.setFirstName("Jule");
        user1.setLastName("Sauvinet");
        user1.setPassword("multimilf");

        // on s'assure que l'objet n'a pas encore été persisté
        Assert.assertEquals(user1.getId(), 0);

        user1 =  userDao.createUser(user1);

        List<User> users2 = userDao.getAllUsers();

        for (User u : users2){
            System.out.println("email  : " + u.getEmail() + " nom : " + u.getLastName() + " prénom : "+ u.getFirstName());
        }

        //on s'assure que l'objet à été persisté (son ID a changé car auto-incrémentée)
        Assert.assertNotNull(user1.getId());
        Assert.assertTrue(user1.getId() > 0);

        // on affiche pour verifier que l'utilisateurs à bien été ajouté
        List<User> users3 = userDao.getAllUsers();

        for (User u : users3){
            System.out.println("email  : " + u.getEmail() + " nom : " + u.getLastName() + " prénom : "+ u.getFirstName());
        }

    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCreateUserNegatif() throws Exception {

        List<User> users2 = userDao.getAllUsers();
        for (User u : users2){
            System.out.println("email  : " + u.getEmail() + " nom : " + u.getLastName() + " prénom : "+ u.getFirstName());
        }
        User user1 = userDao.getByMail("contact@jordan-martin.fr");

        //on essaye d'ajouter un utilisateur présent en base
        User user2 =  userDao.createUser(user1);

        // on affiche pour verifier que rien n'a été ajouté
        List<User> users = userDao.getAllUsers();
        for (User u : users){
            System.out.println("email  : " + u.getEmail() + " nom : " + u.getLastName() + " prénom : "+ u.getFirstName());
        }

        Assert.assertEquals(user1.getId(),user2.getId());

    }

    @Test
    @Rollback(true)
    public void testCheckUserPositif() throws Exception {
        System.out.println("Test CheckUser");

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setFirstName("test");
        user.setLastName("test");
        user.setPassword(PasswordUtil.sha1("mdp2511"));

        userDao.createUser(user);

        String email = "test@gmail.com";

        User user2 = userDao.checkUser(email, "mdp2511");

        Assert.assertEquals(user,user2);
    }

    @Test
    @Rollback(true)
    public void testCheckUserNegatif() throws Exception {
        System.out.println("Test CheckUser");

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setFirstName("test");
        user.setLastName("test");
        user.setPassword(PasswordUtil.sha1("mdp2511"));

        userDao.createUser(user);

        String email = "test@gmail.com";
        String pass = "mdp2512";

        User user2 = userDao.checkUser(email, pass);

        Assert.assertNotEquals(user,user2);
    }

    @Test
    @Rollback(true)
    public void testUpdateUserPositif() throws Exception {
        //creation d'un utilisateur
        User user = new User();
        user.setEmail("test1@gmail.com");
        user.setFirstName("test2");
        user.setLastName("test2");
        user.setPassword(PasswordUtil.sha1("mdp2511"));

        userDao.createUser(user);

        //affichage
        User user2 = userDao.getByMail("test1@gmail.com");
        System.out.println("nom : " +user2.getLastName()+ "  -  prenom : " +user2.getFirstName()+ " -  email : "+user2.getEmail());


        //modification d'un champ
        user.setFirstName("test1");
        userDao.updateUser(user);

        //affichage de l'user,le changement a été effectué (pb de cache)
        User user3 = userDao.getByMail("test1@gmail.com");
        System.out.println("nom : " +user3.getLastName()+ "  -  prenom : " +user3.getFirstName()+ " -  email : "+user3.getEmail());

    }

    @Test
    @Rollback(true)
    public void testUpdateUserNegatif() throws Exception {
        //creation d'un utilisateur
        User user = new User();
        user.setEmail("test3@gmail.com");
        user.setFirstName("test2");
        user.setLastName("test2");
        user.setPassword(PasswordUtil.sha1("mdp2511"));

        userDao.createUser(user);

        //affichage du nouvel utilisateur
        User user2 = userDao.getByMail("test3@gmail.com");
        System.out.println("nom : " +user2.getLastName()+ "  -  prenom : " +user2.getFirstName()+ " -  email : "+user2.getEmail());


        //on change son adresse mail par une adresse mail déjà utilisée par un autre utilisateur
        user.setFirstName("contact@jordan-marin.fr");
        userDao.updateUser(user);

        //affichage de l'user, le champ n'a pas été modifié en base (pb de cache 2nd niveau)
        User user3 = userDao.getByMail("test3@gmail.com");
        System.out.println("nom : " +user3.getLastName()+ "  -  prenom : " +user3.getFirstName()+ " -  email : "+user3.getEmail());

    }



    @Test
    @Rollback(true)
    public void testDeleteUserPositif() throws Exception {

        List<User> users3 = userDao.getAllUsers();

        for (User u : users3){
            System.out.println("email  : " + u.getEmail() + " nom : " + u.getLastName() + " prénom : "+ u.getFirstName());
        }

        //creation d'un user
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setFirstName("test");
        user.setLastName("test");
        user.setPassword(PasswordUtil.sha1("mdp2511"));

        userDao.createUser(user);

        //assertion de l'existence
        User user2 = userDao.getByMail("test@gmail.com");
        Assert.assertEquals(user, user2);

        //delete
        userDao.deleteUser(user);

        //assertion de l'inexistence
        User user3 = userDao.getByMail("test@gmail.com");

        Assert.assertEquals(null, user3);

        // on affiche pour montrer que l'utilisateur a disparu
        List<User> users = userDao.getAllUsers();
        for (User u : users){
            System.out.println("email  : " + u.getEmail() + " nom : " + u.getLastName() + " prénom : "+ u.getFirstName());
        }

    }

    @Test
    @Rollback(true)
    public void testDeleteUserNegatif() throws Exception {

        //creation d'un user
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setFirstName("test");
        user.setLastName("test");
        user.setPassword(PasswordUtil.sha1("mdp2511"));

        List<User> users = userDao.getAllUsers();

        for (User u : users){
            System.out.println("email  : " + u.getEmail() + " nom : " + u.getLastName() + " prénom : "+ u.getFirstName());
        }

        // on tente de supprimer un utilisateur qui n'est pas dans la base
        userDao.deleteUser(user);

        // on affiche pour montrer que rien n'a changé
        List<User> users2 = userDao.getAllUsers();

        for (User u : users2){
            System.out.println("email  : " + u.getEmail() + " nom : " + u.getLastName() + " prénom : "+ u.getFirstName());
        }

    }

    @Test
    @Rollback(true)
    public void testGetByMail() throws Exception {
        System.out.println("Test GetByMail");

        //creation d'un utilisateur de référence
        User user1 = new User();
        user1.setEmail("julessauvineto@yahoo.fr");
        user1.setFirstName("Jule");
        user1.setLastName("Sauvine");
        user1.setPassword("multim69");

        userDao.createUser(user1);

        String email = "julessauvineto@yahoo.fr";

        //on tente de retrouver l'utilisateur par l@ mail utilisée précédement
        User user2 = userDao.getByMail(email);

        Assert.assertTrue(user2.getId() > 0);
        Assert.assertEquals(email, user2.getEmail());

        // on s'assure que l'user inséré au début est le même que celui retrouvé
        Assert.assertEquals(user1,user2);
        System.out.println("Email : "+user2.getEmail());
    }

    @Test
    @Rollback(true)
    public void testGetById() throws Exception {

        System.out.println("Test GetById");

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setFirstName("test");
        user.setLastName("test");
        user.setPassword(PasswordUtil.sha1("mdp2511"));

        userDao.createUser(user);

        User user2 = userDao.getById(user.getId());

        Assert.assertEquals(user.getEmail(), user2.getEmail());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        System.out.println("Test GetAllUsers");

        List<User> users = userDao.getAllUsers();

        for (User u : users){
            System.out.println("email user : " + u.getEmail());
        }
    }
}