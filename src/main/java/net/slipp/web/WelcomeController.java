package net.slipp.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	@GetMapping("/helloworld")
	public String welcome( Model model) {
		System.out.println(" this is welcome ");
		List<MyModel> repo = Arrays.asList(new MyModel("kein"),new MyModel("kein2"));
		model.addAttribute("repo", repo);
		return "welcome";
	}
}
