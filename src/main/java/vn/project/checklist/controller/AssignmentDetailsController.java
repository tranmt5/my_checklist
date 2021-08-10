package vn.project.checklist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.project.checklist.model.Assignment;
import vn.project.checklist.model.AssignmentDetails;
import vn.project.checklist.service.AssignmentDetailService;

import javax.servlet.http.HttpServletRequest;

@Controller

public class AssignmentDetailsController {

    @Autowired
    private AssignmentDetailService assignmentDetailService;

    @RequestMapping(value = "/assignmentDetails/sequence", method = RequestMethod.GET)
    @ResponseBody
    public String saveSequence(HttpServletRequest request, int id) {
        String[] assignmentDetailsId = request.getParameterValues("assignmentDetailsId[]");

        int [] index = new int[assignmentDetailsId.length];
        for (int i = 0; i < assignmentDetailsId.length; i++) {
            index[i] = Integer.parseInt(assignmentDetailsId[i]);
        }

        int j = 0;
        for (int i = 0; i < index.length; i++) {
            AssignmentDetails assignmentDetails1 = assignmentDetailService.getAssignmentDetailById(index[i]);
            assignmentDetails1.setSequence(j);
            assignmentDetailService.createAssignmentDetail(assignmentDetails1);
            j++;
        }

        return "success";
    }
}
