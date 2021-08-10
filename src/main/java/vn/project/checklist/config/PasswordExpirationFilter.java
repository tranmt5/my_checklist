package vn.project.checklist.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import vn.project.checklist.model.User;
import vn.project.checklist.service.MyUserDetails;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class PasswordExpirationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURL = httpServletRequest.getRequestURL().toString();

        if(requestURL.endsWith(".png") || requestURL.endsWith(".jpg") || requestURL.endsWith(".css") || requestURL.endsWith(".js") || requestURL.endsWith("/change_password_expired") || requestURL.endsWith("/changePassword")) {
            filterChain.doFilter(httpServletRequest, servletResponse);
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = null;
        if (authentication != null) {
            principal = authentication.getPrincipal();
        }

        if (principal != null && principal instanceof MyUserDetails) {
            MyUserDetails myUserDetails = (MyUserDetails) principal;
            User user = myUserDetails.getUser();
            if (user.isPasswordExpired()) {
//                System.out.println("user " + user.getUsername() + ": Password expired");
//                System.out.println("Last changed password " + user.getPasswordChangeTime());
//                System.out.println("Current time: " + new Date());
                HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
                String redirectURL = httpServletRequest.getContextPath() + "/change_password_expired";
                httpServletResponse.sendRedirect(redirectURL);

            } else {
//                System.out.println("user " + user.getUsername() + ": Password not expired");
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
