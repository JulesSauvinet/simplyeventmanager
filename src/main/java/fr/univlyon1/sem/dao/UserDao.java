package fr.univlyon1.sem.dao;

import fr.univlyon1.sem.model.relationnal.User;

import java.util.List;

public interface UserDao {
    User createUser(User user);
    User updateUser(User user);
    User deleteUser(User user);
    User checkUser(String email, String password);
    User getByMail(String email);
    User getById (int id);
    List<User> getAllUsers();
    User getByFbId(String userId);
}
