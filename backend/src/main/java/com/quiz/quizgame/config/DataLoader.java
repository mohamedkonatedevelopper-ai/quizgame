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
		if ( quizRepository.count () > 0 )
		{
			System.out.println ( "📦 Questions already exist — skipping data load." );
			return;
		}

		System.out.println ( "🚀 Inserting default quiz questions..." );

		Quiz q1 = new Quiz ();
		q1.setQuestion ( "В каком городе находится женский футбольный клуб ЦСКА?" );
		q1.setOption_1 ( "Санкт-Петербург" );
		q1.setOption_2 ( "Казань" );
		q1.setOption_3 ( "Москва" );
		q1.setOption_4 ( "Самара" );
		q1.setImage_url ( "/images/question.png" );
		q1.setAnswer ( "Москва" );

		Quiz q2 = new Quiz ();
		q2.setQuestion ( "В каком году был основан ЖФК ЦСКА?" );
		q2.setOption_1 ( "2000" );
		q2.setOption_2 ( "2003" );
		q2.setOption_3 ( "2005" );
		q2.setOption_4 ( "2010" );
		q2.setImage_url ( "/images/question.png" );
		q2.setAnswer ( "2003" );

		Quiz q3 = new Quiz ();
		q3.setQuestion ( "Как назывался клуб в 2003–2004 годах?" );
		q3.setOption_1 ( "Спартак" );
		q3.setOption_2 ( "Надежда-Боевое Братство" );
		q3.setOption_3 ( "Динамо" );
		q3.setOption_4 ( "Локомотив" );
		q3.setImage_url ( "/images/question.png" );
		q3.setAnswer ( "Надежда-Боевое Братство" );

		Quiz q4 = new Quiz ();
		q4.setQuestion ( "Как назывался клуб в 2004–2016 годах?" );
		q4.setOption_1 ( "Россиянка" );
		q4.setOption_2 ( "Зенит" );
		q4.setOption_3 ( "Динамо" );
		q4.setOption_4 ( "Спартак" );
		q4.setImage_url ( "/images/question.png" );
		q4.setAnswer ( "Россиянка" );

		Quiz q5 = new Quiz ();
		q5.setQuestion ( "С какого года клуб выступает под названием ЦСКА?" );
		q5.setOption_1 ( "2012" );
		q5.setOption_2 ( "2014" );
		q5.setOption_3 ( "2016" );
		q5.setOption_4 ( "2018" );
		q5.setImage_url ( "/images/question.png" );
		q5.setAnswer ( "2016" );

		Quiz q6 = new Quiz ();
		q6.setQuestion ( "Сколько раз клуб становился чемпионом России?" );
		q6.setOption_1 ( "5" );
		q6.setOption_2 ( "6" );
		q6.setOption_3 ( "7" );
		q6.setOption_4 ( "9" );
		q6.setImage_url ( "/images/question.png" );
		q6.setAnswer ( "7" );

		Quiz q7 = new Quiz ();
		q7.setQuestion ( "В какой лиге выступает женская команда ЦСКА?" );
		q7.setOption_1 ( "Первая лига" );
		q7.setOption_2 ( "Winline Суперлига" );
		q7.setOption_3 ( "Лига Европы" );
		q7.setOption_4 ( "Национальная лига" );
		q7.setImage_url ( "/images/question.png" );
		q7.setAnswer ( "Winline Суперлига" );

		Quiz q8 = new Quiz ();
		q8.setQuestion ( "В каком году ЦСКА стал чемпионом России?" );
		q8.setOption_1 ( "2017" );
		q8.setOption_2 ( "2018" );
		q8.setOption_3 ( "2019" );
		q8.setOption_4 ( "2022" );
		q8.setImage_url ( "/images/question.png" );
		q8.setAnswer ( "2019" );

		Quiz q9 = new Quiz ();
		q9.setQuestion ( "В каком году ЦСКА снова выиграл чемпионат России?" );
		q9.setOption_1 ( "2018" );
		q9.setOption_2 ( "2020" );
		q9.setOption_3 ( "2021" );
		q9.setOption_4 ( "2024" );
		q9.setImage_url ( "/images/question.png" );
		q9.setAnswer ( "2020" );

		Quiz q10 = new Quiz ();
		q10.setQuestion ( "Сколько раз клуб выигрывал Кубок России?" );
		q10.setOption_1 ( "5" );
		q10.setOption_2 ( "7" );
		q10.setOption_3 ( "9" );
		q10.setOption_4 ( "12" );
		q10.setImage_url ( "/images/question.png" );
		q10.setAnswer ( "9" );


		quizRepository.save ( q1 );
		quizRepository.save ( q2 );
		quizRepository.save ( q3 );
		quizRepository.save ( q4 );
		quizRepository.save ( q5 );
		quizRepository.save ( q6 );
		quizRepository.save ( q7 );
		quizRepository.save ( q8 );
		quizRepository.save ( q9 );
		quizRepository.save ( q10 );

		System.out.println ( "✅ Default questions inserted." );
	}
}
