package vn.project.checklist    .repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.project.checklist.model.User;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository <User, Integer>, PagingAndSortingRepository <User, Integer> {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username LIKE %?1%")
    Page<User> findAll(String keyword, Pageable pageable);
}
