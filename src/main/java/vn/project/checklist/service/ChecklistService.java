package vn.project.checklist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.project.checklist.model.Checklist;
import vn.project.checklist.repository.ChecklistRepo;

import java.util.List;

@Service
public class ChecklistService {
    @Autowired
    ChecklistRepo checklistRepo;

    public List<Checklist> getListChecklist() {
        return checklistRepo.findAll();
    }

    public Page<Checklist> getListChecklistByName(String keyword, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber -1, 20);
        if(keyword != null) {
            return checklistRepo.findAll(keyword,pageable);
        }
        return checklistRepo.findAll(pageable);
    }

    public void createChecklist(Checklist checklist) {
        checklistRepo.save(checklist);
    }

    public Checklist getChecklistById(int id) {
        return checklistRepo.findById(id).get();
    }

    public void deleteChecklist(int id) {
        checklistRepo.deleteById(id);
    }
}
