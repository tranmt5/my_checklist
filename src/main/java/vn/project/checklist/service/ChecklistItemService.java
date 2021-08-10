package vn.project.checklist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.project.checklist.model.ChecklistItem;
import vn.project.checklist.repository.ChecklistItemRepo;

import java.util.*;

@Service
public class ChecklistItemService {
    @Autowired
    private ChecklistItemRepo checklistItemRepo;

    public List<ChecklistItem> getChecklistItem() {
        return checklistItemRepo.findAll();
    }

    public List<ChecklistItem> getListChecklistItemByChecklistId(int id) {
        return checklistItemRepo.findListChecklistItemByChecklistId(id);
    }


    public void createChecklistItem(ChecklistItem checklistItem) {

        checklistItemRepo.save(checklistItem);
    }

    public ChecklistItem getChecklistItemById(int id) {
        return checklistItemRepo.findById(id).get();
    }

    public void deleteChecklistItem(int id) {
        checklistItemRepo.deleteById(id);
    }

}
