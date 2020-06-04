package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.User;
import net.slipp.domain.UserRepository;

@Controller
@RequestMapping("/users")  // 중복제거 , 아래 메소드에 있는걸 공용으로 사용할때 사용 ..
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		System.out.println("aa userId" + userId);
		System.out.println("aa password" + password);
		User user = userRepository.findByUserId(userId);
		if (user == null) {
			return "redirect:/users/loginForm";
		}
		if ( !password.equals(user.getPassword())) {
			return "redirect:/users/loginForm";
		}

		session.setAttribute("user",  user);
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout( HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/";
	}
	
	@PostMapping("")
	public String create(User user) {
		System.out.println("aa userId" + user);
		userRepository.save(user);
		return "redirect:/users";
	}
	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model) {
		User user = userRepository.findById(id).orElse(new User());
		model.addAttribute("user",user);
		System.out.println("updateForm user : " +  id);
		System.out.println("updateForm user : " +  user);
		return "/user/updateForm";
	}
	
	@PostMapping("/{id}")
	public String update(@PathVariable Long id, User updateUser) {
		User user =  userRepository.findById(id).orElse(new User());
		user.update(updateUser);
		userRepository.save(user);
		return "redirect:/users";
	}
}
