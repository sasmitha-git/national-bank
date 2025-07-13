package lk.jiat.bank.core.service;

import jakarta.ejb.Remote;
import lk.jiat.bank.core.dto.UserDTO;
import lk.jiat.bank.core.model.User;

import java.util.List;

@Remote
public interface UserService {

    User getUserById(Long id);
    User getUserByEmail(String email);

    List<UserDTO> getAllCustomers();
    void addUser(User user);
    void updateUser(User user);
    void passwordUpdate(Long id, String newPassword);
    void deactivateUser(Long id);
    boolean isActiveUser(String email);

    boolean validate(String email, String password);

}
