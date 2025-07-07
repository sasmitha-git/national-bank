package lk.jiat.bank.core.service;

import jakarta.ejb.Remote;
import lk.jiat.bank.core.model.User;

@Remote
public interface UserService {

    User getUserById(Long id);
    User getUserByEmail(String email);
    void addUser(User user);
    void updateUser(User user);
    void deactivateUser(Long id);
    boolean isActiveUser(String email);

    boolean validate(String email, String password);

}
