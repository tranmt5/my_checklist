package vn.project.checklist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/403").setViewName("error/403");
        registry.addViewController("/homePage").setViewName("homePage");
        registry.addViewController("/checklist_assignee").setViewName("checklist_assignee");
    }

//   .............allow access folder user-avatar..............................
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        Path avatarUploadDir = Paths.get("./user-avatars");
//        String avatarUploadPath = avatarUploadDir.toFile().getAbsolutePath();
//        registry.addResourceHandler("/user-avatars/**").addResourceLocations("file:/" + avatarUploadPath + "/");
//    }
}
