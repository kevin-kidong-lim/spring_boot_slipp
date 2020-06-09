package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.Result;
import net.slipp.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping("/form")
	public String form(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		return "/qna/form";
	}
	
	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser, title, contents);
		questionRepository.save(newQuestion);
		
		return "redirect:/";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model, HttpSession session) {
//		Question question = questionRepository.findById(id).orElse(new Question());
		model.addAttribute("question",  questionRepository.findById(id).orElse(new Question()));
		return "/qna/show";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		Question question = questionRepository.findById(id).orElse(new Question());	
		Result result = valid(session, question);
		if ( !result.isValid()) {
			model.addAttribute("errorMessage", result.getMessage());
			return "/user/login";
		}
		model.addAttribute("question",  question);
		return "/qna/updateForm";
		/*
		 * try { hasPermission(session, question);
		 *  model.addAttribute("question", question); 
		 *  return "/qna/updateForm"; } 
		 *  catch (IllegalStateException e) {
		 * model.addAttribute("errorMessage", e.getMessage()); return "/user/login"; }
		 */
	}
	
	private Result valid(HttpSession session, Question question) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail(" Please login [0]");
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if ( !question.isSameWriter(loginUser)) {
			return Result.fail(" No write user [1]");
		}
		return Result.ok();
	}
	
//	private boolean hasPermission(HttpSession session, Question question) {
//		if (!HttpSessionUtils.isLoginUser(session)) {
//			throw new IllegalStateException(" Please login [0]");
//		}
//		User loginUser = HttpSessionUtils.getUserFromSession(session);
//		if ( !question.isSameWriter(loginUser)) {
//			throw new IllegalStateException(" No write user [1]");
//		}
//		return false;
//	}
	@PostMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
		Question question = questionRepository.findById(id).orElse(new Question());	
		Result result = valid(session, question);
		if ( !result.isValid()) {
			model.addAttribute("errorMessage", result.getMessage());
			return "/user/login";
		}
		question.update(title, contents);
		questionRepository.save(question);
		return String.format("redirect:/questions/%d", id);
//		
//		try {
//			Question question = questionRepository.findById(id).orElse(new Question());	
//			hasPermission(session, question);
//			question.update(title, contents);
//			questionRepository.save(question);
//			return String.format("redirect:/questions/%d", id);
//		} catch (IllegalStateException e) {
//			model.addAttribute("errorMessage", e);
//			return "/user/login";
//		}
	}
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable Long id, Model model, HttpSession session) {
		Question question = questionRepository.findById(id).orElse(new Question());	
		Result result = valid(session, question);
		if ( !result.isValid()) {
			model.addAttribute("errorMessage", result.getMessage());
			return "/user/login";
		}
		questionRepository.deleteById(id);
		return "redirect:/";
//		
//		try {
//			Question question = questionRepository.findById(id).orElse(new Question());	
//			hasPermission(session, question);
//			questionRepository.deleteById(id);
//			return "redirect:/";
//		} catch (IllegalStateException e) {
//			model.addAttribute("errorMessage", e);
//			return "/user/login";
//		}
	}
	
}
