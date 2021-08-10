package vn.project.checklist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.project.checklist.model.Assignment;
import vn.project.checklist.model.AssignmentDetails;
import vn.project.checklist.repository.AssignmentRepo;

import java.util.List;

@Service
public class AssignmentService {
    @Autowired
    AssignmentRepo assignmentRepo;
    public List<Assignment> getListAssignment() {
        return assignmentRepo.findAll();
    }

    public Page<Assignment> getListAssignmentPage(int pageNumber) {
        Sort sort = Sort.by("status").ascending();
        String keyword = SecurityContextHolder.getContext().getAuthentication().getName();
        Pageable pageable = PageRequest.of(pageNumber-1, 5,sort);
        return assignmentRepo.findAll(keyword,pageable);
    }

    public Page<Assignment> getListAssignmentPageAdmin(String keyword, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber-1, 20);
        if (keyword != null) {
            return assignmentRepo.findAll(keyword,pageable);
        }
        return assignmentRepo.findAll(pageable);
    }

    public void createAssignment(Assignment assignment) {
        assignmentRepo.save(assignment);
    }

    public Assignment getAssignmentById(int id) {
        return assignmentRepo.findById(id).get();
    }

    public void deleteAssignmentById(int id) {
        assignmentRepo.deleteById(id);
    }

}
