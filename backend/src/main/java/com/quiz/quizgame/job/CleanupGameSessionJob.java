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

		LocalDateTime limit = LocalDateTime.now ().minusMinutes ( 30 );

		List < GameSession > sessions = sessionRepo.findByFinishedTrueAndQuestionStartTimeBefore ( limit );

		if ( ! sessions.isEmpty () )
		{

			sessions.forEach ( session ->
			                   {

				                   // ✅ Sécurité supplémentaire avant suppression
				                   if ( session.isFinished () && session.getCurrentQuestionIndex () > 0 )
				                   {

					                   System.out.println ( "🗑 Deleting session id: " + session.getId () );

					                   sessionRepo.delete ( session );
				                   }
			                   } );
		}
	}
}
