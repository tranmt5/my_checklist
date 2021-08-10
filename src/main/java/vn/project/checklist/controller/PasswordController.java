package vn.project.checklist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import vn.project.checklist.model.User;
import vn.project.checklist.service.MyUserDetails;
import vn.project.checklist.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class PasswordController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/change_password_expired")
    public String showChangePasswordExpiredPage(Model model) {
        model.addAttribute("titleChangePassword", "Change Your Expired Password");
        model.addAttribute("msg", "Change Your Expired Password");
        return "change_password";
    }

    @PostMapping("/changePassword")
    public String processChangePassword(Model model, HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        User user = myUserDetails.getUser();

        String oldPassword = httpServletRequest.getParameter("oldPassword");
        String newPassword = httpServletRequest.getParameter("newPassword");
        String confirmPassword = httpServletRequest.getParameter("confirmPassword");

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            model.addAttribute("titleChangePassword", "Change Your Password");
            model.addAttribute("msg", "Something wrong! Please try again!");
            model.addAttribute("newMessage", "Your old password is incorrect!");
            return "change_password";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("titleChangePassword", "Change Your Password");
            model.addAttribute("msg", "Something wrong! Please try again!");
            model.addAttribute("newMessage", "New password not match!");
            return "change_password";
        }

        if (oldPassword.equals(newPassword)) {
            model.addAttribute("titleChangePassword", "Change Your Password");
            model.addAttribute("msg", "Something wrong! Please try again!");
            model.addAttribute("newMessage", "Your new password must be different than the old one!");
            return "change_password";
        }

        userService.updatePassword(newPassword, user, passwordEncoder);

        return "redirect:/login?logout";
    }


    @GetMapping("/setting/change_password")
    public String showChangePasswordPage(Model model) {
        model.addAttribute("titleChangePassword", "Change Your Password");
        model.addAttribute("msg", "Change Your Password");
        return "change_password";
    }
}
