package com.quiz.quizgame.job;

import com.quiz.quizgame.model.GameSession;
import com.quiz.quizgame.repository.GameSessionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CleanupGameSessionJob
{

	private final GameSessionRepository sessionRepo;

	public CleanupGameSessionJob ( GameSessionRepository sessionRepo )
	{
		this.sessionRepo = sessionRepo;
	}

	// 🔥 S'exécute toutes les 10 minutes
	@Scheduled ( fixedRate = 600000 )
	public void cleanup ()
	{

		System.out.println ( "🧹 CLEANUP JOB RUNNING..." );

		LocalDateTime limit = LocalDateTime.now ().minusMinutes ( 10 );

		List < GameSession > sessions = sessionRepo.findByFinishedTrueAndQuestionStartTimeBefore ( limit );

		if ( ! sessions.isEmpty () )
		{
			System.out.println ( "🗑 Deleting " + sessions.size () + " sessions" );
			sessionRepo.deleteAll ( sessions );
		}
	}
}
