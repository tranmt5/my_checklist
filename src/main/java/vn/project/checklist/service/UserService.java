package vn.project.checklist.service;

import org.springframework.data.domain.Page;
import vn.project.checklist.model.User;

import java.util.List;

public interface UserService {

    List<User> getListUser();
    Page<User> getListUserByUsername(String keyword,int pageNumber);
    User createOrEditUser(User user);
    User getUserById(int id);
    void deleteUser(int id);
    boolean userExists(String username);
}
