package com.quiz.quizgame.service;

import com.quiz.quizgame.model.GameSession;
import com.quiz.quizgame.model.Quiz;
import com.quiz.quizgame.repository.GameSessionRepository;
import com.quiz.quizgame.repository.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GameService
{

	private static final int QUESTION_TIME_LIMIT = 15;

	private final GameSessionRepository sessionRepo;
	private final QuizRepository        quizRepo;

	public GameService ( GameSessionRepository sessionRepo , QuizRepository quizRepo )
	{
		this.sessionRepo = sessionRepo;
		this.quizRepo    = quizRepo;
	}

	// 🎮 START GAME
	public GameSession startGame ()
	{

		GameSession session = new GameSession ();
		session.setScore ( 0 );
		session.setFinished ( false );
		session.setCurrentQuestionIndex ( 0 );
		session.setQuestionStartTime ( LocalDateTime.now () );

		System.out.println ( "📊 TOTAL QUESTIONS: " + quizRepo.count () );
		
		return sessionRepo.save ( session );
	}

	// ❓ GET QUESTION
	public Quiz getQuestion ( Long sessionId )
	{

		GameSession   session   = getSession ( sessionId );
		List < Quiz > questions = quizRepo.findAll ();
		int           index     = session.getCurrentQuestionIndex ();

		// 🔥 SI PLUS DE QUESTIONS → ON NE CONTINUE PAS
		if ( index >= questions.size () )
		{
			session.setFinished ( true );
			sessionRepo.save ( session );
			throw new RuntimeException ( "Game Over" );
		}

		// 🔥 SI LE JEU EST DÉJÀ FINI → ON STOP
		if ( session.isFinished () )
		{
			throw new RuntimeException ( "Game Over" );
		}

		Quiz question = questions.get ( index );

		session.setQuestionStartTime ( LocalDateTime.now () );
		sessionRepo.save ( session );

		return question;
	}

	// ✅ SUBMIT ANSWER
	public Map < String, Object > submitAnswer ( Long sessionId , String answer )
	{

		GameSession   session   = getSession ( sessionId );
		List < Quiz > questions = quizRepo.findAll ();
		int           index     = session.getCurrentQuestionIndex ();

		if ( index >= questions.size () )
		{
			session.setFinished ( true );
			sessionRepo.save ( session );

			return Map.of ( "message" , "Game Over" , "score" , session.getScore () , "gameOver" , true );
		}

		Quiz currentQuestion = questions.get ( index );

		boolean correct = false;

		if ( answer != null && session.getQuestionStartTime () != null )
		{

			long secondsElapsed = Duration.between ( session.getQuestionStartTime () , LocalDateTime.now () ).getSeconds ();

			if ( secondsElapsed <= QUESTION_TIME_LIMIT && currentQuestion.getAnswer ().equalsIgnoreCase ( answer ) )
			{

				session.setScore ( session.getScore () + 10 );
				correct = true;
			}
		}

		session.setCurrentQuestionIndex ( index + 1 );

		boolean gameOver = session.getCurrentQuestionIndex () >= questions.size ();

		if ( gameOver )
		{
			session.setFinished ( true );
			sessionRepo.save ( session );
		}

		sessionRepo.save ( session );

		System.out.println ( "🔥 SCORE BEFORE RETURN: " + session.getScore () );
		System.out.println ( "🔥 CURRENT INDEX: " + session.getCurrentQuestionIndex () );
		return Map.of ( "message" , correct ? "Correct!" : "Wrong!" , "score" , session.getScore () , "gameOver" , gameOver );
	}

	private void deleteSession ( Long sessionId )
	{
		sessionRepo.deleteById ( sessionId );
	}

	private GameSession getSession ( Long sessionId )
	{
		return sessionRepo.findById ( sessionId ).orElseThrow ( () -> new RuntimeException ( "Session not found" ) );
	}
}
