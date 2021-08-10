package vn.project.checklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.project.checklist.model.Role;

import java.util.List;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

    @Query("SELECT u FROM Role u WHERE u.name LIKE %?1%")
    List<Role> findAllByName (String name);
}
