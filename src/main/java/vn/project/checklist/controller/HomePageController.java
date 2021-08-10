package vn.project.checklist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {

    @RequestMapping("/homePage")
    public String homePage() {
        return "homePage";
    }
}
