package vn.project.checklist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.project.checklist.model.*;
import vn.project.checklist.service.*;

import java.util.List;

@Controller
@RequestMapping("/checklist")
public class ChecklistController {
    @Autowired
    private ChecklistService checklistService;

    @Autowired
    private ChecklistItemService checklistItemService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }

    @GetMapping("/")
    public String listChecklist(Model model, @Param("keyword") String keyword) {
        return listChecklistByPage(model, keyword,1);
    }

    @GetMapping("/page/{pageNumber}")
    public String listChecklistByPage(Model model,@Param("keyword") String keyword,@PathVariable("pageNumber") int currentPage) {
        Page<Checklist> page = checklistService.getListChecklistByName(keyword,currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        List<Checklist> listChecklists = page.getContent();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("listChecklists", listChecklists);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        return "checklist_list";
    }

    @GetMapping("/create")
    public String createChecklist(Model model) {
        model.addAttribute("checklist", new Checklist());
        return "checklist_create";
    }

    @PostMapping("/save")
    public String createChecklist(@ModelAttribute("checklist") Checklist checklist, RedirectAttributes redirectAttributes) {
        checklistService.createChecklist(checklist);
        List<ChecklistItem> checklistItems = checklist.getChecklistItems();
        for(ChecklistItem checklistItem : checklistItems) {
            checklistItem.setChecklist(checklist);
            checklistItemService.createChecklistItem(checklistItem);
        }
        redirectAttributes.addFlashAttribute("createdChecklistMessage","The checklist has been saved successfully!");
        return "redirect:/checklist/create";
    }

    @PostMapping("/saveEdit")
    public String editChecklist(@ModelAttribute("checklist") Checklist checklist, RedirectAttributes redirectAttributes) {
        checklistService.createChecklist(checklist);
        List<ChecklistItem> checklistItems = checklist.getChecklistItems();
        for(ChecklistItem checklistItem : checklistItems) {
            checklistItem.setChecklist(checklist);
            checklistItemService.createChecklistItem(checklistItem);
        }
        redirectAttributes.addFlashAttribute("editMessage","The checklist has been edited successfully!");
        return "redirect:/checklist/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editChecklist(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("checklist_edit");
        Checklist checklist = checklistService.getChecklistById(id);
        List<ChecklistItem> checklistItems = checklistItemService.getListChecklistItemByChecklistId(id);
        mav.addObject("checklist", checklist);
        mav.addObject("checklistItems",checklistItems);
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteChecklist(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes) {
        checklistService.deleteChecklist(id);
        redirectAttributes.addFlashAttribute("deleteMessage","The checklist has been deleted successfully!");
        return "redirect:/checklist/";
    }

    @RequestMapping("/item/delete/{id}")
    public String delete(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes){
        ChecklistItem checklistItem = checklistItemService.getChecklistItemById(id);
        int index = checklistItem.getChecklist().getId();

        checklistItemService.deleteChecklistItem(id);
        redirectAttributes.addFlashAttribute("deleteItemMessage","The ChecklistItem has been deleted successfully!");
        return "redirect:/checklist/edit/" + index;
    }

}
