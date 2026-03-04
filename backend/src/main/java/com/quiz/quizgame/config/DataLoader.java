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
	public void loadData ()
	{

		// 🔥 Si des questions existent → on ne fait rien
		if ( quizRepository.count () > 0 )
		{
			System.out.println ( "📦 Questions already exist — skipping data load." );
			return;
		}

		System.out.println ( "🚀 Inserting default quiz questions..." );

		Quiz q1 = new Quiz ();
		q1.setQuestion ( "2 + 2 ?" );
		q1.setOption_1 ( "3" );
		q1.setOption_2 ( "4" );
		q1.setOption_3 ( "5" );
		q1.setOption_4 ( "6" );
		q1.setImage_url ( "images/france,jpg" );
		q1.setAnswer ( "4" );

		Quiz q2 = new Quiz ();
		q2.setQuestion ( "Capital of France ?" );
		q2.setOption_1 ( "Berlin" );
		q2.setOption_2 ( "Madrid" );
		q2.setOption_3 ( "Paris" );
		q2.setOption_4 ( "Rome" );
		q1.setImage_url ( "images/france,jpg" );
		q2.setAnswer ( "Paris" );

		quizRepository.save ( q1 );
		quizRepository.save ( q2 );

		System.out.println ( "✅ Default questions inserted." );
	}
}
