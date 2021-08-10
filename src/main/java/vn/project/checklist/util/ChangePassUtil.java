package vn.project.checklist.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ChangePassUtil {
    public static void changePass() {
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
        System.out.println(encoder.encode("12345"));
    }
}
