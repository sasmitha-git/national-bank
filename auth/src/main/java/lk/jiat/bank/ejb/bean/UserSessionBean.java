package lk.jiat.bank.ejb.bean;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.jiat.bank.core.model.User;
import lk.jiat.bank.core.service.UserService;

@Stateless
public class UserSessionBean implements UserService {

    @PersistenceContext
    private EntityManager em;


    @Override
    public User getUserById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User getUserByEmail(String email) {
        return em.createNamedQuery("User.findByEmail", User.class)
                .setParameter("email", email).getSingleResult();
    }

    @RolesAllowed({"ADMIN"})
    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    @RolesAllowed("ADMIN")
    @Override
    public void updateUser(User user) {
        em.merge(user);
    }

    @RolesAllowed("ADMIN")
    @Override
    public void deleteUser(User user) {
        em.remove(user);
    }

    @Override
    public boolean validate(String email, String password) {

        User u = em.createNamedQuery("User.findByEmailAndPassword", User.class)
                .setParameter("email",email)
                .setParameter("password",password)
                .getSingleResult();

        return u!=null;
    }
}
