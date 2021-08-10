package vn.project.checklist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.project.checklist.model.Role;
import vn.project.checklist.service.RoleService;

import java.util.List;

@Controller
@RequestMapping("/setting/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/save")
    public String createRole(@ModelAttribute("role") Role role, RedirectAttributes redirectAttributes) {
        roleService.createRole(role);
        redirectAttributes.addFlashAttribute("message","The role has been saved successfully!");
        return "redirect:/setting/role/";
    }

    @PostMapping("/saveEdit")
    public String editRole(@ModelAttribute("role") Role role, RedirectAttributes redirectAttributes) {
        roleService.createRole(role);
        redirectAttributes.addFlashAttribute("editMessage","The role has been edited successfully!");
        return "redirect:/setting/role/";
    }

    @GetMapping("/")
    public String listRole(Model model) {
        List<Role> roles = roleService.getListRole();
        model.addAttribute("roles",roles);
        model.addAttribute("role", new Role());
        return "role_list";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editRole(@PathVariable(name="id") int id) {
        ModelAndView mav = new ModelAndView("role_edit");
        Role  role = roleService.getRoleById(id);
        mav.addObject("role", role);
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes){
        roleService.deleteRoleById(id);
        redirectAttributes.addFlashAttribute("deleteMessage","The role has been deleted successfully!");
        return "redirect:/setting/role/";
    }

}
