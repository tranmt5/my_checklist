package vn.project.checklist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.project.checklist.model.*;
import vn.project.checklist.service.AssignmentDetailService;
import vn.project.checklist.service.AssignmentService;
import vn.project.checklist.service.ChecklistService;
import vn.project.checklist.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/assignment")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    AssignmentDetailService assignmentDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChecklistService checklistService;


    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }

    @RequestMapping("/assign/{id}")
    public ModelAndView assignChecklist(@PathVariable(name = "id") int id, Model model) {
        List<User> listUser = userService.getListUser();
        model.addAttribute("listUser",listUser);

        model.addAttribute("assignment", new Assignment());
        ModelAndView mav = new ModelAndView("assignment_assign");
        Checklist checklist = checklistService.getChecklistById(id);
        mav.addObject("checklist", checklist);
        return mav;
    }

    @PostMapping("/save")
    public String createAssignment(@ModelAttribute("assignment") Assignment assignment, RedirectAttributes redirectAttributes) {
        assignment.setStatus(0);
        assignment.setProgress(0);
        assignmentService.createAssignment(assignment);
        Checklist checklist = assignment.getChecklist();
        List<ChecklistItem> checklistItems = checklist.getChecklistItems();
        int sequence = 0;
        for (ChecklistItem checklistItem : checklistItems) {
            AssignmentDetails assignmentDetails = new AssignmentDetails();
            assignmentDetails.setAssignment(assignment);
            assignmentDetails.setChecklistItem(checklistItem);
            assignmentDetails.setStatus(0);
            assignmentDetails.setSequence(sequence);
            assignmentDetailService.createAssignmentDetail(assignmentDetails);
            sequence++;
        }
        redirectAttributes.addFlashAttribute("messageAssignment","The assignment has been saved successfully!");
        return "redirect:/checklist/";
    }

    @GetMapping("/")
    public String assignment(Model model, @Param("keyword") String keyword) {
        return listAssignmentPage(model,keyword,1);
    }

    @GetMapping("/page/{pageNumber}")
    public String listAssignmentPage(Model model, @Param("keyword") String keyword, @PathVariable("pageNumber") int currentPage) {
        Page<Assignment> page = assignmentService.getListAssignmentPage(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        List<Assignment> listAssignments = page.getContent();
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("listAssignments",listAssignments);
        return "assignment_list";
    }

    @GetMapping("/pageAdmin")
    public String assignChecklistPageAdmin(Model model, @Param("keyword") String keyword) {
        return listAssignmentPageAdmin(model,keyword,1);
    }

    @GetMapping("/pageAdmin/{pageNumber}")
    public String listAssignmentPageAdmin(Model model, @Param("keyword") String keyword, @PathVariable("pageNumber") int currentPage) {
        Page<Assignment> page = assignmentService.getListAssignmentPageAdmin(keyword,currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        List<Assignment> listAssignments = page.getContent();
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("listAssignments",listAssignments);
        return "assignment_listAdmin";
    }

    @RequestMapping("/delete/{id}")
    public String deleteAssignment(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes) {
        assignmentService.deleteAssignmentById(id);
        redirectAttributes.addFlashAttribute("deleteMessage","The assignment has been deleted successfully!");
        return "redirect:/assignment/pageAdmin";
    }

    @RequestMapping("/action/{id}")
    public String actionChecklist(@PathVariable(name = "id") int id, Model model) {
        Assignment assignment = assignmentService.getAssignmentById(id);
        //list  sort by sequence
        List<AssignmentDetails> assignmentDetails = assignmentDetailService.getListAssignmentDetailsByAssignmentId(id);
        model.addAttribute("assignment",assignment);
        model.addAttribute("assignmentDetails",assignmentDetails);
        return "assignment_action";
    }

    @RequestMapping("/statusItem/completed/{id}")
    public String changeStatusItemOn(@PathVariable(name = "id") int id, Model model) {
        AssignmentDetails assignmentDetails = assignmentDetailService.getAssignmentDetailById(id);
        assignmentDetails.setStatus(1);
        assignmentDetails.getAssignment().setStatus(1);
        int index = assignmentDetails.getAssignment().getId();
        assignmentDetailService.createAssignmentDetail(assignmentDetails);

        Assignment assignment = assignmentDetails.getAssignment();
        Checklist checklist = assignment.getChecklist();
        List<ChecklistItem> checklistItems = checklist.getChecklistItems();
        List<AssignmentDetails> assignmentDetails1 = assignment.getAssignmentDetails();

        int sum = 0;
        for (AssignmentDetails assignmentDetails2 :assignmentDetails1) {
            sum += assignmentDetails2.getStatus();
        }

        int size = checklistItems.size();
        if (size == sum) {
            assignment.setStatus(2);
        }
        int percent = sum * 100/size;
        assignment.setProgress(percent);
        assignmentService.createAssignment(assignment);
        model.addAttribute("assignmentDetails",assignmentDetails);
        return "redirect:/assignment/action/" + index;
    }

    @RequestMapping("/statusItem/cancelled/{id}")
    public String changeStatusItemOff(@PathVariable(name = "id") int id, Model model) {
        AssignmentDetails assignmentDetails = assignmentDetailService.getAssignmentDetailById(id);
        assignmentDetails.setStatus(0);
        int index = assignmentDetails.getAssignment().getId();
        assignmentDetailService.createAssignmentDetail(assignmentDetails);

        Assignment assignment = assignmentDetails.getAssignment();
        Checklist checklist = assignment.getChecklist();
        List<ChecklistItem> checklistItems = checklist.getChecklistItems();
        List<AssignmentDetails> assignmentDetails1 = assignment.getAssignmentDetails();

        int sum = 0;
        for (AssignmentDetails assignmentDetails2 :assignmentDetails1) {
            sum += assignmentDetails2.getStatus();
        }

        int size = checklistItems.size();
        if (sum == 0) {
            assignment.setStatus(0);
        } else if (sum == size ) {
            assignment.setStatus(2);
        } else {
            assignment.setStatus(1);
        }
        int percent = sum * 100/size;
        assignment.setProgress(percent);
        assignmentService.createAssignment(assignment);
        model.addAttribute("assignmentDetails",assignmentDetails);
        return "redirect:/assignment/action/" + index;
    }

    @PostMapping("action/cancel")
    public String cancelAssignment(@ModelAttribute("assignment") Assignment assignment,RedirectAttributes redirectAttributes) {
        assignment.setStatus(3);
        assignmentService.createAssignment(assignment);
        redirectAttributes.addFlashAttribute("cancelledAssignment", "The Assignment has been cancelled successfully!");
        return "redirect:/assignment/";
    }

    @GetMapping("/detail/{id}")
    public String assignmentDetailPage(@PathVariable(name = "id") int id, Model model) {
        Assignment assignment = assignmentService.getAssignmentById(id);
        Checklist checklist = assignment.getChecklist();
        List<ChecklistItem> checklistItems = checklist.getChecklistItems();
        List<AssignmentDetails> assignmentDetails = assignmentDetailService.getListAssignmentDetailsByAssignmentId(id);
        int size = checklistItems.size();
        int sum =0;
        for(AssignmentDetails assignmentDetails1 : assignmentDetails) {
            sum += assignmentDetails1.getStatus();
        }
        String ratio = sum + "/" + size;
        model.addAttribute(assignment);
        model.addAttribute("assignmentDetails",assignmentDetails);
        model.addAttribute("ratio",ratio);
        return "Assignment_detail";
    }

    @PostMapping("/detail/save")
    public String saveStatusAssignment(@ModelAttribute("assignment") Assignment assignment,RedirectAttributes redirectAttributes) {
        assignmentService.createAssignment(assignment);
        int index = assignment.getId();
        redirectAttributes.addFlashAttribute("changeStatusAssignment", "The Assignment status has been changed successfully!");
        return "redirect:/assignment/detail/" + index;
    }

}
