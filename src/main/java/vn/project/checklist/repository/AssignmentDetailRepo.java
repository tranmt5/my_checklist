package vn.project.checklist.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import vn.project.checklist.model.AssignmentDetails;

import java.util.List;

@Repository
public interface AssignmentDetailRepo extends JpaRepository <AssignmentDetails, Integer>, PagingAndSortingRepository<AssignmentDetails, Integer> {
    List<AssignmentDetails> findListAssignmentDetailsByAssignmentId(int id,Sort sort);
}
