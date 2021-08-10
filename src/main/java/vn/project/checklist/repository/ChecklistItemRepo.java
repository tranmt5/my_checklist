package vn.project.checklist.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import vn.project.checklist.model.ChecklistItem;

import java.util.List;

@Repository
public interface ChecklistItemRepo extends JpaRepository<ChecklistItem, Integer> {
    List<ChecklistItem> findListChecklistItemByChecklistId(int id);
}
