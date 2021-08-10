package vn.project.checklist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vn.project.checklist.util.ChangePassUtil;

@SpringBootApplication

public class ChecklistApplication {

	public static void main(String[] args) {

		SpringApplication.run(ChecklistApplication.class, args);
//		ChangePassUtil.changePass();
	}

}
