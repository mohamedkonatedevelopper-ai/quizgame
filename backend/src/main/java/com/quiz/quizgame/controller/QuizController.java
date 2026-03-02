package com.quiz.quizgame.controller;

import com.quiz.quizgame.dto.AnswerRequest;
import com.quiz.quizgame.model.Quiz;
import com.quiz.quizgame.service.QuizService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ( "/api/home" )
@CrossOrigin ( origins = "http://localhost:5173" )
public class QuizController
{

	private final QuizService service;

	public QuizController ( QuizService service )
	{
		this.service = service;
	}

	@GetMapping ( "/questions" )
	public List < Quiz > getAllQuestion ()
	{
		return service.getAllQuestion ();
	}

	@PostMapping ( "/check" )
	public String checkAnswer ( @RequestBody AnswerRequest request )
	{

		boolean isCorrect = service.checkAnswer ( request.getQuestionId () , request.getSelectedAnswer () );

		if ( isCorrect )
		{
			return "Correct answer! +10 points 🎉";
		}
		else
		{
			return "Wrong answer ❌";
		}
	}
}
