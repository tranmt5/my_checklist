package vn.project.checklist.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.project.checklist.model.User;
import vn.project.checklist.repository.UserRepo;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<User> {
    @Autowired
    private UserRepo userRepo;

    @Override
    public Optional<User> getCurrentAuditor() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return Optional.ofNullable(userRepo.findByUsername(username));
    }
}
