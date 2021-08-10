package vn.project.checklist.controller;

//import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import net.sf.json.JSONArray;
//import org.json.JSONArray;
//import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.project.checklist.model.Role;
import vn.project.checklist.model.User;
import vn.project.checklist.service.RoleService;
import vn.project.checklist.service.UserServiceImpl;
import vn.project.checklist.util.FileUploadUtil;
import org.apache.commons.codec.binary.Base64;


import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/setting/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleService roleService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }

    @GetMapping("/")
    public String listUser(Model model,@Param("keyword") String keyword) {
        return listUserByPage(model,keyword,1);
    }

    @GetMapping("/page/{pageNumber}")
    public String listUserByPage(Model model,@Param("keyword") String keyword,@PathVariable("pageNumber") int currentPage) {
        Page<User> page = userService.getListUserByUsername(keyword, currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        List<User> listUsers = page.getContent();
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("listUsers", listUsers);
        return "user_list";
    }

    @RequestMapping("/add")
    public String addUserPage(Model model) {
        model.addAttribute("user", new User());
        return "user_add";
    }

    @PostMapping("/save")
    public String createUser(@ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(userService.userExists(user.getUsername())) {
            bindingResult.addError(new FieldError("user","username","User already in use"));
        }

        if(user.getPassword() != null && user.getPasswordConfirm() != null) {
            if(!user.getPassword().equals(user.getPasswordConfirm())){
                bindingResult.addError(new FieldError("user","passwordConfirm","Password must match!"));
            }
        }
        if(bindingResult.hasErrors()) {
            return "user_add";
        }
        user.setPasswordChangeTime(new Date());
        userService.createOrEditUser(user);
        redirectAttributes.addFlashAttribute("addUserMessage","The user has been saved successfully!");
        return "redirect:/setting/user/add";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editUser(@PathVariable(name = "id") int id, Model model) {
        ModelAndView mav = new ModelAndView("user_edit");
        User user = userService.getUserById(id);
        user.setBase64Img("data:image/png;base64," + Base64.encodeBase64String(user.getAvatar()));

        mav.addObject("user", user);

        List<Role> listRoles = roleService.getListRole();
        model.addAttribute("listRoles", listRoles);
        return mav;
    }

//    @RequestMapping(value = "/edit/", method = RequestMethod.GET)
//    @ResponseBody
//    public  ArrayList getRoleNamesList(@RequestParam String keyword) {
//        List<Role> roles = roleService.getRolesByQuery(keyword);
//        ArrayList roleNames = new ArrayList();
//        for(Role role : roles) {
//            String name = role.getName();
//            roleNames.add(name);
//        }
//        return roleNames;
//    }

    @RequestMapping(value = "/edit/", method = RequestMethod.GET)
    @ResponseBody
    public  ArrayList getRoleNamesList(@RequestParam String keyword) {
        List<Role> roles = roleService.getRolesByQuery(keyword);
        ArrayList roleNames = new ArrayList<>();
        for(Role role : roles) {
            roleNames.add(role);
        }
        return roleNames;
    }

    @PostMapping("/saveEdit")
    public String editUser (@ModelAttribute("user") User user, RedirectAttributes redirectAttributes, @RequestParam("fileImage")MultipartFile multipartFile) throws IOException {
//        String fileName;
//        String oldFileName = user.getAvatar();
//        String newFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//
//        if (!newFileName.isEmpty()) {
//            String uploadDir = "./user-avatars/" + user.getId();
//            FileUploadUtil.saveFile(uploadDir,multipartFile,newFileName);
//            fileName = newFileName;
//        } else {
//            fileName = oldFileName;
//        }
//        user.setAvatar(fileName);
        user.setAvatar(multipartFile.getBytes());
        user.setNameAvatar(multipartFile.getOriginalFilename());
        user.setTypeAvatar(multipartFile.getContentType());
        user.setPasswordChangeTime(new Date());
        userService.createOrEditUser(user);

        redirectAttributes.addFlashAttribute("editMessage", "The user has been edited successfully!");
        return "redirect:/setting/user/";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes){
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("deleteMessage", "The user has been deleted successfully!");
        return "redirect:/setting/user/";
    }

}
