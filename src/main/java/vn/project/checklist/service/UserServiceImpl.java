package vn.project.checklist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.project.checklist.model.User;
import vn.project.checklist.repository.UserRepo;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public List<User> getListUser() {
        return userRepo.findAll();
    }

    @Override
    public Page<User> getListUserByUsername(String keyword, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber-1,20);
        if(keyword != null) {
            return userRepo.findAll(keyword,pageable);
        }
        return userRepo.findAll(pageable);
    }

    @Override
    public User createOrEditUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
        return user;
    }

    @Override
    public User getUserById(int id) {
        return userRepo.findById(id).get();
    }

    @Override
    public void deleteUser(int id) {
        userRepo.deleteById(id);
    }

    @Override
    public boolean userExists(String username) {
       User user = userRepo.findByUsername(username);
       if(user != null) {
           return true;
       }
       return false;
    }

    public void updatePassword(String newPassword, User user, PasswordEncoder passwordEncoder) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setPasswordChangeTime(new Date());
        userRepo.save(user);
    }
}
