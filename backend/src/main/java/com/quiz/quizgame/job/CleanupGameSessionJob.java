package com.quiz.quizgame.job;

import com.quiz.quizgame.repository.GameSessionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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

		sessionRepo.findAll ().forEach ( session ->
		                                 {

			                                 if ( session.isFinished () )
			                                 {

				                                 // 🔥 Supprimer si terminé depuis plus de 5 minutes
				                                 if ( session.getQuestionStartTime () != null && session.getQuestionStartTime ().isBefore ( LocalDateTime.now ().minusMinutes ( 5 ) ) )
				                                 {

					                                 sessionRepo.delete ( session );
				                                 }
			                                 }

		                                 } );
	}
}
