package com.quiz.quizgame.config;

import com.quiz.quizgame.model.Quiz;
import com.quiz.quizgame.repository.QuizRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataLoader
{

	private final QuizRepository quizRepository;

	public DataLoader ( QuizRepository quizRepository )
	{
		this.quizRepository = quizRepository;
	}

	@PostConstruct
	public void init ()
	{

		if ( quizRepository.count () > 0 )
		{
			System.out.println ( "📦 Questions already exist." );
			return;
		}

		System.out.println ( "🚀 Inserting quiz questions..." );

		quizRepository.save ( new Quiz ( 0 , "2 + 2 ?" , "3" , "4" , "5" , "6" , "images/france.jpg" , "4" ) );

		quizRepository.save ( new Quiz ( 0 , "Capital of France ?" , "Berlin" , "Madrid" , "Paris" , "Rome" , "images/france.jpg" , "Paris" ) );

		System.out.println ( "✅ Questions inserted." );
	}
}
