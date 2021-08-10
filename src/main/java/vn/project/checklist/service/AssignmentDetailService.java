package vn.project.checklist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.project.checklist.model.AssignmentDetails;
import vn.project.checklist.repository.AssignmentDetailRepo;

import java.util.List;

@Service
public class AssignmentDetailService {
    @Autowired
    AssignmentDetailRepo assignmentDetailRepo;
    public List<AssignmentDetails> getAssignmentDetail() {

        return assignmentDetailRepo.findAll();
    }

    public List<AssignmentDetails> getListAssignmentDetailsByAssignmentId(int id) {
        Sort sort = Sort.by("sequence").ascending();
        return assignmentDetailRepo.findListAssignmentDetailsByAssignmentId(id,sort);
    }


    public void createAssignmentDetail(AssignmentDetails assignmentDetails) {

        assignmentDetailRepo.save(assignmentDetails);
    }

    public AssignmentDetails getAssignmentDetailById(int id) {
        return assignmentDetailRepo.findById(id).get();
    }

}
