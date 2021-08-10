package vn.project.checklist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import vn.project.checklist.model.Checklist;

@Repository
public interface ChecklistRepo extends JpaRepository<Checklist, Integer>, PagingAndSortingRepository<Checklist, Integer> {

    @Query ("SELECT c FROM Checklist c WHERE c.name LIKE %?1%")
    Page<Checklist> findAll(String keyword, Pageable pageable);

}
