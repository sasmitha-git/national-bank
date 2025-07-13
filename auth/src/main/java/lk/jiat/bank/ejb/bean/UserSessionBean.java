package lk.jiat.bank.ejb.bean;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lk.jiat.bank.core.dto.UserDTO;
import lk.jiat.bank.core.model.User;
import lk.jiat.bank.core.model.UserRole;
import lk.jiat.bank.core.service.UserService;

import java.util.ArrayList;
import java.util.List;

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
      try{
          return em.createNamedQuery("User.findByEmail", User.class)
                  .setParameter("email", email).getSingleResult();
      }catch (NoResultException e){
          return null;
      }
    }

    @RolesAllowed({"ADMIN"})
    @Override
    public List<UserDTO> getAllCustomers() {
       List<User> users =  em.createNamedQuery("User.findByUserRole", User.class)
                .setParameter("role", UserRole.CUSTOMER)
                .getResultList();

       List<UserDTO> userDTOs = new ArrayList<>();

       for (User user : users) {
           UserDTO dto = new UserDTO();
           dto.setId(user.getId());
           dto.setFullName(user.getFullName());
           dto.setEmail(user.getEmail());
           dto.setPhone(user.getPhone());
           dto.setUserRole(user.getUserRole());
           dto.setActive(user.isActive());

           userDTOs.add(dto);

       }

        return userDTOs;
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

    @PermitAll
    @Override
    public void passwordUpdate(Long id, String newPassword) {
        User user = em.find(User.class, id);
        if(user != null) {
            user.setPassword(newPassword);
            em.merge(user);
        }
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


    @PermitAll
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

        try {
            User u = em.createNamedQuery("User.findByEmailAndPassword", User.class)
                    .setParameter("email",email)
                    .setParameter("password",password)
                    .getSingleResult();

            return u!=null;

        }catch (NoResultException e){
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
