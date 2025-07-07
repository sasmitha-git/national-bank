package lk.jiat.bank.ejb.bean;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
    public void deactivateUser(Long id) {
        User user = em.find(User.class, id);
        if(user != null) {
            user.setActive(false);
            em.merge(user);
        }
    }


    @RolesAllowed({"ADMIN","CUSTOMER"})
    @Override
    public boolean isActiveUser(String email) {
        try {
            User user = em.createNamedQuery("User.findByEmail",User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return user.isActive();
        }catch (NoResultException e){
            return false;
        }
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
