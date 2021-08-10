package vn.project.checklist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import vn.project.checklist.model.Assignment;

@Repository
public interface AssignmentRepo extends JpaRepository <Assignment, Integer>, PagingAndSortingRepository<Assignment, Integer> {

    @Query("SELECT u FROM Assignment u WHERE u.user.username LIKE %?1%")
    Page<Assignment> findAll(String keyword, Pageable pageable);
}
