package com.quiz.quizgame.service;

import com.quiz.quizgame.model.Quiz;
import com.quiz.quizgame.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService
{
	private final QuizRepository repository;

	public QuizService ( QuizRepository repository )
	{
		this.repository = repository;
	}

	public List < Quiz > getAllQuestion ()
	{
		return repository.findAll ();
	}

	public boolean checkAnswer ( long questionId , String selectedAnswer )
	{

		Quiz quiz = repository.findById ( questionId ).orElseThrow ( () -> new RuntimeException ( "Question not found" ) );

		return quiz.getAnswer ().equalsIgnoreCase ( selectedAnswer );
	}
}
