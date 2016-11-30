package fr.univlyon1.sem.dao.impl;

import fr.univlyon1.sem.dao.AbstractDao;
import fr.univlyon1.sem.dao.UserDao;
import fr.univlyon1.sem.model.relationnal.User;
import fr.univlyon1.sem.utils.PasswordUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao implements UserDao {

    @Override
    @Transactional
    public User createUser(User user) {
        persist(user);
        return user;
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        merge(user);
        return user;
    }

    @Override
    @Transactional
    public User deleteUser(User user) {
        delete(user);
        getSession().flush();
        return user;
    }

    @Override
    @Transactional
    public User checkUser(String email, String password) {
        User u = getByMail(email);
        if (u != null && u.getPassword().equals(PasswordUtil.sha1(password))) {
            return u;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public User getByMail(String email) {

        Criteria cr = getSession().createCriteria(User.class);
        cr.add(Restrictions.eq("email", email));
        return (User) cr.uniqueResult();
    }

    @Override
    @Transactional
    public User getById(int id) {
        Criteria cr = getSession().createCriteria(User.class);
        cr.add(Restrictions.eq("id", id));
        return (User) cr.uniqueResult();
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        Criteria cr = getSession().createCriteria(User.class);
        List<User> listUsers = cr.list();
        return listUsers;
    }

    @Override
    @Transactional
    public User getByFbId(String fb_id) {
        Criteria cr = getSession().createCriteria(User.class);
        cr.add(Restrictions.eq("fb_id", fb_id));
        return (User) cr.uniqueResult();
    }
}
