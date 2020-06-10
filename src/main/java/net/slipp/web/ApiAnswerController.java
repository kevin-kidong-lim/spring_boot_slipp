package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.slipp.domain.Answer;
import net.slipp.domain.AnswerRepository;
import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.Result;
import net.slipp.domain.User;

//@Controller
@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;
	
	@PostMapping("")
	public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
//			return "redirect:/users/loginForm";
			return null;
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(questionId).orElse(new Question());
		Answer answer = new Answer(loginUser, question, contents);
		question.addAnswer();
		
		return answerRepository.save(answer);
		
//		return String.format("redirect:/questions/%d", questionId);
	}
	
	@GetMapping("/{id}")
	public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("Login please");
		}
		Answer answer = answerRepository.findById(id).orElse(new Answer());
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if (!answer.isSameWriter(loginUser)) {
			return Result.fail("writer is not user");
		}
		answerRepository.deleteById(id);
		Question question = questionRepository.findById(questionId).orElse(new Question());
		question.deleteAnswer();
		questionRepository.save(question);
		
		return Result.ok();
		
	}
}
